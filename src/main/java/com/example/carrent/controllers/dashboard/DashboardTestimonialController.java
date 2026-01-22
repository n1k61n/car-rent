package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.blog.BlogDahboardUpdateDto;
import com.example.carrent.dtos.testimonial.TestimonialCreateDto;
import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.dtos.testimonial.TestimonialUpdateDto;
import com.example.carrent.services.TestimonialService;
import jakarta.validation.Valid;
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

    @GetMapping("/create")
    public String showTestimonial() {
        return "dashboard/testimonial/create";
    }

    @PostMapping("/create")
    public String createTesstimonial(@Valid @ModelAttribute TestimonialCreateDto testimonialCreateDto){
        boolean result = testimonialService.create(testimonialCreateDto);
        return "redirect:/dashboard/testimonial/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        TestimonialUpdateDto testimonialUpdateDto = testimonialService.getTestimonial(id);
        model.addAttribute("testimonialUpdateDto", testimonialUpdateDto);
        return "dashboard/testimonial/update";
    }

    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id, TestimonialUpdateDto testimonialUpdateDto) {
        boolean result = testimonialService.updateTestimonial(id, testimonialUpdateDto);
        return "redirect:/dashboard/testimonial/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTestimonial(@PathVariable Long id) {
        boolean result = testimonialService.delete(id);
        return "redirect:/dashboard/testimonial/index";
    }

}
