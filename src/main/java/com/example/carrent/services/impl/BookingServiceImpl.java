package com.example.carrent.services.impl;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import com.example.carrent.models.Car;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public boolean completeBooking(BookingCompleteDto dto) {
        // 1. Avtomobili tapırıq
        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Avtomobil tapılmadı"));

        try {
            // 2. Yeni Booking entity-si yaradırıq
            Booking booking = new Booking();
            booking.setCar(car);
            booking.setStartDate(dto.getStartDate());
            booking.setEndDate(dto.getEndDate());
            booking.setPickupLocation(dto.getPickupLocation());
            booking.setNotes(dto.getNotes());
            booking.setStatus(BookingStatus.PENDING);

            // 3. Qiyməti backend-də yenidən hesablayırıq (Təhlükəsizlik üçün)
            long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
            booking.setTotalPrice((days <= 0 ? 1 : days) * car.getDailyPrice());

            // 4. Faylın (Sürücülük vəsiqəsi) saxlanılması
            if (dto.getLicenseFile() != null && !dto.getLicenseFile().isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + dto.getLicenseFile().getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/uploads/licenses/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, dto.getLicenseFile().getBytes());
                booking.setLicenseFilePath("/uploads/licenses/" + fileName);
            }

            // 5. Bazaya yazırıq
            bookingRepository.save(booking);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
