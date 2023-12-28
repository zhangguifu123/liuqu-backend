package com.omate.liuqu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer uid;
    private String username;
    private String email;
    private String phone;
    private String personalDescription;
    private String gender;
    private String avatarPath;
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatar_path) {
        this.avatarPath = avatar_path;
    }

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personal_description) {
        this.personalDescription = personal_description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    private String hobby;
// getters and setters

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Override toString() if needed for debugging or logging
    @Override
    public String toString() {
        return "UserDTO{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
