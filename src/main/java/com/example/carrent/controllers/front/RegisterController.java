package com.example.carrent.controllers.front;


import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "front/login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "front/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserRegistrationDto registrationDto, Model model) {

        if (userService.existsByEmail(registrationDto.getEmail())) {
            model.addAttribute("error", "Bu email artıq istifadə olunub!");
            return "front/register";
        }

        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("error", "Şifrələr eyni deyil!");
            return "front/register";
        }

        boolean result = userService.registerNewUser(registrationDto);

        return "redirect:/login?success";
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserProfileDto currentUser, Model model) {
        model.addAttribute("user", currentUser);
        return "front/profile";
    }

}
