package com.example.blogbackend.controller;

import com.example.blogbackend.entity.PostComment;
import com.example.blogbackend.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blog/comments")
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;

    //GET ALL COMMENTS
    @GetMapping
    public List<PostComment> getAllComments(){
        return postCommentService.getAllComments();
    }

    //GET ALL COMMENT IN ONE POST
    @GetMapping("/post/{postid}")
    public List<PostComment> getAllComments(@PathVariable Long postid){
        return postCommentService.getCommentsByPostsID(postid);
    }
}
