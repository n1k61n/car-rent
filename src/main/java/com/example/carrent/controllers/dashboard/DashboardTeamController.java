package com.example.carrent.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/team")
public class DashboardTeamController {


    @GetMapping("/index")
    public String dashboard(){
        return "dashboard/team/index";
    }


}
