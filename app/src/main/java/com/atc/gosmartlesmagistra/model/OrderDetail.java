package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("teacher_course_id")
    @Expose
    private Integer teacherCourseId;
    @SerializedName("on_at")
    @Expose
    private String onAt;
    @SerializedName("section")
    @Expose
    private Integer section;
    @SerializedName("section_time")
    @Expose
    private String sectionTime;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("on_details")
    @Expose
    private List<String> onDetails = null;
    @SerializedName("teacher_course")
    @Expose
    private TeacherCourse teacherCourse;
    private final static long serialVersionUID = 547246616525677130L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderDetail() {
    }

    /**
     *
     * @param teacherCourse
     * @param updatedAt
     * @param amount
     * @param id
     * @param onAt
     * @param teacherCourseId
     * @param onDetails
     * @param createdAt
     * @param sectionTime
     * @param section
     * @param orderId
     */
    public OrderDetail(Integer id, Integer orderId, Integer teacherCourseId, String onAt, Integer section, String sectionTime, String amount, String createdAt, String updatedAt, List<String> onDetails, TeacherCourse teacherCourse) {        super();
        this.id = id;
        this.orderId = orderId;
        this.teacherCourseId = teacherCourseId;
        this.onAt = onAt;
        this.section = section;
        this.sectionTime = sectionTime;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.onDetails = onDetails;
        this.teacherCourse = teacherCourse;
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

    public Integer getTeacherCourseId() {
        return teacherCourseId;
    }

    public void setTeacherCourseId(Integer teacherCourseId) {
        this.teacherCourseId = teacherCourseId;
    }

    public String getOnAt() {
        return onAt;
    }

    public void setOnAt(String onAt) {
        this.onAt = onAt;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public List<String> getOnDetails() {
        return onDetails;
    }

    public void setOnDetails(List<String> onDetails) {
        this.onDetails = onDetails;
    }

    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

}