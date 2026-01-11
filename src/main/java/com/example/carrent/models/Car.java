package com.example.carrent.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "daily_price")
    private Double dailyPrice;
    private Integer year;
    private String transmission;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "door_count")
    private Integer doorCount;
    @Column(name = "passenger_count")
    private Integer passengerCount;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "luggage_capacity")
    private Integer luggageCapacity;
    private Boolean available;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    private String suitcases;
    private String fuelPolicy;
    private String mileage;
    private Boolean roadAssistance = true;
    private Boolean insuranceIncluded = true;
    private Boolean freeCancellation = true;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "car_features", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "feature_name")
    private List<String> features = new ArrayList<>();

}
