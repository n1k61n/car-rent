package com.example.carrent.controllers.front;

import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import com.example.carrent.dtos.comment.CommentDto;
import com.example.carrent.services.BlogService;
import com.example.carrent.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final CommentService commentService;

    @GetMapping()
    public String getBlogPage(@RequestParam(defaultValue = "0") int page,Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<BlogDto> blogs = blogService.getAllBlogs(pageable);
        model.addAttribute("blogs", blogs);
        return "front/blog/blog";
    }

    @PostMapping("/single/add-comment")
    public String addComment(@ModelAttribute CommentDto commentDto) {
        boolean result = commentService.saveComment(commentDto);
        return "redirect:/blog/single/" + commentDto.getBlogId();
    }


    @GetMapping("/single/{id}")
    public String getSingleBlog(@PathVariable Long id, Model model){
        BlogSingleDto blogSingleDto = blogService.getBlogById(id);
        model.addAttribute("blogSingle", blogSingleDto);
        return "front/blog/single";
    }

}
