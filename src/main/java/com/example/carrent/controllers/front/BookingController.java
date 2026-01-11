package com.example.carrent.controllers.front;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.dtos.car.CarDto;
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

import java.security.Principal;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;


    @PostMapping("/booking/save")
    public String showBookingPage(@Valid @ModelAttribute("bookingDto") BookingDto bookingDto,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Tarixlər düzgün formatda deyil.");
            return "redirect:/car/" + bookingDto.getCarId();
        }

        if (bookingDto.getCarId() == null) {
            return "redirect:/listing";
        }

        CarDto car = carService.getCarById(bookingDto.getCarId());
        if (car == null) return "redirect:/listing";

        // Tarix yoxlanışı
        if (bookingDto.getStartDate() == null || bookingDto.getEndDate() == null) {
            redirectAttributes.addFlashAttribute("error", "Zəhmət olmasa tarixləri seçin.");
            return "redirect:/car/" + bookingDto.getCarId();
        }

        if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
            redirectAttributes.addFlashAttribute("error", "Qaytarma tarixi götürmə tarixindən əvvəl ola bilməz.");
            return "redirect:/car/" + bookingDto.getCarId();
        }

        long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        if (days <= 0) days = 1;

        model.addAttribute("car", car);
        model.addAttribute("days", days);
        Double dailyPrice = car.getDailyPrice() != null ? car.getDailyPrice() : 0.0;
        model.addAttribute("totalPrice", days * dailyPrice);

        return "front/booking";
    }

    @PostMapping("/booking/complete")
    public String completeBooking(@ModelAttribute BookingCompleteDto bookingDto,
                                  @RequestParam("licenseFile") MultipartFile file,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        String userEmail = principal.getName();

        // Əgər fayl yoxdursa (GET sorğusu və ya fayl seçilməyib), geri qaytarırıq
        if (file == null || file.isEmpty()) {
            // Parametrləri URL-ə əlavə edirik ki, istifadəçi yenidən daxil etməsin
            return "redirect:/booking/save?carId=" + bookingDto.getCarId() +
                   "&startDate=" + bookingDto.getStartDate() +
                   "&endDate=" + bookingDto.getEndDate() +
                   "&pickupLocation=" + (bookingDto.getPickupLocation() != null ? bookingDto.getPickupLocation() : "");
        }

        try {
            boolean result = bookingService.completeBooking(bookingDto, file);
            if (result) return "redirect:/booking/success";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Xəta baş verdi: " + e.getMessage());
        }
        return "redirect:/booking/save?carId=" + bookingDto.getCarId();
    }


    @GetMapping("/booking/success")
    public String showSuccessPage() {
        return "front/success";
    }
}
