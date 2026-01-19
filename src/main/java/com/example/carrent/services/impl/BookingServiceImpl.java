package com.example.carrent.services.impl;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Booking;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;



    @Override
    @Transactional(readOnly = true)
    public List<BookingOrdersDto> getAllOrders() {
        log.debug("Fetching all booking orders");
        List<Booking> bookings = bookingRepository.findAllWithDetails();

        return bookings.stream().map(booking -> {
            BookingOrdersDto dto = new BookingOrdersDto();
            dto.setId(booking.getId());

            // Lazy obyektlərə müraciət artıq xəta verməyəcək
            if (booking.getCar() != null) {
                dto.setCarId(booking.getCar().getId());
                dto.setCarBrand(booking.getCar().getBrand());
                dto.setCarModel(booking.getCar().getModel());
            }

            if (booking.getUser() != null) {
                dto.setUserFirstName(booking.getUser().getFirstName());
                dto.setUserLastName(booking.getUser().getLastName());
            }

            dto.setStartDate(booking.getStartDate());
            dto.setEndDate(booking.getEndDate());
            dto.setStatus(booking.getStatus());
            dto.setLicenseFilePath(booking.getLicenseFilePath());

            return dto;
        }).toList();
    }


    @Override
    @Transactional
    public boolean updateStatus(Long id, BookingStatus status) {
        log.info("Updating booking status. ID: {}, Status: {}", id, status);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sifariş tapılmadı. ID: " + id));
        booking.setStatus(status);
        if (status == BookingStatus.COMPLETED || status == BookingStatus.CANCELLED) {
            booking.getCar().setAvailable(true);
        }
        bookingRepository.save(booking);
        log.info("Booking status updated successfully. ID: {}", id);
        return true;
    }

    @Override
    public long countActive() {
        return bookingRepository.countByStatus(BookingStatus.ACTIVE);
    }


    private String saveLicenseFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "uploads/licenses/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }


    @Override
    @Transactional
    public Long completeBooking(BookingCompleteDto dto, MultipartFile licenseFile) {
        log.info("Attempting to complete booking for Car ID: {}, User ID: {}", dto.getCarId(), dto.getUserId());
        if (licenseFile == null || licenseFile.isEmpty()) {
            throw new IllegalArgumentException("Sürücülük vəsiqəsi faylı mütləqdir!");
        }
        validateLicenseFile(licenseFile);


        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Avtomobil tapılmadı. ID: " + dto.getCarId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı. ID: " + dto.getUserId()));


        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        if (days <= 0) days = 1;


        Booking booking = new Booking();
        booking.setCar(car);
        booking.setUser(user);
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setPickupLocation(dto.getPickupLocation());
        booking.setNotes(dto.getNotes());
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(days * car.getDailyPrice());


        try {
            String fileName = saveLicenseFile(licenseFile);
            booking.setLicenseFilePath(fileName);
        } catch (IOException e) {
            log.error("Failed to save license file", e);
            throw new RuntimeException("Fayl sisteminə yazıla bilmədi: " + e.getMessage());
        }

        car.setAvailable(false);
        Booking savedBooking = bookingRepository.save(booking);

        notificationService.createNotification(
                "New booking for " + savedBooking.getCar().getBrand(),
                "/dashboard/bookings/index",
                "BOOKING"
        );
        log.info("Booking completed successfully. Booking ID: {}", savedBooking.getId());
        return savedBooking.getId();
    }


    private void validateLicenseFile(MultipartFile file) {
        long maxSize = 10 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new RuntimeException("Fayl çox böyükdür! Maksimum 10MB.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Yalnız şəkil formatları (JPG, PNG) qəbul edilir.");
        }
    }

    @Override
    public List<Booking> getRecentBookings() {
        return bookingRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    public double getMonthEarnings() {
        return  bookingRepository.getMonthEarnings();
    }

    @Override
    public Map<String, Double> getMonthlyEarnings() {

        List<Object[]> results = bookingRepository.getMonthlyEarnings();
        Map<String, Double> data = new LinkedHashMap<>();

        String[] months = {
                "Yan", "Fev", "Mar", "Apr", "May", "İyn",
                "İyl", "Avq", "Sen", "Okt", "Noy", "Dek"
        };

        for (int i = 0; i < 12; i++) {
            data.put(months[i], 0.0);
        }

        for (Object[] row : results) {
            int monthIndex = ((Number) row[0]).intValue() - 1;
            double amount = ((Number) row[1]).doubleValue();
            data.put(months[monthIndex], amount);
        }

        return data;
    }

}
