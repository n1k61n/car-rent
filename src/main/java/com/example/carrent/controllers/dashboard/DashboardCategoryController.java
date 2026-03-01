package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.category.CategoryDto;
import com.example.carrent.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/category")
@RequiredArgsConstructor
public class DashboardCategoryController {


    private final CategoryService categoryService;

    @GetMapping("/index")
    public String index(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "dashboard/category/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "dashboard/category/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return "redirect:/dashboard/category/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDto);
        return "dashboard/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
        return "redirect:/dashboard/category/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/dashboard/category/index";
    }

}
