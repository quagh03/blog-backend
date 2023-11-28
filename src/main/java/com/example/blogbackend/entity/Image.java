package com.example.blogbackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @Column(name = "filename", columnDefinition = "TEXT", nullable = false)
    private String filename;

    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    private String url;

    public Image() {
    }

    public Image(Post post, String filename, String url) {
        this.post = post;
        this.filename = filename;
        this.url = url;
    }

    public Image(String url, String filename) {
        this.url = url;
        this.filename = filename;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
