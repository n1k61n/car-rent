package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.user.UsersDashboardDto;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/user")
@RequiredArgsConstructor
public class DashboardUserController {

    private final UserService userService;



    @GetMapping("/index")
    public String listUsers(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String keyword) {

        Page<UsersDashboardDto> userPage = userService.findPaginated(page, size, keyword);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);

        return "dashboard/user/index";
    }

    @GetMapping("/details/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        UsersDashboardDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "dashboard/user/details";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleUserStatus(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return "redirect:/dashboard/user/index";
    }

    @PostMapping("/make-admin/{id}")
    public String makeAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.assignAdminRole(id);
        redirectAttributes.addFlashAttribute("message", "İstifadəçi rolu ADMIN olaraq dəyişdirildi.");
        return "redirect:/dashboard/user/index";
    }
}
