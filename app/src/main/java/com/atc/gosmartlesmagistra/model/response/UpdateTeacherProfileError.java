package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherProfileError implements Serializable
{

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("graduated")
    @Expose
    private String graduated;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("upload_izajah")
    @Expose
    private String uploadIzajah;
    private final static long serialVersionUID = 4741755232648829964L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherProfileError() {
    }

    /**
     *
     * @param uploadIzajah
     * @param title
     * @param graduated
     * @param phoneNumber
     * @param email
     * @param address
     * @param longitude
     * @param latitude
     * @param firstName
     * @param photo
     */
    public UpdateTeacherProfileError(String firstName, String phoneNumber, String address, String email, String latitude, String longitude, String title, String graduated, String photo, String uploadIzajah) {
        super();
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.graduated = graduated;
        this.photo = photo;
        this.uploadIzajah = uploadIzajah;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGraduated() {
        return graduated;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
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