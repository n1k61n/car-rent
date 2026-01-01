package com.example.carrent.services.impl;

import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.models.Car;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.services.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public CarDto getCarById(Long id) {
        if(carRepository.existsById(id)){
            Car car = carRepository.findById(id).get();
            return modelMapper.map(car, CarDto.class);
        }
        return new CarDto();
    }


    @Override
    public Set<String> getAllCarTypes() {
        Set<String> brands =  carRepository.findAllBrandsOrderByName();
        return brands;
    }

    @Override
    public Page<Car> searchCarsPageable(String brand, String pickup, String dropoff, Pageable pageable) {
        String brandFilter = (brand == null || brand.isEmpty() || brand.equals("Select Type")) ? null : brand;

        LocalDate start = null;
        LocalDate end = null;

        try {
            if (pickup != null && !pickup.isEmpty()) {
                start = LocalDate.parse(pickup);
            }
            if (dropoff != null && !dropoff.isEmpty()) {
                end = LocalDate.parse(dropoff);
            }
        } catch (DateTimeParseException e) {
            // Tarix formatı səhvdirsə, loglamaq və ya null saxlamaq olar
            System.err.println("Tarix formatı səhvdir: " + e.getMessage());
        }

        return carRepository.findAvailableCarsPageable(brandFilter, start, end, pageable);
    }


}
