package com.omate.liuqu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer uid;

    @NotBlank(message = "Username is mandatory")
    private String username;
//    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Phone is mandatory")
    private String phone;
//    @NotBlank(message = "Password is mandatory")
    private String password;
//    @NotBlank(message = "AvatarPath is mandatory")
    private String avatarPath = "http://13.236.138.98:8082/api/view/cHJvZmlsZS1waWN0dXJl_1698819116508.jpg";
    @NotBlank(message = "UserType is mandatory")
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

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personal_description) {
        this.personalDescription = personal_description;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    private String personalDescription;
    private String hobby;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}