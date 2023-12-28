package com.omate.liuqu.dto;

import java.util.Date;
import java.util.List;

public class CommentDTO {


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDTO> replies) {
        this.replies = replies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommentUserDTO getUser() {
        return user;
    }

    public void setUser(CommentUserDTO user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    private Integer id;
    private CommentUserDTO user;
    private Date date;
    private String content;
    private List<CommentDTO> replies;
    private Integer parentCommentId;
}
