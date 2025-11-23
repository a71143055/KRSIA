package com.krsia.blog.controller;

import com.krsia.blog.entity.Post;
import com.krsia.blog.entity.User;
import com.krsia.blog.service.PostService;
import com.krsia.blog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.list());
        return "posts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.find(id);
        if (post == null) return "redirect:/posts";
        model.addAttribute("post", post);
        return "post-detail";
    }

    @GetMapping("/new")
    public String form() {
        return "post-form";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @AuthenticationPrincipal UserDetails principal) {
        User author = userService.getByEmail(principal.getUsername());
        postService.create(title, content, author);
        return "redirect:/posts";
    }
}
