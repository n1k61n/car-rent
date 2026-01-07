package com.example.carrent.controllers.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "front/login";
    }

    @GetMapping("/register")
    public String register(){
        return "front/register";
    }

    @GetMapping("/forgot")
    public String forgot(){
        return "front/forgot";
    }



}
