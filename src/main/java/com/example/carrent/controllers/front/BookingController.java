package com.example.carrent.controllers.front;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingCreateDto;
import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;

    @PostMapping("/booking/save")
    public String showDetailsPage(@Valid @ModelAttribute BookingDto bookingDto,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Tarixlər düzgün formatda deyil.");
            return "redirect:/car-details/" + bookingDto.getCarId();
        }


        if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
            redirectAttributes.addFlashAttribute("error", "Qaytarma tarixi götürmə tarixindən əvvəl ola bilməz.");
            return "redirect:/car-details/" + bookingDto.getCarId();
        }

        CarDto car = carService.getCarById(bookingDto.getCarId());
        if (car == null) {
            return "redirect:/listing";
        }

        long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        if (days <= 0) days = 1;


        model.addAttribute("car", car);
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("days", days);

        Double dailyPrice = car.getDailyPrice() != null ? car.getDailyPrice() : 0.0;
        model.addAttribute("totalPrice", days * dailyPrice);

        return "front/booking-details";
    }

    @PostMapping("/booking/complete")
    public String completeBooking(@ModelAttribute BookingCompleteDto bookingDto,
                                  RedirectAttributes redirectAttributes) {
        System.out.println(bookingDto);
        try {
            boolean result = bookingService.completeBooking(bookingDto);
            redirectAttributes.addFlashAttribute("success", "Sifarişiniz uğurla tamamlandı!");
            return "redirect:/booking/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xəta baş verdi: " + e.getMessage());
            return "redirect:/booking/details";
        }
    }

    @GetMapping("/booking/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("pageTitle", "Sifarişiniz Qəbul Edildi!");
        return "front/success";
    }
}
