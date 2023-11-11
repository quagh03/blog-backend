package com.example.blogbackend.service;

import com.example.blogbackend.entity.Post;
import com.example.blogbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
}
