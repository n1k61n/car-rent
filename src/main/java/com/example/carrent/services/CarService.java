package com.example.carrent.services;

import com.example.carrent.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Page<Car> findAll(Pageable pageable);
}
