package com.krsia.blog.service;

import com.krsia.blog.entity.Post;
import com.krsia.blog.entity.User;
import com.krsia.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> list() {
        return postRepository.findAll();
    }

    public Post find(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post create(String title, String content, User author) {
        return postRepository.save(new Post(title, content, author));
    }
}
