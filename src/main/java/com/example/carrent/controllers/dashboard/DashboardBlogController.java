package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.blog.BlogDahboardCreateDto;
import com.example.carrent.dtos.blog.BlogDahboardUpdateDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import com.example.carrent.services.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/blog")
@RequiredArgsConstructor
public class DashboardBlogController {

    private final BlogService blogService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BlogSingleDto> blogPage = blogService.getAll(pageable);

        model.addAttribute("blogList", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());
        model.addAttribute("totalItems", blogPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "dashboard/blog/index";
    }


    @GetMapping("/create")
    public String showCreateForm() {
        return "dashboard/blog/create";
    }

    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute BlogDahboardCreateDto blogDahboardCreateDto) {
        boolean result = blogService.createBlog(blogDahboardCreateDto);
        return "redirect:/dashboard/blog/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BlogDahboardUpdateDto blogDahboardUpdateDto = blogService.getUpdateBlog(id);
        model.addAttribute("blog", blogDahboardUpdateDto);
        return "dashboard/blog/update";
    }

    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id, BlogDahboardUpdateDto blogDahboardUpdateDto){
        boolean result = blogService.updateBlog(id, blogDahboardUpdateDto);
        return "redirect:/dashboard/blog/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id){
        boolean result = blogService.deleteBlog(id);
        return "redirect:/dashboard/blog/index";
    }

}
