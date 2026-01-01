package com.example.carrent.services;

import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CarService {

    Page<Car> findAll(Pageable pageable);

    CarDto getCarById(Long id);

    Set<String> getAllCarTypes();

    Page<Car> searchCarsPageable(String brand, String pickup, String dropoff, Pageable pageable);
}
