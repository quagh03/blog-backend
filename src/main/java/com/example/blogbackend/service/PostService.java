package com.example.blogbackend.service;

import com.example.blogbackend.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPosts();

    Optional<Post> getPostById(Long id);

    List<Post> searchByKeyword(String keyword);

    Post updatePost(Long postId, Post postToUpdate);

    Post addPost(Post postToAdd);

    void deletePost(Long id);
}
