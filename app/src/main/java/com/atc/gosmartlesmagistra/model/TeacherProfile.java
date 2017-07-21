
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherProfile implements Serializable
{

    @SerializedName("title")
    @Expose
    private Integer title;
    @SerializedName("izajah_number")
    @Expose
    private String izajahNumber;
    @SerializedName("graduated")
    @Expose
    private String graduated;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("upload_izajah")
    @Expose
    private String uploadIzajah;
    @SerializedName("formal_photo")
    @Expose
    private String formalPhoto;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_updated_at")
    @Expose
    private String totalUpdatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("teacher_bank")
    @Expose
    private TeacherBank teacherBank;
    @SerializedName("teacher_courses")
    @Expose
    private List<TeacherCourse> teacherCourses = null;
    @SerializedName("teacher_total_histories")
    @Expose
    private List<TeacherTotalHistory> teacherTotalHistories = null;
    private final static long serialVersionUID = 4188594433897398307L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherProfile() {
    }

    /**
     *
     * @param total
     * @param totalUpdatedAt
     * @param teacherCourses
     * @param teacherTotalHistories
     * @param formalPhoto
     * @param photo
     * @param uploadIzajah
     * @param updatedAt
     * @param title
     * @param graduated
     * @param bio
     * @param createdAt
     * @param teacherBank
     * @param izajahNumber
     */
    public TeacherProfile(Integer title, String izajahNumber, String graduated, String bio, String photo, String uploadIzajah, String formalPhoto, String total, String totalUpdatedAt, String createdAt, String updatedAt, TeacherBank teacherBank, List<TeacherCourse> teacherCourses, List<TeacherTotalHistory> teacherTotalHistories) {
        super();
        this.title = title;
        this.izajahNumber = izajahNumber;
        this.graduated = graduated;
        this.bio = bio;
        this.photo = photo;
        this.uploadIzajah = uploadIzajah;
        this.formalPhoto = formalPhoto;
        this.total = total;
        this.totalUpdatedAt = totalUpdatedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.teacherBank = teacherBank;
        this.teacherCourses = teacherCourses;
        this.teacherTotalHistories = teacherTotalHistories;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getIzajahNumber() {
        return izajahNumber;
    }

    public void setIzajahNumber(String izajahNumber) {
        this.izajahNumber = izajahNumber;
    }

    public String getGraduated() {
        return graduated;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUploadIzajah() {
        return uploadIzajah;
    }

    public void setUploadIzajah(String uploadIzajah) {
        this.uploadIzajah = uploadIzajah;
    }

    public String getFormalPhoto() {
        return formalPhoto;
    }

    public void setFormalPhoto(String formalPhoto) {
        this.formalPhoto = formalPhoto;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalUpdatedAt() {
        return totalUpdatedAt;
    }

    public void setTotalUpdatedAt(String totalUpdatedAt) {
        this.totalUpdatedAt = totalUpdatedAt;
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

    public TeacherBank getTeacherBank() {
        return teacherBank;
    }

    public void setTeacherBank(TeacherBank teacherBank) {
        this.teacherBank = teacherBank;
    }

    public List<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(List<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    public List<TeacherTotalHistory> getTeacherTotalHistories() {
        return teacherTotalHistories;
    }

    public void setTeacherTotalHistories(List<TeacherTotalHistory> teacherTotalHistories) {
        this.teacherTotalHistories = teacherTotalHistories;
    }

}