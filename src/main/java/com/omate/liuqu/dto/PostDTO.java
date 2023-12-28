package com.omate.liuqu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Integer id;
    private Date date;
    private String content;
    private CommentUserDTO user;
    private List<CommentDTO> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public CommentUserDTO getUser() {
        return user;
    }

    public void setUser(CommentUserDTO user) {
        this.user = user;
    }
    


}
