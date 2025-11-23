package com.krsia.blog.service;

import com.krsia.blog.entity.Comment;
import com.krsia.blog.entity.Post;
import com.krsia.blog.entity.User;
import com.krsia.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment add(String content, User author, Post post) {
        return commentRepository.save(new Comment(content, author, post));
    }
}
