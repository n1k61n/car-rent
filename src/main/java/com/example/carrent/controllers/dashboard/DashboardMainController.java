package com.example.carrent.controllers.dashboard;

import com.example.carrent.models.Car;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.CarService;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardMainController {

    private final CarService carService;
    private final BookingService bookingService;
    private final UserService userService;



    @GetMapping()
    public String dashboard(Model model){
        model.addAttribute("carsCount", carService.countAll());
        model.addAttribute("bookingsCount", bookingService.countActive());
        model.addAttribute("usersCount", userService.countAll());
        double monthEarning = bookingService.getMonthEarnings();
        model.addAttribute("monthEarnings", monthEarning);


        Map<String, Double> monthlyEarnings = bookingService.getMonthlyEarnings();

        model.addAttribute("labels", monthlyEarnings.keySet());
        model.addAttribute("data", monthlyEarnings.values());

        return "dashboard/index";
    }



}
