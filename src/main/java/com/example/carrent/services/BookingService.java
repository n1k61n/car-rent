package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.dtos.booking.BookingUserDto;
import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BookingService {

    List<BookingOrdersDto> getAllOrders();

    boolean updateStatus(Long id, BookingStatus status);

    Long completeBooking(BookingCompleteDto bookingDto, MultipartFile licenseFile);

    long countActive();

    List<Booking> getRecentBookings();

    boolean deleteBooking(Long id, String username);

    List<BookingUserDto> findByUser(UserProfileDto user);

    long countByUser(UserProfileDto user);

    long countByUserAndStatus(UserProfileDto user, BookingStatus bookingStatus);

    BigDecimal sumTotalPriceByUser(UserProfileDto user);

    double getMonthEarnings();

    Map<String, Double> getMonthlyEarnings();

    Map<String, Long> getBookingStatusDistribution();

    List<BookingOrdersDto> getAllActiveOrders();
}
