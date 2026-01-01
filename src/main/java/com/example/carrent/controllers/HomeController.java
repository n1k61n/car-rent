package com.example.carrent.controllers;


import com.example.carrent.models.Car;
import com.example.carrent.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CarService carService;


    @GetMapping()
    public String home() {
        return "front/index";
    }

    @GetMapping("/listing")
    public String getListings(Model model, @RequestParam(defaultValue = "0") int page) {
        // Hər səhifədə 6 maşın göstərmək üçün
        Pageable pageable = PageRequest.of(page, 6);
        Page<Car> carPage = carService.findAll(pageable);

        model.addAttribute("carPage", carPage);
        model.addAttribute("currentPage", page);
        return "front/listing";
    }

    @GetMapping("/testimonials")
    public String testimonials() {
        return "front/testimonials";
    }


    @GetMapping("/blog")
    public String blog(){
        return "front/blog";
    }

    @GetMapping("/about")
    public String about(){
        return "front/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "front/contact";
    }



}