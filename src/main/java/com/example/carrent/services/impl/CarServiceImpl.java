package com.example.carrent.services.impl;

import com.example.carrent.dtos.car.CarCreateDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.car.CarUpdateDto;
import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Car;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.services.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public CarDto getCarById(Long id) {
        return carRepository.findById(id)
                .map(car -> modelMapper.map(car, CarDto.class))
                .orElseGet(CarDto::new);
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
            System.err.println("Tarix formatı səhvdir: " + e.getMessage());
        }

        return carRepository.findAvailableCarsPageable(brandFilter, start, end, pageable);
    }


    @Override
    public boolean createCar(CarCreateDto carCreateDto) {
        try {
            Car car = modelMapper.map(carCreateDto, Car.class);
            car.setAvailable(true);
            carRepository.save(car);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Transactional
    public boolean deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Avtomobil tapılmadı: " + carId));
        bookingRepository.deleteByCarId(carId);
        carRepository.delete(car);
        return true;
    }

    @Override
    public CarUpdateDto getUpdateCar(Long id) {
        if(carRepository.existsById(id)){
            Car car = carRepository.findById(id).get();
            return modelMapper.map(car, CarUpdateDto.class);
        }
        return null;
    }

    @Override
    public boolean updateCar(Long id, CarUpdateDto carUpdateDto) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()){
            Car existCar = optionalCar.get();
            modelMapper.map(carUpdateDto, existCar);
            existCar.setId(id);
            carRepository.save(existCar);
            return true;
        }
        return false;
    }



    public Page<CarDto> searchCars(String keyword, Pageable pageable) {
        Page<Car> carPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            carPage = carRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(
                    keyword, keyword, pageable);
        } else {
            carPage = carRepository.findAll(pageable);
        }
        return carPage.map(car -> modelMapper.map(car, CarDto.class));
    }

    @Override
    public long countAll() {
        return carRepository.count();
    }


}
