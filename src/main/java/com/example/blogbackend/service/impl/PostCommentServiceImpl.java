package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.PostComment;
import com.example.blogbackend.repository.PostCommentRepository;
import com.example.blogbackend.service.PostCommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    public List<PostComment> getAllComments(){
        try {
            return postCommentRepository.findAll();
        }catch (Exception e){
            throw  new EntityNotFoundException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<PostComment> getCommentsByPostsID(Long postid){
        try {
            return postCommentRepository.getPostCommentByPost_Id(postid);
        }catch (Exception e){
            throw  new EntityNotFoundException("Error: " + e.getMessage());
        }
    }
}
