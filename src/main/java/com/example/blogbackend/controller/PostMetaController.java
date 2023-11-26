package com.example.blogbackend.controller;

import com.example.blogbackend.dto.PostMetaDto;
import com.example.blogbackend.entity.PostMeta;
import com.example.blogbackend.service.PostMetaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/postmeta")
public class PostMetaController {
    @Autowired
    private PostMetaService postMetaService;

    @GetMapping
    public ResponseEntity<?> getAllPostMeta(){
        try {
            List<PostMeta> postMetaList = postMetaService.getAllPostMeta();
            return new ResponseEntity<>(postMetaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{postid}")
    public ResponseEntity<?> getPostMetaByPostId(@PathVariable Long postid){
        try {
            List<PostMeta> postMetaList = postMetaService.getPostMetaByPostId(postid);
            return new ResponseEntity<>(postMetaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> addPostMeta(@RequestBody PostMetaDto postMeta){
        try {
            PostMeta newPostMeta = postMetaService.addPostMeta(postMeta);
            return new ResponseEntity<>(newPostMeta, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
