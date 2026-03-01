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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<Car> findAll(Pageable pageable) {
        log.debug("Fetching all available cars");
        return carRepository.findByAvailableTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public CarDto getCarById(Long id) {
        log.debug("Fetching car by ID: {}", id);
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
        log.debug("Searching cars. Brand: {}, Category: {}, Passengers: {}", brand, category, passengerCount);
        String brandFilter = (brand == null || brand.isEmpty() || brand.equals("Select Type")) ? null : brand;
        String categoryFilter = (category == null || category.isEmpty()) ? null : category;
        Integer passengerCountFilter = (passengerCount == null || passengerCount <= 0) ? null : passengerCount;

        return carRepository.findAvailableCars(brandFilter, categoryFilter, passengerCountFilter, pageable);
    }


    @Override
    @Transactional
    public boolean createCar(CarCreateDto carCreateDto) {
        log.info("Creating new car: {} {}", carCreateDto.getBrand(), carCreateDto.getModel());
        try {
            Car car = modelMapper.map(carCreateDto, Car.class);
            car.setId(null);
            
            if (carCreateDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(carCreateDto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kateqoriya tapılmadı"));
                car.setCategory(category);
            }
            
            car.setAvailable(true);
            carRepository.save(car);
            log.info("Car created successfully. ID: {}", car.getId());
            return true;
        } catch (Exception e) {
            log.error("Error creating car", e);
            return false;
        }
    }

    @Transactional
    public boolean deleteCar(Long carId) {
        log.info("Deleting car ID: {}", carId);
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Avtomobil tapılmadı: " + carId));
        bookingRepository.deleteByCarId(carId);
        carRepository.delete(car);
        log.info("Car deleted successfully. ID: {}", carId);
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
    @Transactional
    public boolean updateCar(Long id, CarUpdateDto carUpdateDto) {
        log.info("Updating car ID: {}", id);

        Car existCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avtomobil tapılmadı: " + id));

        modelMapper.map(carUpdateDto, existCar);
        existCar.setId(id); // Restore ID because modelMapper overwrites it if carUpdateDto.id is null

        // Obyektin ID-sini dəyişməyin, Hibernate id-ni idarə edir
        if (carUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(carUpdateDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Kateqoriya tapılmadı"));
            existCar.setCategory(category);
        }

        carRepository.save(existCar);
        log.info("Car updated successfully. ID: {}", id);
        return true;
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
