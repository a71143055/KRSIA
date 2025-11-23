package com.krsia.blog.controller;

import com.krsia.blog.entity.Post;
import com.krsia.blog.entity.User;
import com.krsia.blog.service.CommentService;
import com.krsia.blog.service.PostService;
import com.krsia.blog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public String add(@RequestParam Long postId,
                      @RequestParam String content,
                      @AuthenticationPrincipal UserDetails principal) {
        Post post = postService.find(postId);
        if (post == null) return "redirect:/posts";
        User author = userService.getByEmail(principal.getUsername());
        commentService.add(content, author, post);
        return "redirect:/posts/" + postId;
    }
}
