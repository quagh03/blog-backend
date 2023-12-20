package com.example.blogbackend.service;

import com.example.blogbackend.entity.PostComment;

import java.util.List;

public interface PostCommentService {
    List<PostComment> getAllComments();

    List<PostComment> getCommentsByPostsID(Long postid);
}
