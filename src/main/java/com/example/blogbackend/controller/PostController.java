package com.example.blogbackend.controller;

import com.example.blogbackend.dto.PostDto;
import com.example.blogbackend.entity.Post;
import com.example.blogbackend.entity.Role;
import com.example.blogbackend.entity.User;
import com.example.blogbackend.service.PostService;
import com.example.blogbackend.service.UserService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/admin/publish/{postid}")
    public ResponseEntity<?> publishPost(@PathVariable Long postid){
        try {
            return new ResponseEntity<>(postService.publishPost(postid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/unpublish/{postid}")
    public ResponseEntity<?> unPublishPost(@PathVariable Long postid){
        try {
            return new ResponseEntity<>(postService.unPublishPost(postid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    //GET POST
    @GetMapping("/notpublised")
    public ResponseEntity<?> getPostNotPublised(){
        try {
            return new ResponseEntity<>(postService.getPostNotPublised(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/publised")
    public ResponseEntity<?> getPostsPublised(){
        try {
            return new ResponseEntity<>(postService.getPostPublised(), HttpStatus.OK);
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User currentUser = userService.getUserByUsername(userName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay user"));

            Post existingPost = postService.getPostById(postid)
                    .orElseThrow(() -> new EntityNotFoundException("Khong co post voi id"));
            if(currentUser.getRoles().getRole() == Role.UserRole.ROLE_ADMIN || Objects.equals(currentUser.getId(), existingPost.getAuthor().getId())){
                Post updatedPost = postService.updatePost(postid, postToEdit);
                return new ResponseEntity<>(updatedPost, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Bạn không có quyền chỉnh sửa thông tin của post này", HttpStatus.FORBIDDEN);
            }

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Khong co user"));

            postDto.setAuthorId(user.getId());

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