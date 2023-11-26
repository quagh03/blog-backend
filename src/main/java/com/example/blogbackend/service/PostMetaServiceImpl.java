package com.example.blogbackend.service;

import com.example.blogbackend.dto.PostMetaDto;
import com.example.blogbackend.entity.Post;
import com.example.blogbackend.entity.PostMeta;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.PostMetaRepository;
import com.example.blogbackend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostMetaServiceImpl implements PostMetaService{
    @Autowired
    private PostMetaRepository postMetaRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostMeta> getAllPostMeta(){
        try {
            return postMetaRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả Post Meta",e);
        }
    }

    @Override
    public List<PostMeta> getPostMetaByPostId(Long postid) {
        try {
            return postMetaRepository.findByPost_Id(postid);
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả Post Meta của postid: " + postid ,e);
        }
    }

    // Thêm PostMeta mới
    public PostMeta addPostMeta(PostMetaDto postMetaDto) {
        try {
            Post post = postRepository.findById(postMetaDto.getPostId())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find Post with id: " + postMetaDto.getPostId()));
            PostMeta postMeta = new PostMeta(post, postMetaDto.getKey(), postMetaDto.getContent());
            return postMetaRepository.save(postMeta);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving PostMeta: " + e.getMessage());
        }
    }

    // Cập nhật PostMeta
    @Override
    public PostMeta updatePostMeta(PostMetaDto postMetaDto) {
        try {
            PostMeta postMeta = postMetaRepository.findById(postMetaDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find PostMeta with id: " + postMetaDto.getId()));
            postMeta.setKey(postMetaDto.getKey());
            postMeta.setContent(postMetaDto.getContent());
            return postMetaRepository.save(postMeta);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating PostMeta: " + e.getMessage());
        }
    }

    // Xóa PostMeta
    @Override
    public void deletePostMeta(Long id) {
        try {
            postMetaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting PostMeta: " + e.getMessage());
        }
    }


}
