package com.example.carrent.controllers.front;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserProfileUpdateDto;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/profile/update")
    public String profileUpdate(Principal principal, UserProfileUpdateDto userProfileUpdateDto){

        boolean result = userService.updateProfile(principal.getName(), userProfileUpdateDto);
        return "redirect:/profile";
    }



    @GetMapping("/bookings")
    public String showBookings(Model model, Principal principal) {
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("bookings", user.getBookings());
        return "front/user_bookings";
    }
}