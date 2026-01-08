package com.example.carrent.dtos.booking;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
public class BookingCompleteDto {
    private Long userId;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private String notes;

    // HTML-dəki adlarla eyni olmalıdır:
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // MultipartFile burada olmasa daha yaxşıdır, çünki Controller-də @RequestParam ilə alırsınız.
    // Amma qalsa da olar, sadəcə HTML-də 'licenseFile' adı ilə eyni olmalıdır.
    private MultipartFile licenseFile;
}