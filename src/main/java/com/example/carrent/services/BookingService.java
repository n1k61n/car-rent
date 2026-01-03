package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingDto;

public interface BookingService {

    boolean checkAvailability(BookingDto bookingDto);

    boolean createBooking(BookingDto bookingDto);
}
