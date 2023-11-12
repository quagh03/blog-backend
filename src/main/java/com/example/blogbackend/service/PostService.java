package com.example.blogbackend.service;

import com.example.blogbackend.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    List<Post> searchByKeyword(String keyword);
}
