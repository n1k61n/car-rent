package com.example.carrent.controllers.front;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "front/profile";
    }



    @GetMapping("/bookings")
    public String showBookings(Model model, Principal principal) {
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("bookings", user.getBookings());
        return "front/user_bookings";
    }
}