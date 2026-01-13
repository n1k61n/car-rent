package com.example.carrent.controllers.front;

import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import com.example.carrent.dtos.comment.CommentDto;
import com.example.carrent.services.BlogService;
import com.example.carrent.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String addComment(@Valid @ModelAttribute("commentDto") CommentDto commentDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Xətaları və köhnə daxil edilmiş məlumatları flash atribut olaraq göndəririk
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentDto", bindingResult);
            redirectAttributes.addFlashAttribute("commentDto", commentDto);
            return "redirect:/blog/single/" + commentDto.getBlogId() + "#comment-section";
        }

        boolean result = commentService.saveComment(commentDto);
        if (result) {
            redirectAttributes.addFlashAttribute("commentSuccess", "Şərhiniz uğurla əlavə edildi və yoxlanıldıqdan sonra dərc olunacaq.");
        } else {
            redirectAttributes.addFlashAttribute("commentError", "Şərh əlavə edilərkən xəta baş verdi.");
        }

        return "redirect:/blog/single/" + commentDto.getBlogId();
    }


    @GetMapping("/single/{id}")
    public String getSingleBlog(@PathVariable Long id, Model model){
        BlogSingleDto blogSingleDto = blogService.getBlogById(id);
        model.addAttribute("blogSingle", blogSingleDto);
        
        // Əgər flash atributda commentDto yoxdursa, yeni boş bir obyekt yaradırıq
        if (!model.containsAttribute("commentDto")) {
            model.addAttribute("commentDto", new CommentDto());
        }

        return "front/blog/single";
    }

}
