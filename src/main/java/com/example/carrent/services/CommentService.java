package com.example.carrent.services;

import com.example.carrent.dtos.comment.CommentDto;

public interface CommentService {

    boolean saveComment(CommentDto commentDto);
}
