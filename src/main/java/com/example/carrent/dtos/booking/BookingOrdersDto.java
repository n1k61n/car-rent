package com.example.carrent.dtos.booking;

import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
public class BookingOrdersDto {
    private Long id;

    // Entity yox, String və ya müvafiq DTO istifadə edin
    private String userFirstName;
    private String userLastName;

    private String carBrand;
    private String carModel;

    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private Double totalPrice;
    private BookingStatus status;
    private String notes;
    private String licenseFilePath;
}
