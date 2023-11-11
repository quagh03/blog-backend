package com.example.blogbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 75)
    private String title;

    @Column(name = "meta_title", length = 100)
    private String metaTitle;

    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public Tag() {
    }

    public Tag(String title, String metaTitle, String slug, String content) {
        this.title = title;
        this.metaTitle = metaTitle;
        this.slug = slug;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
