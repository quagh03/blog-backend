package com.example.blogbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post_meta")
public class PostMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "`key`", nullable = false)
    private String key;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public PostMeta() {
    }

    public PostMeta(Post post, String key, String content) {
        this.post = post;
        this.key = key;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    @Override
    public String toString() {
        return "PostMeta{" +
                "id=" + id +
                ", post=" + post +
                ", key='" + key + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
