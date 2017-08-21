package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateOrderRequest implements Serializable
{

    @SerializedName("teacher_course_id")
    @Expose
    private Integer teacherCourseId;
    @SerializedName("on_at")
    @Expose
    private String onAt;
    private final static long serialVersionUID = 3133064999740264369L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateOrderRequest() {
    }

    /**
     *
     * @param onAt
     * @param teacherCourseId
     */
    public UpdateOrderRequest(Integer teacherCourseId, String onAt) {
        super();
        this.teacherCourseId = teacherCourseId;
        this.onAt = onAt;
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