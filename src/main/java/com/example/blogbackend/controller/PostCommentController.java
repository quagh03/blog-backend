package com.example.blogbackend.controller;

import com.example.blogbackend.entity.PostComment;
import com.example.blogbackend.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //GET ALL CHILD COMMENTS BY PARENT ID
    @GetMapping("/parents/{parentid}")
    public List<PostComment> getCommentByParentId(@PathVariable Long parentid){
        return null;
    }

    //ADD COMMENT
    @PostMapping("/post/{postid}")
    public PostComment addCommentByPostId(@PathVariable Long postid){
        return null;
    }

    //UPDATE COMMENT

    //DELETE COMMENT

}
