
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentProfile implements Serializable
{

    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("school_address")
    @Expose
    private String schoolAddress;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("formal_photo")
    @Expose
    private String formalPhoto;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = -6973873453011235176L;

    /**
     * No args constructor for use in serialization
     *
     */
    public StudentProfile() {
    }

    /**
     *
     * @param updatedAt
     * @param degree
     * @param school
     * @param createdAt
     * @param department
     * @param formalPhoto
     * @param schoolAddress
     * @param photo
     */
    public StudentProfile(String school, String degree, String department, String schoolAddress, String photo, String formalPhoto, String createdAt, String updatedAt) {
        super();
        this.school = school;
        this.degree = degree;
        this.department = department;
        this.schoolAddress = schoolAddress;
        this.photo = photo;
        this.formalPhoto = formalPhoto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFormalPhoto() {
        return formalPhoto;
    }

    public void setFormalPhoto(String formalPhoto) {
        this.formalPhoto = formalPhoto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}