package com.example.carrent.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/monitoring")
public class DashboardMonitoringController {

    @GetMapping("/index")
    public String showMonitoringPage(Model model) {
//        model.addAttribute("currentPage", "monitoring");
        return "dashboard/monitoring/index";
    }
}