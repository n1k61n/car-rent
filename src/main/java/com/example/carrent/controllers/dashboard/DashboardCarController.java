package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.car.CarCreateDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/car")
@RequiredArgsConstructor
public class DashboardCarController {

    private final CarService carService;

    @GetMapping("/index")
    public String cars(Model model){
        List<CarDto> carDtoList = carService.getAllCars();
        model.addAttribute("carList", carDtoList);
        return "dashboard/car/index";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "dashboard/car/create";
    }

    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute CarCreateDto carCreateDto) {
        System.out.println(carCreateDto);
        // carService.save(carDto);
        return "redirect:/dashboard/car/index";
    }

}
