package com.example.carrent.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String brand;

    private String model;

    @Column(name = "daily_price")
    private Double dailyPrice;

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

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
