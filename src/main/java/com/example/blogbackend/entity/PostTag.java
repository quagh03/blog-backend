package com.example.blogbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "post_tag", indexes = {
        @Index(name = "idx_pt_tag", columnList = "tag_id"),
        @Index(name = "idx_pt_post", columnList = "post_id")
},
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_pt_post_tag", columnNames = {"post_id", "tag_id"})
        })
public class PostTag {
    @EmbeddedId
    private PostTagId id;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public PostTag() {
        this.id = new PostTagId();
    }

    public PostTag(Post post, Tag tag) {
        this.id = new PostTagId();
        this.post = post;
        this.tag = tag;
    }

    public PostTagId getId() {
        return id;
    }

    public void setId(PostTagId id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Embeddable
    public static class PostTagId implements Serializable {

        @Column(name = "post_id")
        private Long postId;

        @Column(name = "tag_id")
        private Long tagId;

        public PostTagId() {
        }

        public PostTagId(Long postId, Long tagId) {
            this.postId = postId;
            this.tagId = tagId;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }

        public Long getTagId() {
            return tagId;
        }

        public void setTagId(Long tagId) {
            this.tagId = tagId;
        }
    }
}
