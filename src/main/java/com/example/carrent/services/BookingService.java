package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookingService {



    List<BookingOrdersDto> getAllOrders();

    boolean updateStatus(Long id, BookingStatus status);

    Long completeBooking(BookingCompleteDto bookingDto, MultipartFile licenseFile);

    long countActive();

    List<Booking> getRecentBookings();

    boolean deleteBooking(Long id, String username);
}
