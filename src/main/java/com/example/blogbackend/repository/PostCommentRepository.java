package com.example.blogbackend.repository;

import com.example.blogbackend.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> getPostCommentByPost_Id(Long postId);
}
