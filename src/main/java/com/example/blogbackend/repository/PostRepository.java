package com.example.blogbackend.repository;

import com.example.blogbackend.entity.Category;
import com.example.blogbackend.entity.Post;
import com.example.blogbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> search(@Param("keyword") String keyword);

    List<Post> findByCategoryList(Category category);

    List<Post> findByAuthor(User user);

}
