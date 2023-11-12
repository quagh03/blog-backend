package com.example.blogbackend.service;

import com.example.blogbackend.entity.Post;
import com.example.blogbackend.exceptionhandle.CustomException;
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
        try{
            return postRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng",e);
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
}
