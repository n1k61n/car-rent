package com.example.carrent.services;

import com.example.carrent.dtos.car.CarCreateDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.car.CarUpdateDto;
import com.example.carrent.models.Car;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CarService {
    Page<Car> findAll(Pageable pageable);
    CarDto getCarById(Long id);
    Set<String> getAllCarTypes();
    Page<Car> searchCarsPageable(String brand, String category, Integer passengerCount, Pageable pageable);
    boolean createCar(@Valid CarCreateDto carCreateDto);
    boolean deleteCar(Long id);
    CarUpdateDto getUpdateCar(Long id);
    boolean updateCar(Long id, CarUpdateDto carUpdateDto);
    Page<CarDto> searchCars(String keyword, Pageable pageable);


    long countAll();
}
