package com.example.carrent.controllers.front;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.user.UserBookingDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.CarService;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;
    private final UserService userService;


    @GetMapping("/booking/save")
    public String showBookingPage(@ModelAttribute("bookingDto") BookingDto bookingDto,
                                  Model model,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {

        if (bookingDto.getCarId() == null) {
            return "redirect:/listing";
        }

        return prepareBookingModel(bookingDto, model, authentication, redirectAttributes);
    }

    @PostMapping("/booking/save")
    public String processBookingSelection(@Valid @ModelAttribute("bookingDto") BookingDto bookingDto,
                                          BindingResult bindingResult,
                                          Model model,
                                          Authentication authentication,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Validasiya xətası varsa (məsələn: tarixlər boşdursa), yenidən səhifəni yüklə
            return prepareBookingModel(bookingDto, model, authentication, redirectAttributes);
        }

        if (bookingDto.getEndDate() != null && bookingDto.getStartDate() != null) {
            if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
                model.addAttribute("error", "Qaytarma tarixi götürmə tarixindən əvvəl ola bilməz.");
            }
        }

        return prepareBookingModel(bookingDto, model, authentication, redirectAttributes);
    }


    private String prepareBookingModel(BookingDto bookingDto, Model model,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {

        CarDto car = carService.getCarById(bookingDto.getCarId());
        if (car == null) return "redirect:/listing";

        // Qiymət və gün hesablama
        if (bookingDto.getStartDate() != null && bookingDto.getEndDate() != null &&
                !bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {

            long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
            if (days <= 0) days = 1;

            model.addAttribute("days", days);
            BigDecimal dailyPrice = car.getDailyPrice() != null ? car.getDailyPrice() : BigDecimal.ZERO;
            model.addAttribute("totalPrice", dailyPrice.multiply(BigDecimal.valueOf(days)));
        } else {
            model.addAttribute("days", 0);
            model.addAttribute("totalPrice", BigDecimal.ZERO);
        }

        model.addAttribute("car", car);
        populateUserData(model, authentication);

        return "front/catalog/booking";
    }

    private void populateUserData(Model model, Authentication authentication) {
        try {
            UserBookingDto user = userService.getUserByEmail(authentication.getName());
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", "İstifadəçi məlumatları yüklənərkən xəta: " + e.getMessage());
        }

    }

    @PostMapping("/booking/complete")
    public String completeBooking(@ModelAttribute BookingCompleteDto bookingDto,
                                  @RequestParam("licenseFile") MultipartFile file,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {

        // Əgər fayl yoxdursa (GET sorğusu və ya fayl seçilməyib), geri qaytarırıq
        if (file == null || file.isEmpty()) {
            // Parametrləri URL-ə əlavə edirik ki, istifadəçi yenidən daxil etməsin
            return "redirect:/booking/save?carId=" + bookingDto.getCarId() +
                   "&startDate=" + bookingDto.getStartDate() +
                   "&endDate=" + bookingDto.getEndDate() +
                   "&pickupLocation=" + (bookingDto.getPickupLocation() != null ? bookingDto.getPickupLocation() : "");
        }

        try {
            Long bookingId = bookingService.completeBooking(bookingDto, file);
            return "redirect:/booking/payment/" + bookingId;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Xəta baş verdi: " + e.getMessage());
        }
        return "redirect:/booking/save?carId=" + bookingDto.getCarId();
    }

    @GetMapping("/booking/payment/{id}")
    public String showPaymentPage(@PathVariable Long id, Model model) {
        model.addAttribute("bookingId", id);
        return "front/catalog/payment";
    }

    @PostMapping("/booking/process-payment")
    public String processPayment(@RequestParam("bookingId") Long bookingId, RedirectAttributes redirectAttributes) {
        try {
            bookingService.updateStatus(bookingId, BookingStatus.PENDING);
            return "redirect:/booking/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment failed: " + e.getMessage());
            return "redirect:/booking/payment/" + bookingId;
        }
    }


    @GetMapping("/booking/success")
    public String showSuccessPage() {
        return "front/catalog/success";
    }
}
