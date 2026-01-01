package com.example.carrent.controllers;


import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;


    @GetMapping("/testimonials")
    public String testimonials(Model model) {
        List<TestimonialDto> list = testimonialService.getLastTestimonials();
        model.addAttribute("testimonials", list);
        return "front/testimonials";
    }


}
