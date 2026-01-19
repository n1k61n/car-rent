package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookingService {

    List<BookingOrdersDto> getAllOrders();

    boolean updateStatus(Long id, BookingStatus status);

    Long completeBooking(BookingCompleteDto bookingDto, MultipartFile licenseFile);

    long countActive();

    List<Booking> getRecentBookings();

    double getMonthEarnings();

    Map<String, Double> getMonthlyEarnings();
}
