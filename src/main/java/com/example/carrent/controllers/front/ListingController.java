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

@Controller
@RequiredArgsConstructor
public class ListingController {

    private final CarService carService;
    private final TestimonialService testimonialService;


    @GetMapping("/listing")
    public String getListings(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String brand,
                              @RequestParam(required = false) String pickup,
                              @RequestParam(required = false) String dropoff) {

        // Hər səhifədə 6 maşın
        Pageable pageable = PageRequest.of(page, 6);

        // Filtrasiya olunmuş maşınları gətiririk
        Page<Car> carPage = carService.searchCarsPageable(brand, pickup, dropoff, pageable);

        model.addAttribute("carPage", carPage);
        model.addAttribute("currentPage", page);

        // Filtr parametrlərini geri göndəririk ki, pagination linklərində istifadə edək
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("selectedPickup", pickup);
        model.addAttribute("selectedDropoff", dropoff);


        int count = 3;
        List<TestimonialDto> testimonialDtoListlist = testimonialService.getLastTestimonials(count);
        model.addAttribute("testimonials", testimonialDtoListlist);

        return "front/listing";
    }
}


