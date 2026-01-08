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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Transactional
    @Override
    public boolean completeBooking(BookingCompleteDto dto) {
        // Debug üçün: Konsola baxın, ID-nin gəldiyinə əmin olun
        System.out.println("Gələn User ID: " + dto.getUserId());

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("İstifadəçi ID-si boş ola bilməz!");
        }

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Avtomobil tapılmadı"));

        try {
            Booking booking = new Booking();
            booking.setCar(car);
            booking.setStartDate(dto.getStartDate());
            booking.setEndDate(dto.getEndDate());
            booking.setPickupLocation(dto.getPickupLocation());
            booking.setNotes(dto.getNotes());
            booking.setStatus(BookingStatus.PENDING);

            // İstifadəçini set edirik
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("İstifadəçi tapılmadı"));
            booking.setUser(user);

            // ... qiymət hesablama və fayl saxlama hissəsi (dəyişmir)

            bookingRepository.save(booking);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

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

}
