package com.example.carrent.controllers;

import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/car/{id}")
    public String getCarDetails(@PathVariable Long id, Model model) {
        CarDto car = carService.getCarById(id);
        System.out.println("Gələn maşın: " + car);

        if (car == null) {
            return "error/404"; // Maşın tapılmadıqda
        }

        model.addAttribute("car", car);
        return "front/car-details";
    }
}
