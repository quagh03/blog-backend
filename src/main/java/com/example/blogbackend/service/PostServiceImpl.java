package com.example.blogbackend.service;

import com.example.blogbackend.entity.Post;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts(){
        try{
            return postRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng",e);
        }
    }

    @Override
    public Optional<Post> getPostById(Long id){
        try {
            return postRepository.findById(id);
        }catch (Exception e){
            throw new CustomException("Lỗi khi lấy bài đăng có Id: " + id, e);
        }
    }

    @Override
    public List<Post> searchByKeyword(String keyword){
        if (keyword == null) {
            throw new CustomException("Không có từ khóa để tìm kiếm");
        }
        try {
            List<Post> results = postRepository.search(keyword);
            if(results == null || results.isEmpty()){
                throw new CustomException("Không tìm thấy " + keyword);
            }
            return results;
        } catch (RuntimeException e){
            throw new CustomException("",e);
        }
    }

    @Override
    public Post updatePost(Long postId, Post postToUpdate){
        try {
            Post existPost = postRepository.findById(postId)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy bài viết dùng với Id: " + postId));
            BeanUtils.copyProperties(postToUpdate, existPost, "id");
            return postRepository.save(existPost);
        }catch (Exception e){
            throw new CustomException("Lỗi khi cập nhật bài viết", e);
        }
    }

    @Override
    public Post addPost(Post postToAdd){
        try {
            return postRepository.save(postToAdd);
        }catch (Exception e){
            throw new CustomException("Lỗi khi đăng bài viết", e);
        }
    }

    @Override
    public void deletePost(Long id){
        try {
            Post existPost = postRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thầy bài viết với Id: " + id));
            postRepository.delete(existPost);
        }catch (Exception e){
            throw new CustomException("Lỗi khi xoá bài viết", e);
        }
    }
}
