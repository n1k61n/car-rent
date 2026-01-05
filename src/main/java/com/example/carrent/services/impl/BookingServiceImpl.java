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
        boolean result = bookingRepository.existsOverlapping(
                bookingDto.getCarId(),
                bookingDto.getStartDate().atStartOfDay(),
                bookingDto.getEndDate().atStartOfDay());
        return !result;
    }

    @Override
    public boolean createBooking(BookingDto bookingDto) {
        if (!checkAvailability(bookingDto)) {
            throw new RuntimeException("Bu tarixlərdə avtomobil artıq rezerv olunub!");
        }
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        booking.setStatus(BookingStatus.PENDING);
        bookingRepository.save(booking);

        return true;
    }
}
