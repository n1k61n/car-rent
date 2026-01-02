package com.example.carrent.controllers;

import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blog")
    public String getBlogPage(@RequestParam(defaultValue = "0") int page,Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<BlogDto> blogs = blogService.getAllBlogs(pageable);

        model.addAttribute("blogs", blogs);
        return "front/blog";
    }
}
