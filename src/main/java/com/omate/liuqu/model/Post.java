package com.omate.liuqu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class Post {

    @ManyToOne
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pid;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date post) {
        this.postDate = post;
    }

    @NotNull(message = "Uid is mandatory")
    @Column(name = "uid")
    private Integer uid;
    @NotBlank(message = "Content is mandatory")
    private String content;
    private Date postDate = Date.from(Instant.now());


}
