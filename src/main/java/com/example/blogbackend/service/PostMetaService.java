package com.example.blogbackend.service;

import com.example.blogbackend.dto.PostMetaDto;
import com.example.blogbackend.entity.PostMeta;

import java.util.List;

public interface PostMetaService {
    List<PostMeta> getAllPostMeta();

    List<PostMeta> getPostMetaByPostId(Long postid);

    // Thêm PostMeta mới
    PostMeta addPostMeta(PostMetaDto postMeta);

    // Cập nhật PostMeta
    PostMeta updatePostMeta(PostMetaDto postMeta);

    // Xóa PostMeta
    void deletePostMeta(Long id);
}
