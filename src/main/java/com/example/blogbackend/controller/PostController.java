package com.example.blogbackend.controller;

import com.example.blogbackend.dto.PostDto;
import com.example.blogbackend.entity.Post;
import com.example.blogbackend.service.PostService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(){
        try {
            return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //SEARCH BY KEYWORD
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<?> serchByKeyword(@PathVariable String keyword){
        try {
            return new ResponseEntity<>(postService.searchByKeyword(keyword), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //GET POST BY AUTHOR_ID
    @GetMapping("/author/{authorid}")
    public ResponseEntity<?> getPostsByAuthor(@PathVariable Long authorid){
        try {
            return new ResponseEntity<>(postService.getPostsByAuthor(authorid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET POST LIST BY CATEGORY ID
    @GetMapping("/category/{categoryid}")
    public ResponseEntity<?> getPostsByCategory(@PathVariable Long categoryid){
        try {
            return new ResponseEntity<>(postService.getPostsByCategory(categoryid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{postid}")
    public ResponseEntity<?> getPostById(@PathVariable Long postid){
        try{
            return new ResponseEntity<>(postService.getPostById(postid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{postid}")
    public ResponseEntity<?> editPost(@PathVariable Long postid, @RequestBody PostDto postToEdit){
        try {
            Post updatedPost = postService.updatePost(postid, postToEdit);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        try{
            postDto.setViews(0);
            Post createdPost = postService.addPost(postDto);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deletePost(@RequestParam Long postid){
        try{
            postService.deletePost(postid);
            return new ResponseEntity<>("Đã xoá bài viết id " + postid, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

//POST JSON
//                  {
//                "authorId": 1,
//                "parentId": null,
//                "categoryIds": [1, 3],
//                "title": "Tiêu đề bài viết mới",
//                "metaTitle": "Meta tiêu đề bài viết mới",
//                "slug": "tieu-de-bai-viet-moi",
//                "summary": "Tóm tắt bài viết mới",
//                "published": true,
//                "createdAt": "2023-11-26T10:34:56",
//                "updatedAt": "2023-11-26T10:34:56",
//                "publishedAt": "2023-11-23T14:34:56",
//                "content": "Nội dung bài viết mới",
//                "thumbnail": "URL hình ảnh đại diện bài viết mới"
//                }