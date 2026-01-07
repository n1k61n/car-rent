package com.example.carrent.dtos.booking;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BookingCreateDto {
    private Long carId;
    private LocalDate startDate;
    private  LocalDate endDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String notes;
    private Double totalPrice;
    private String licenseFilePath;



}
