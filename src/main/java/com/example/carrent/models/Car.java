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

    private Double dailyPrice;

//* String transmission (Ötürücü - Manual/Automatic)
    private String transmission;

    //* String fuelType (Yanacaq növü)
    private String fuelType;

//* Integer doorCount
    private Integer doorCount;
//* Integer passengerCount
    private Integer passengerCount;


//* String imagePath (Şəkil yolu)
    private String imageUrl;


//* Boolean available (Hal-hazırda boşdadır?)
    private Boolean available;
//* @ManyToOne Category category (Hər maşın bir kateqoriyaya aid olur)
    @ManyToOne
    private Category category;

}
