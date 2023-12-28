package com.omate.liuqu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "autism_register")
public class AutismRegister {


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public void setGuardianFirstName(String guardianFirstName) {
        this.guardianFirstName = guardianFirstName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public void setGuardianLastName(String guardianLastName) {
        this.guardianLastName = guardianLastName;
    }

    public String getRelationshipToIndividual() {
        return relationshipToIndividual;
    }

    public void setRelationshipToIndividual(String relationshipToIndividual) {
        this.relationshipToIndividual = relationshipToIndividual;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getRaceEthnicity() {
        return raceEthnicity;
    }

    public void setRaceEthnicity(String raceEthnicity) {
        this.raceEthnicity = raceEthnicity;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;

    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotNull(message = "dob is mandatory")
    private Date dob;

    @NotBlank(message = "gender is mandatory")
    private String gender;

    @NotBlank(message = "phone is mandatory")
    private String phone;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "address is mandatory")
    private String address;

    private String guardianFirstName;

    private String guardianLastName;

    private String relationshipToIndividual;


    private String guardianEmail;

    private String guardianPhone;

    private String raceEthnicity;

    private String spokenLanguages;

    private String nationality;

    private String diagnosis;

    private Date diagnosisDate;

    private Date registerDate = Date.from(Instant.now());



}
