package com.example.carrent.dtos.booking;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class BookingCompleteDto {

    private Long userId;

    @NotNull(message = "{validation.carId.notNull}")
    private Long carId;

    @NotNull(message = "{validation.startDate.notNull}")
    @FutureOrPresent(message = "{validation.startDate.futureOrPresent}")
    private LocalDate startDate;

    @NotNull(message = "{validation.endDate.notNull}")
    @FutureOrPresent(message = "{validation.endDate.futureOrPresent}")
    private LocalDate endDate;

    @NotBlank(message = "{validation.pickupLocation.notBlank}")
    private String pickupLocation;

    private String notes;

    @NotBlank(message = "{validation.firstName.notBlank}")
    private String firstName;

    @NotBlank(message = "{validation.lastName.notBlank}")
    private String lastName;

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.valid}")
    private String email;

    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    private MultipartFile licenseFile;
}
