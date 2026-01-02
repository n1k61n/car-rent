package com.example.carrent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {



    @GetMapping("/login")
    public String login(){
        return "dashboard/login";
    }

    @GetMapping("/register")
    public String register(){
        return "dashboard/register";
    }

    @GetMapping("/forgot")
    public String forgot(){
        return "dashboard/forgot";
    }

}
