package com.example.carrent.controllers.dashboard;

import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/chat")
@RequiredArgsConstructor
public class DashboardChatController {

    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getRecentUsers());
        return "dashboard/chat/index";
    }
}
