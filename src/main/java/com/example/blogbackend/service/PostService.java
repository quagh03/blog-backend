package com.example.blogbackend.service;

import com.example.blogbackend.dto.PostDto;
import com.example.blogbackend.entity.Category;
import com.example.blogbackend.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getPostsByCategory(Long categoryid);

    List<Post> getPostNotPublised();

    List<Post> getPostPublised();

    Post publishPost(Long postid);

    Post unPublishPost(Long postid);

    List<Post> getPostsByAuthor(Long authorid);

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long id);

    List<Post> searchByKeyword(String keyword);

    Post updatePost(Long postId, PostDto postDto);

    Post addPost(PostDto postDto);

    void deletePost(Long id);
}
