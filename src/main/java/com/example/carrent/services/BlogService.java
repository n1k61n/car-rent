package com.example.carrent.services;

import com.example.carrent.dtos.blog.BlogDahboardCreateDto;
import com.example.carrent.dtos.blog.BlogDahboardUpdateDto;
import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<BlogDto> getAllBlogs(Pageable pageable);
    BlogSingleDto getBlogById(Long id);

    Page<BlogSingleDto> getAll(Pageable pageable);

    boolean createBlog(@Valid BlogDahboardCreateDto blogDahboardCreateDto);


    BlogDahboardUpdateDto getUpdateBlog(Long id);

    boolean updateBlog(Long id, BlogDahboardUpdateDto blogDahboardUpdateDto);

    boolean deleteBlog(Long id);
}
