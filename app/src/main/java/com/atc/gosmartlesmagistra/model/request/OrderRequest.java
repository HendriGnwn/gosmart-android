package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest implements Serializable
{

    @SerializedName("teacher_unique_number")
    @Expose
    private String teacherUniqueNumber;
    @SerializedName("teacher_course_id")
    @Expose
    private Integer teacherCourseId;
    @SerializedName("on_at")
    @Expose
    private String onAt;
    private final static long serialVersionUID = 1872368536384508197L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderRequest() {
    }

    /**
     *
     * @param onAt
     * @param teacherCourseId
     * @param teacherUniqueNumber
     */
    public OrderRequest(String teacherUniqueNumber, Integer teacherCourseId, String onAt) {
        super();
        this.teacherUniqueNumber = teacherUniqueNumber;
        this.teacherCourseId = teacherCourseId;
        this.onAt = onAt;
    }

    public String getTeacherUniqueNumber() {
        return teacherUniqueNumber;
    }

    public void setTeacherUniqueNumber(String teacherUniqueNumber) {
        this.teacherUniqueNumber = teacherUniqueNumber;
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

}