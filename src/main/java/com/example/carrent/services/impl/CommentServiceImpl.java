package com.example.carrent.services.impl;

import com.example.carrent.dtos.comment.CommentDto;
import com.example.carrent.models.Blog;
import com.example.carrent.models.Comment;
import com.example.carrent.repositories.BlogRepository;
import com.example.carrent.repositories.CommentRepository;
import com.example.carrent.services.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public boolean saveComment(CommentDto commentDto) {
        if (commentDto.getBlogId() != null) {
            Blog blog = blogRepository.findById(commentDto.getBlogId())
                    .orElseThrow(() -> new RuntimeException("Blog yoxdur"));
            Comment comment = modelMapper.map(commentDto, Comment.class);
            comment.setId(null);
            comment.setBlog(blog);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }
}
