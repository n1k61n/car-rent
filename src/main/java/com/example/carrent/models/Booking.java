package com.example.carrent.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    //İstifadəçinin maşını icarəyə götürdüyü məlumatlar.
    //Booking (Rezervasiya): Müştəri sayta girir, maşını seçir və "Bron et" düyməsini sıxır.
    // Maşın hələ onda deyil, sadəcə onun üçün ayrılıb. (Status: PENDING və ya CONFIRMED)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    //* @ManyToOne User user
    @ManyToOne
    private User user;
    //* @ManyToOne Car car
    @ManyToOne
    private Car car;
    //* LocalDate pickupDate (Götürmə tarixi)
    private LocalDate pickupDate;
    //* LocalDate dropoffDate (Təhvil vermə tarixi)
    private LocalDate dropoffDate;
    //* String pickupLocation (Haradan götürəcək)
    private String pickupLocation;
    //* Double totalPrice (Cəmi məbləğ)
    private Double totalPrice;

}
