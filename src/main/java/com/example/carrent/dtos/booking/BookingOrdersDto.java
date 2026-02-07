package com.example.carrent.dtos.booking;

import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class BookingOrdersDto {
    private Long id;

    private Long carId;
    private String userFirstName;
    private String userLastName;

    private String carBrand;
    private String carModel;

    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private BigDecimal totalPrice;
    private BigDecimal dailyPrice;
    private BookingStatus status;
    private String notes;
    private String licenseFilePath;
    private Car car;
    private User user;
    private String phone;
}
