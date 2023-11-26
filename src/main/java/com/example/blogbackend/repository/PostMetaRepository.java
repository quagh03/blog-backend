package com.example.blogbackend.repository;

import com.example.blogbackend.entity.PostMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostMetaRepository extends JpaRepository<PostMeta, Long> {
    List<PostMeta> findByPost_Id(Long postid);
}
