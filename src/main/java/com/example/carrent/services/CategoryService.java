package com.example.carrent.services;

import com.example.carrent.dtos.category.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    void createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long id);

    void updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);
}
