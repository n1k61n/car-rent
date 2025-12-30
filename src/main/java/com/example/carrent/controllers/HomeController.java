package com.example.carrent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @GetMapping()
    public String home() {
        return "front/index";
    }

    @GetMapping("/listing")
    public String listing() {
        return "front/listing";
    }

    @GetMapping("/testimonials")
    public String testimonials() {
        return "front/testimonials";
    }


    @GetMapping("/blog")
    public String blog(){
        return "front/blog";
    }

    @GetMapping("/about")
    public String about(){
        return "front/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "front/contact";
    }



}