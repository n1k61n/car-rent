package com.example.carrent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front")
public class HomeController {


    @GetMapping("/index")
    public String home() {
        return "index";
    }
}