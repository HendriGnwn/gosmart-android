package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateModel implements Serializable
{
    public static Integer statusOnGoing = 5;
    public static Integer statusDone = 10;


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("section")
    @Expose
    private Integer section;
    @SerializedName("section_time")
    @Expose
    private String sectionTime;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("private_details")
    @Expose
    private List<PrivateDetail> privateDetails = null;
    @SerializedName("student")
    @Expose
    private User student;
    @SerializedName("teacher")
    @Expose
    private User teacher;
    private final static long serialVersionUID = 7995359464480305752L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateModel() {
    }

    /**
     *
     * @param startDate
     * @param status
     * @param endDate
     * @param code
     * @param sectionTime
     * @param section
     * @param privateDetails
     * @param id
     * @param updatedAt
     * @param student
     * @param createdAt
     * @param teacher
     * @param orderId
     */
    public PrivateModel(Integer id, Integer orderId, Integer section, String sectionTime, String code, String startDate, String endDate, Integer status, String createdAt, String updatedAt, List<PrivateDetail> privateDetails, User student, User teacher) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.section = section;
        this.sectionTime = sectionTime;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.privateDetails = privateDetails;
        this.student = student;
        this.teacher = teacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(String sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<PrivateDetail> getPrivateDetails() {
        return privateDetails;
    }

    public void setPrivateDetails(List<PrivateDetail> privateDetails) {
        this.privateDetails = privateDetails;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getStatusText() {
        switch (this.getStatus()) {
            case 1:
                return "Menunggu Aktif";
            case 5:
                return "Berjalan";
            case 10:
                return "Selesai";
        }

        return "-";
    }
    public String getFormattedStartDate() {
        String choose = this.getStartDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", new Locale("id", "ID")).parse(choose);
            SimpleDateFormat formatted = new SimpleDateFormat("dd MMM yyyy", new Locale("id", "ID"));
            choose = formatted.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return choose;
    }

    public String getFormattedEndDate() {
        String choose = this.getEndDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", new Locale("id", "ID")).parse(choose);
            SimpleDateFormat formatted = new SimpleDateFormat("dd MMM yyyy", new Locale("id", "ID"));
            choose = formatted.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return choose;
    }

}