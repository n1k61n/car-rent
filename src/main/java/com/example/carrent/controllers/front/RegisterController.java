package com.example.carrent.controllers.front;


import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.services.OtpService;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final OtpService otpService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "front/auth/login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "front/auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "front/auth/register";
        }

        // 2. Şifrələrin eyniliyini yoxla
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            // Xüsusi xəta mesajını confirmPassword sahəsinə bağlayırıq
            bindingResult.rejectValue("confirmPassword", "error.userDto", "Şifrələr bir-biri ilə eyni deyil!");
            return "front/auth/register";
        }

        // 3. Email-in unikal olmasını yoxla
        if (userService.existsByEmail(registrationDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userDto", "Bu email artıq qeydiyyatdan keçib!");
            return "front/auth/register";
        }

        // 4. Qeydiyyat prosesi
        try {

            userService.registerNewUser(registrationDto);
            redirectAttributes.addFlashAttribute("email", registrationDto.getEmail());
            return "redirect:/verify-otp";
        } catch (Exception e) {
            model.addAttribute("error", "Qeydiyyat zamanı gözlənilməz xəta baş verdi.");
            return "front/auth/register";
        }
    }

    @GetMapping("/verify-otp")
    public String showVerifyOtpForm(Model model) {
        if (!model.containsAttribute("email")) {
            return "redirect:/register";
        }
        return "front/auth/verify-otp";
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String code, Model model) {
        boolean isValid = otpService.verifyOtp(email, code);

        if (isValid) {
            userService.enableUser(email);
            return "front/auth/registration-success";
        } else {
            model.addAttribute("error", "Kod yanlışdır.");
            model.addAttribute("email", email);
            return "front/auth/verify-otp";
        }
    }

    @GetMapping("/auth/resend")
    public String resendOtp(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            otpService.createAndSendOtp(email);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("message", "Yeni kod göndərildi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Kod göndərilərkən xəta baş verdi.");
        }
        return "redirect:/verify-otp";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "front/auth/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.resetPasswordToRandom(email);
            redirectAttributes.addFlashAttribute("message", "Yeni şifrə emailinizə göndərildi. Zəhmət olmasa emailinizi yoxlayın.");
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("error", "Email tapılmadı.");
            return "front/auth/forgot_password";
        }
    }
}
