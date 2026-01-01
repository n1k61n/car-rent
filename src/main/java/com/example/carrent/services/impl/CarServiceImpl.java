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


}
