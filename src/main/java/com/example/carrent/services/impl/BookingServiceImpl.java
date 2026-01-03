package com.example.carrent.services.impl;

import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean checkAvailability(BookingDto bookingDto) {
        // Avtomobilin seçilən tarixlərdə boş olub-olmadığını yoxlayırıq
//        return !bookingRepository.existsOverlapping(
//                bookingDto.getCarId(),
//                bookingDto.getPickUp(),
//                bookingDto.getDropOff()
//        );
        return false;
    }

    @Override
    public boolean createBooking(BookingDto bookingDto) {
        // 1. Öncə mövcudluğu yoxla
        if (!checkAvailability(bookingDto)) {
            throw new RuntimeException("Bu tarixlərdə avtomobil artıq rezerv olunub!");
        }

        // 2. DTO-nu Entity-yə çevir (ModelMapper ilə)
        Booking booking = modelMapper.map(bookingDto, Booking.class);

        // 3. Statusu təyin et və yaddaşa yaz
        booking.setStatus(BookingStatus.PENDING); // Və ya PENDING
        bookingRepository.save(booking);

        return true;
    }
}
