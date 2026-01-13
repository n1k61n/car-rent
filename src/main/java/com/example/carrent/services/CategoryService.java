package com.example.carrent.services;

import com.example.carrent.dtos.category.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
}
