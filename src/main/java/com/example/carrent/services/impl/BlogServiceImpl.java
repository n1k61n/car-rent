package com.example.carrent.services.impl;


import com.example.carrent.dtos.blog.BlogDahboardCreateDto;
import com.example.carrent.dtos.blog.BlogDahboardUpdateDto;
import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Blog;
import com.example.carrent.repositories.BlogRepository;
import com.example.carrent.repositories.CommentRepository;
import com.example.carrent.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl  implements BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;


    @Override
    public Page<BlogDto> getAllBlogs(Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogPage.map(blog -> modelMapper.map(blog, BlogDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public BlogSingleDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog tapılmadı. ID: " + id));
        return modelMapper.map(blog, BlogSingleDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogSingleDto> getAll(Pageable pageable) {
        Page<Blog> blogs =  blogRepository.findAll(pageable);
        return blogs.map(b -> modelMapper.map(b, BlogSingleDto.class));
    }



    @Override
    public boolean createBlog(BlogDahboardCreateDto dto) {
        Blog blog = modelMapper.map(dto, Blog.class);
        blog.setCreatedAt(LocalDate.now());
        blogRepository.save(blog);
        return true;
    }

    @Override
    public BlogDahboardUpdateDto getUpdateBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow();
        if(blog != null){
            return modelMapper.map(blog, BlogDahboardUpdateDto.class);
        }
        return null;
    }

    @Override
    public boolean updateBlog(Long id, BlogDahboardUpdateDto dto) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yenilənəcək blog tapılmadı. ID: " + id));
        modelMapper.map(dto, existingBlog);
        existingBlog.setId(id);
        blogRepository.save(existingBlog);
        return true;
    }

    @Override
    public boolean deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Silinəcək blog tapılmadı. ID: " + id);
        }
        blogRepository.deleteById(id);
        return true;
    }

}
