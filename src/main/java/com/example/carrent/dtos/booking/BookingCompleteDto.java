package com.example.carrent.dtos.booking;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
public class BookingCompleteDto {
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private String notes;
    private MultipartFile licenseFile;
}
