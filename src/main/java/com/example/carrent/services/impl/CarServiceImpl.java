package com.example.carrent.services.impl;

import com.example.carrent.dtos.car.CarCreateDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.car.CarUpdateDto;
import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Car;
import com.example.carrent.models.Category;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findByAvailableTrue(pageable);
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
    public Page<Car> searchCarsPageable(String brand, String category, Integer passengerCount, Pageable pageable) {
        String brandFilter = (brand == null || brand.isEmpty() || brand.equals("Select Type")) ? null : brand;
        String categoryFilter = (category == null || category.isEmpty()) ? null : category;
        Integer passengerCountFilter = (passengerCount == null || passengerCount <= 0) ? null : passengerCount;

        return carRepository.findAvailableCars(brandFilter, categoryFilter, passengerCountFilter, pageable);
    }


    @Override
    public boolean createCar(CarCreateDto carCreateDto) {
        try {
            Car car = modelMapper.map(carCreateDto, Car.class);
            
            if (carCreateDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(carCreateDto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kateqoriya tapılmadı"));
                car.setCategory(category);
            }
            
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
            CarUpdateDto dto = modelMapper.map(car, CarUpdateDto.class);
            if (car.getCategory() != null) {
                dto.setCategoryId(car.getCategory().getId());
            }
            return dto;
        }
        return null;
    }

    @Override
    public boolean updateCar(Long id, CarUpdateDto carUpdateDto) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()){
            Car existCar = optionalCar.get();
            modelMapper.map(carUpdateDto, existCar);
            
            if (carUpdateDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(carUpdateDto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kateqoriya tapılmadı"));
                existCar.setCategory(category);
            }

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
