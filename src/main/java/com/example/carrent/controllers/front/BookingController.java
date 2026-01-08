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
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CarService carService;

    @RequestMapping(value = "/booking/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetailsPage(@Valid @ModelAttribute("bookingDto") BookingDto bookingDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors() || bookingDto.getCarId() == null) {
            return "redirect:/listing";
        }

        CarDto car = carService.getCarById(bookingDto.getCarId());
        if (car == null) return "redirect:/listing";

        long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        model.addAttribute("car", car);
        model.addAttribute("days", days <= 0 ? 1 : days);
        model.addAttribute("totalPrice", (days <= 0 ? 1 : days) * (car.getDailyPrice() != null ? car.getDailyPrice() : 0.0));

        return "front/booking";
    }

    @PostMapping("/booking/complete")
    public String completeBooking(@ModelAttribute BookingCompleteDto bookingDto,
                                  @RequestParam("licenseFile") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {


        try {
            boolean result = bookingService.completeBooking(bookingDto, file);
            if (result) return "redirect:/booking/success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/booking/save?carId=" + bookingDto.getCarId();
    }


    @GetMapping("/booking/success")
    public String showSuccessPage() {
        return "front/success";
    }
}
