package com.example.carrent.dtos.booking;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BookingDto {
    private Long carId;
    @FutureOrPresent(message = "Götürmə tarixi keçmiş ola bilməz")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @FutureOrPresent(message = "Qaytarma tarixi keçmiş ola bilməz")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String pickupLocation;
}
