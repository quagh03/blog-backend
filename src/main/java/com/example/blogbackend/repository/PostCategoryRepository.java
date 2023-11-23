package com.example.blogbackend.repository;

import com.example.blogbackend.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, PostCategory.PostCategoryId> {
}
