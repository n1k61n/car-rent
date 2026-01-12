package com.example.carrent.controllers.front;


import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.services.OtpService;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final OtpService otpService;

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
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "front/register";
        }

        // 2. Şifrələrin eyniliyini yoxla
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            // Xüsusi xəta mesajını confirmPassword sahəsinə bağlayırıq
            bindingResult.rejectValue("confirmPassword", "error.userDto", "Şifrələr bir-biri ilə eyni deyil!");
            return "front/register";
        }

        // 3. Email-in unikal olmasını yoxla
        if (userService.existsByEmail(registrationDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userDto", "Bu email artıq qeydiyyatdan keçib!");
            return "front/register";
        }

        // 4. Qeydiyyat prosesi
        try {
            userService.registerNewUser(registrationDto);
            return "front/verify-otp";
        } catch (Exception e) {
            model.addAttribute("error", "Qeydiyyat zamanı gözlənilməz xəta baş verdi.");
            return "front/register";
        }
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String code, Model model) {
        boolean isValid = otpService.verifyOtp(email, code);

        if (isValid) {
            userService.enableUser(email);
            return "registration-success";
        } else {
            model.addAttribute("error", "Kod yanlışdır.");
            return "verify-otp";
        }
    }

}
