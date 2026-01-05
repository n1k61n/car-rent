package com.example.carrent.models;

import com.example.carrent.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    private LocalDate startDate;

    private LocalDate endDate;

    private String pickupLocation;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String notes;
    @Transient // Hibernate bu sahəni bazada sütun olaraq yaratmayacaq
    private MultipartFile licenseFile;

    // Bazada faylın yolunu saxlamaq üçün String istifadə et
    private String licenseFilePath;

}
