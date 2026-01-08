package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    boolean completeBooking(BookingCompleteDto bookingDto);


    List<BookingOrdersDto> getAllOrders();

    boolean updateStatus(Long id, BookingStatus status);
}
