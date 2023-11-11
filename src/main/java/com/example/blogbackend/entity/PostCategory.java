package com.example.blogbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "post_category", indexes = {
        @Index(name = "idx_pc_category", columnList = "category_id"),
        @Index(name = "idx_pc_post", columnList = "post_id")
},
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_pc_post_category", columnNames = {"post_id", "category_id"})
        })
public class PostCategory {
    @EmbeddedId
    private PostCategoryId id;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public PostCategory() {
        this.id = new PostCategoryId();
    }

    public PostCategory(Post post, Category category) {
        this.id = new PostCategoryId();
        this.post = post;
        this.category = category;
    }

    public PostCategoryId getId() {
        return id;
    }

    public void setId(PostCategoryId id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Embeddable
    public static class PostCategoryId implements Serializable {
        private Long postId;
        private Long categoryId;

        public PostCategoryId() {
        }

        public PostCategoryId(Long postId, Long categoryId) {
            this.postId = postId;
            this.categoryId = categoryId;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
    }
}
