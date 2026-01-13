package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserProfileUpdateDto;
import com.example.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {
        UserProfileDto user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);

        if (!model.containsAttribute("userProfileUpdateDto")) {
            UserProfileUpdateDto updateDto = new UserProfileUpdateDto();
            updateDto.setFirstName(user.getFirstName());
            updateDto.setLastName(user.getLastName());
            updateDto.setPhoneNumber(user.getPhoneNumber());
            model.addAttribute("userProfileUpdateDto", updateDto);
        }
        return "dashboard/admin/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Principal principal,
                                @Valid UserProfileUpdateDto userProfileUpdateDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateDto", bindingResult);
            redirectAttributes.addFlashAttribute("userProfileUpdateDto", userProfileUpdateDto);
            return "redirect:/dashboard/admin/profile";
        }

        boolean result = userService.updateProfile(principal.getName(), userProfileUpdateDto);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Profiliniz uğurla yeniləndi.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Profil yenilənərkən xəta baş verdi.");
        }
        return "redirect:/dashboard/admin/profile";
    }

    @GetMapping("/settings")
    public String showSettingsPage() {
        return "dashboard/admin/settings";
    }

    @GetMapping("/activity-log")
    public String showActivityLogPage() {
        return "dashboard/admin/activity-log";
    }
}
