package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherProfileRequest implements Serializable
{

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
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
    private final static long serialVersionUID = 213163151091076887L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherProfileRequest() {
    }

    /**
     *
     * @param uploadIzajah
     * @param lastName
     * @param title
     * @param graduated
     * @param bio
     * @param phoneNumber
     * @param email
     * @param address
     * @param longitude
     * @param latitude
     * @param izajahNumber
     * @param firstName
     * @param photo
     */
    public UpdateTeacherProfileRequest(String firstName, String lastName, String phoneNumber, String latitude, String longitude, String address, String email, Integer title, String izajahNumber, String graduated, String bio, String photo, String uploadIzajah) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.email = email;
        this.title = title;
        this.izajahNumber = izajahNumber;
        this.graduated = graduated;
        this.bio = bio;
        this.photo = photo;
        this.uploadIzajah = uploadIzajah;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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

}