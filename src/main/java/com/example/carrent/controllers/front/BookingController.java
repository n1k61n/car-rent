package com.example.carrent.controllers.front;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.dtos.booking.BookingDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.user.UserDto;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.CarService;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;


@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;
    private final UserService userService;


    @GetMapping("/booking/save")
    public String showBookingPage(@RequestParam(required = false) Long carId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (carId != null) {
            CarDto car = carService.getCarById(carId);
            model.addAttribute("car", car);
        }
        if (userDetails != null) {
            UserDto currentUser = userService.getUserByEmail(userDetails);
            model.addAttribute("user", currentUser);
        }
        return "front/booking";
    }

    @PostMapping("/booking/save")
    public String showDetailsPage(@Valid @ModelAttribute BookingDto bookingDto,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {



        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Tarixlər düzgün formatda deyil.");
            return "redirect:/car/" + bookingDto.getCarId();
        }


        if (bookingDto.getEndDate().isBefore(bookingDto.getStartDate())) {
            redirectAttributes.addFlashAttribute("error", "Qaytarma tarixi götürmə tarixindən əvvəl ola bilməz.");
            return "redirect:/car/" + bookingDto.getCarId();
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

        return "front/booking";
    }

    @PostMapping("/booking/complete")
    public String completeBooking(@ModelAttribute BookingCompleteDto bookingDto,
                                  @RequestParam("licenseFile") MultipartFile licenseFile,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  RedirectAttributes redirectAttributes)

    {
        System.out.println("DEBUG: Metoda girdi!");
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            boolean result = bookingService.completeBooking(bookingDto, licenseFile);
            if (result) {
                return "redirect:/booking/success";
            } else {
                redirectAttributes.addFlashAttribute("error", "Sifariş tamamlanarkən xəta baş verdi.");
                return "redirect:/booking/save?carId=" + bookingDto.getCarId();
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xəta: " + e.getMessage());
            // Əsas məqam: carId-ni ötürürük ki, GET metodu car obyektini yenidən tapsın
            return "redirect:/booking/save?carId=" + bookingDto.getCarId();
        }
    }

    @GetMapping("/booking/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("pageTitle", "Sifarişiniz Qəbul Edildi!");
        return "front/success";
    }
}
