package com.example.carrent.dtos.booking;

import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingUserDto {
    private Long id;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
    private User user;
    private BigDecimal totalPrice;
}