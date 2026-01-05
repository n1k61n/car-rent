package com.example.carrent.services;

import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<BlogDto> getAllBlogs(Pageable pageable);
    BlogSingleDto getBlogById(Long id);
}
