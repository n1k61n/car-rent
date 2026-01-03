package com.example.carrent.controllers;

import com.example.carrent.dtos.booking.BookingCreateDto;
import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.models.Car;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;


    @PostMapping("/booking/save")
    public String showDetailsPage(@Valid @ModelAttribute BookingDto bookingDto,
                                  BindingResult bindingResult, Model model) {
        // Debug: Terminalda yoxla
//        System.out.println("DTO gəldi: " + bookingDto);

        // Əgər validasiya xətası varsa (məsələn, keçmiş tarix göndərilibsə)
        if (bindingResult.hasErrors()) {
            return "redirect:/car-details/" + bookingDto.getCarId() + "?error=invalid_date";
        }

        LocalDate today = LocalDate.now();
        if(bookingDto.getStartDate().isBefore(today)){
            return "redirect:/car-details/" + bookingDto.getCarId() + "?error=past_date";
        }

        // 2. Məntiqi yoxlama: Qaytarma tarixi götürmədən əvvəldirmi?
        if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
            return "redirect:/car-details/" + bookingDto.getCarId() + "?error=invalid_range";
        }

        if (bookingDto.getCarId() == null) {
            return "redirect:/listing";
        }

        if (bookingDto.getStartDate() == null || bookingDto.getEndDate() == null) {
            return "redirect:/car-details/" + bookingDto.getCarId() + "?error=date_missing";
        }

        CarDto car = carService.getCarById(bookingDto.getCarId());

        if (car == null || car.getId() == null) {
            return "redirect:/listing";
        }

        long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        if (days <= 0) days = 1;

        //        // 3. Əgər istifadəçi login olubsa, onun məlumatlarını modelə əlavə edirik
//        // User currentUser = ... (Spring Security-dən al)
//        // model.addAttribute("user", currentUser);

        model.addAttribute("car", car);
        model.addAttribute("bookingDto", bookingDto);

        Double dailyPrice = car.getDailyPrice() != null ? car.getDailyPrice() : 0.0;
        model.addAttribute("totalPrice", days * dailyPrice);

        return "front/booking-details";
    }


    @PostMapping("/booking/complete")
    public String completeBooking(
            @ModelAttribute BookingCreateDto bookingCreateDto ,   RedirectAttributes redirectAttributes) {
        // Yoxlamaq üçün terminala çap et:
        System.out.println("Gələn məlumatlar: " + bookingCreateDto);
        try {
            // 1. Servis qatında sifarişi yarat
            // Burada həm maşını tapırıq, həm də müştəri məlumatlarını emal edirik
//            bookingService.finalizeBooking(bookingCreateDto);

            redirectAttributes.addFlashAttribute("message", "Təbriklər! Rezervasiyanız uğurla tamamlandı.");
            return "redirect:/success-page"; // Uğur səhifəsinə yönləndirmə

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xəta baş verdi: " + e.getMessage());
            return "redirect:/car/" + bookingCreateDto.getCarId();
        }
    }


}
