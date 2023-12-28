package com.omate.liuqu.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class Comment {
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

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

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public boolean isIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(boolean if_deleted) {
        this.ifDeleted = if_deleted;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer cid;
    private Integer pid;
    private Integer uid;
    @Column(name = "parent_comment_id")
    private Integer parentCommentId;
    private String context;
    private Date commentDate = Date.from(Instant.now());
    private boolean ifDeleted = false;
}
