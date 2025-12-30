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
@Table(name = "categories")
public class Category {
//    Maşınları qruplaşdırmaq üçün (məsələn: Sedan, SUV, Luxury).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//* String name
    private String name;

    @OneToMany
    List<Car> cars = new ArrayList<>();
}
