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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final OtpService otpService;

    private String sanitizeEmail(String email) {
        if (email != null && email.contains(",")) {
            return email.split(",")[0].trim();
        }
        return email;
    }

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
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "front/auth/register";
        }

        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userDto", "Şifrələr bir-biri ilə eyni deyil!");
            return "front/auth/register";
        }

        if (userService.existsByEmail(registrationDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userDto", "Bu email artıq qeydiyyatdan keçib!");
            return "front/auth/register";
        }

        try {
            userService.registerNewUser(registrationDto);
            return "redirect:/verify-otp?email=" + registrationDto.getEmail();
        } catch (Exception e) {
            model.addAttribute("error", "Qeydiyyat zamanı gözlənilməz xəta baş verdi.");
            return "front/auth/register";
        }
    }

    @GetMapping("/verify-otp")
    public String showVerifyOtpForm(@RequestParam(name = "email", required = false) String email, Model model) {
        String sanitizedEmail = sanitizeEmail(email);
        if (sanitizedEmail != null && !sanitizedEmail.isEmpty()) {
            model.addAttribute("email", sanitizedEmail);
        }

        if (!model.containsAttribute("email")) {
            return "redirect:/register";
        }
        return "front/auth/verify-otp";
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String code, RedirectAttributes redirectAttributes) {
        String sanitizedEmail = sanitizeEmail(email);
        try {
            otpService.verifyOtp(sanitizedEmail, code);
            userService.enableUser(sanitizedEmail);
            return "front/auth/registration-success";
        } catch (RuntimeException e) {
            String errorMsg;
            if ("EXPIRED_CODE".equals(e.getMessage())) {
                errorMsg = "Bu kodun istifadə vaxtı bitib. Zəhmət olmasa yeni kod istəyin.";
            } else if ("INVALID_CODE".equals(e.getMessage())) {
                errorMsg = "Daxil etdiyiniz kod yanlışdır.";
            } else {
                errorMsg = "Xəta baş verdi. Yenidən yoxlayın.";
            }

            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/verify-otp?email=" + sanitizedEmail;
        }
    }

    @GetMapping("/auth/resend")
    public String resendOtp(@RequestParam String email, RedirectAttributes redirectAttributes) {
        String sanitizedEmail = sanitizeEmail(email);
        try {
            otpService.createAndSendOtp(sanitizedEmail);
            redirectAttributes.addFlashAttribute("message", "Yeni kod göndərildi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Kod göndərilərkən xəta baş verdi.");
        }
        return "redirect:/verify-otp?email=" + sanitizedEmail;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "front/auth/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Email ünvanı boş ola bilməz.");
            return "front/auth/forgot_password";
        }
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