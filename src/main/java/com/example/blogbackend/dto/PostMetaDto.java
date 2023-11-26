package com.example.blogbackend.dto;

public class PostMetaDto {
    private Long id;
    private Long postId;
    private String key;
    private String content;

    public PostMetaDto() {
    }

    public PostMetaDto(Long postId, String key, String content) {
        this.postId = postId;
        this.key = key;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
