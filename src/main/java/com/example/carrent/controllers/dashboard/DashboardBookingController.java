package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.booking.BookingOrdersDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/bookings")
@RequiredArgsConstructor
public class DashboardBookingController {

    private final BookingService bookingService;

    @GetMapping("/index")
    public String index(Model model) {
//        List<BookingOrdersDto> bookingOrdersDto =  bookingService.getAllOrders();
        List<BookingOrdersDto> bookingOrdersDto =  bookingService.getAllActiveOrders();
        model.addAttribute("bookings", bookingOrdersDto);
        return "dashboard/bookings/index";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        boolean result = bookingService.updateStatus(id, status);
        return "redirect:/dashboard/bookings/index";
    }
}
