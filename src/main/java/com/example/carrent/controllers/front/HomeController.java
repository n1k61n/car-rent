package com.example.carrent.controllers.front;


import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.models.Car;
import com.example.carrent.services.CarService;
import com.example.carrent.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final CarService carService;
    private final TestimonialService testimonialService;


    @GetMapping()
    public String home(Model model,  @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Car> carPage = carService.findAll(pageable);
        model.addAttribute("carPage", carPage);
        int count = 3;
        List<TestimonialDto> testimonialDtoListlist = testimonialService.getLastTestimonials(count);
        model.addAttribute("testimonials", testimonialDtoListlist);
        Set<String> brands = carService.getAllCarTypes();
        model.addAttribute("brands", brands);
        return "front/index";
    }

    @GetMapping("/contact")
    public String contact(){
        return "front/contact";
    }
}