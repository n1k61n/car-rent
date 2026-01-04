package com.example.carrent.services.impl;


import com.example.carrent.dtos.blog.BlogDto;
import com.example.carrent.dtos.blog.BlogSingleDto;
import com.example.carrent.dtos.comment.CommentDto;
import com.example.carrent.models.Blog;
import com.example.carrent.models.Comment;
import com.example.carrent.repositories.BlogRepository;
import com.example.carrent.repositories.CommentRepository;
import com.example.carrent.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public BlogSingleDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow();

        // Bloqa aid şərhləri bazadan çəkirik
        List<Comment> comments = commentRepository.findByBlogIdOrderByCreatedAtDesc(id);

        // Şərhləri DTO-ya çeviririk
        List<CommentDto> commentDtos = comments.stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .toList();

        // BlogSingleDto-nu qururuq
        return BlogSingleDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .author(blog.getAuthor())
                .comments(commentDtos) // Şərhləri buraya əlavə edirik
                .build();
    }

}
