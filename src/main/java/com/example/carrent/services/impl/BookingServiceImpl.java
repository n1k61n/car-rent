package com.example.carrent.services.impl;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.user.UserDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;



    @Override
    @Transactional(readOnly = true)
    public List<BookingOrdersDto> getAllOrders() {
        List<Booking> bookings = bookingRepository.findAllWithDetails(); // Join Fetch metodu

        return bookings.stream().map(booking -> {
            BookingOrdersDto dto = new BookingOrdersDto();
            dto.setId(booking.getId());

            // Lazy obyektlərə müraciət artıq xəta verməyəcək
            if (booking.getCar() != null) {
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
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            return false;
        }
        booking.setStatus(status);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean completeBooking(BookingCompleteDto dto, MultipartFile licenseFile) {
        try {
            // 1. Avtomobili yoxla
            Car car = carRepository.findById(dto.getCarId())
                    .orElseThrow(() -> new EntityNotFoundException("Avtomobil tapılmadı"));

            // 2. İstifadəçini tap (Təhlükəsiz yol: ID-yə güvənmək əvəzinə mövcud sessiyadan alırıq)
            // Əgər DTO-dan gələn ID mütləqdirsə:
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("İstifadəçi tapılmadı ID: " + dto.getUserId()));

            // 3. Booking obyektini qur
            Booking booking = new Booking();
            booking.setCar(car);
            booking.setUser(user);
            booking.setStartDate(dto.getStartDate());
            booking.setEndDate(dto.getEndDate());
            booking.setPickupLocation(dto.getPickupLocation());
            booking.setNotes(dto.getNotes());
            booking.setStatus(BookingStatus.PENDING);

            // 4. Qiymət hesablama
            long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
            if (days <= 0) days = 1;
            booking.setTotalPrice(days * car.getDailyPrice());

            // 5. Faylı yaddaşa yazmaq (Əgər DTO-da MultipartFile varsa)
            if (dto.getLicenseFile() != null && !dto.getLicenseFile().isEmpty()) {
                String fileName = saveLicenseFile(licenseFile);
                booking.setLicenseFilePath(fileName);
            }

            bookingRepository.save(booking);
            return true;

        } catch (Exception e) {
            // Loglama vacibdir, System.out yerinə logger istifadə etmək daha yaxşıdır
            System.err.println("Booking Error: " + e.getMessage());
            // @Transactional olduğu üçün burada runtime exception atmaq lazımdır ki, rollback işləsin
            throw new RuntimeException("Sifariş tamamlanarkən texniki xəta: " + e.getMessage());
        }
    }


    private String saveLicenseFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 1. Faylların saxlanacağı qovluq (məsələn: uploads/licenses)
        String uploadDir = "uploads/licenses/";
        Path uploadPath = Paths.get(uploadDir);

        // 2. Qovluq yoxdursa, yaradırıq
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. Fayl adını unikal edirik (Məs: 550e8400-e29b-car.jpg)
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        // 4. Faylı diskə kopyalayırıq
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 5. Verilənlər bazasında saxlamaq üçün fayl adını (və ya yolunu) qaytarırıq
        return uniqueFileName;
    }

}
