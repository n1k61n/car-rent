package com.example.carrent.services.impl;

import com.example.carrent.dtos.category.CategoryDto;
import com.example.carrent.models.Category;
import com.example.carrent.repositories.CategoryRepository;
import com.example.carrent.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        categoryRepository.save(newCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            return modelMapper.map(category, CategoryDto.class);
        }
        return new CategoryDto();
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            category = new Category();
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }
    }
}
