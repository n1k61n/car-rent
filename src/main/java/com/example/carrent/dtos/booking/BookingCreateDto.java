package com.example.carrent.dtos.booking;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile licenseFile;

}
