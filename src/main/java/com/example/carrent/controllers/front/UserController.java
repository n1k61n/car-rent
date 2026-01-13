package com.example.carrent.controllers.front;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserProfileUpdateDto;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "front/account/profile";
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
        return "front/account/user_bookings";
    }


    @PostMapping("/bookings/delete")
    public String deleteBooking(@RequestParam("id") Long id, Principal principal){
        boolean result = userService.deleteBooking(id, principal.getName());
        return "redirect:/bookings";
    }



}