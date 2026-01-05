package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/testimonial")
@RequiredArgsConstructor
public class DashboardTestimonialController {

    private final TestimonialService testimonialService;


    @GetMapping("/index")
    public String testimonial(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TestimonialDto> testimonialDtos = testimonialService.getTestimonils(pageable);
        model.addAttribute("testimonials", testimonialDtos.getContent());
        model.addAttribute("totalPages", testimonialDtos.getTotalPages());
        model.addAttribute("totalItems", testimonialDtos.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "dashboard/testimonial/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTestimonial(@PathVariable Long id) {
        boolean result = testimonialService.delete(id);
        return "redirect:/dashboard/testimonial/index";
    }


}
