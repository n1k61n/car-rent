package com.example.carrent.controllers.front;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserProfileUpdateDto;
import com.example.carrent.services.BookingService;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        
        if (!model.containsAttribute("userProfileUpdateDto")) {
            UserProfileUpdateDto updateDto = new UserProfileUpdateDto();
            updateDto.setFirstName(user.getFirstName());
            updateDto.setLastName(user.getLastName());
            updateDto.setPhoneNumber(user.getPhoneNumber());
            model.addAttribute("userProfileUpdateDto", updateDto);
        }
        return "front/account/profile";
    }

    @PostMapping("/profile/update")
    public String profileUpdate(Principal principal, 
                                @Valid UserProfileUpdateDto userProfileUpdateDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){
        if (principal == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateDto", bindingResult);
            redirectAttributes.addFlashAttribute("userProfileUpdateDto", userProfileUpdateDto);
            return "redirect:/profile";
        }

        boolean result = userService.updateProfile(principal.getName(), userProfileUpdateDto);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Profiliniz uğurla yeniləndi.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Profil yenilənərkən xəta baş verdi.");
        }
        return "redirect:/profile";
    }



    @GetMapping("/bookings")
    public String showBookings(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("bookings", user.getBookings());
        return "front/account/user_bookings";
    }


    @PostMapping("/bookings/delete")
    public String deleteBooking(@RequestParam("id") Long id, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        boolean result = bookingService.deleteBooking(id, principal.getName());
        return "redirect:/bookings";
    }



}