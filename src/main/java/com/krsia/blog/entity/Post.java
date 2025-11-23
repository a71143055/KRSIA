package com.krsia.blog.entity;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String content;

    @ManyToOne
    private User author;
    // getter/setter
}