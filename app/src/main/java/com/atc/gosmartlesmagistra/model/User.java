
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.model.response.StudentProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{
    public static Integer roleTeacher = 2;
    public static Integer roleStudent = 3;

    @SerializedName("unique_number")
    @Expose
    private String uniqueNumber;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("last_login_at")
    @Expose
    private String lastLoginAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("student_profile")
    @Expose
    private StudentProfile studentProfile;
    @SerializedName("teacher_profile")
    @Expose
    private TeacherProfile teacherProfile;
    private final static long serialVersionUID = 6464877033203885111L;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param lastName
     * @param lastLoginAt
     * @param uniqueNumber
     * @param status
     * @param photo
     * @param updatedAt
     * @param phoneNumber
     * @param firebaseToken
     * @param teacherProfile
     * @param token
     * @param address
     * @param email
     * @param createdAt
     * @param role
     * @param studentProfile
     * @param longitude
     * @param latitude
     * @param firstName
     */
    public User(String uniqueNumber, String firstName, String lastName, String phoneNumber, String photo, Double latitude, Double longitude, String address, String email, String firebaseToken, Integer status, Integer role, String lastLoginAt, String createdAt, String updatedAt, String token, StudentProfile studentProfile, TeacherProfile teacherProfile) {
        super();
        this.uniqueNumber = uniqueNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.status = status;
        this.role = role;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.token = token;
        this.studentProfile = studentProfile;
        this.teacherProfile = teacherProfile;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacherProfile = teacherProfile;
    }

    public String getFullName() {
        String lastName = "";
        if (this.getLastName() != null) {
            lastName = this.getLastName();
        }

        return this.getFirstName() + " " + lastName;
    }

    public String getShortAddress() {
        return App.getCutString(this.getAddress(), 47);
    }

}