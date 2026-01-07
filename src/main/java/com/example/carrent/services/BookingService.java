package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingCreateDto;
import com.example.carrent.dtos.booking.BookingDto;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingService {

    boolean completeBooking(BookingCompleteDto bookingDto);
}
