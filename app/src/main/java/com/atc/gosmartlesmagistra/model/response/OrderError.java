package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderError implements Serializable
{

    @SerializedName("teacher_unique_number")
    @Expose
    private String teacherUniqueNumber;
    @SerializedName("teacher_course_id")
    @Expose
    private String teacherCourseId;
    @SerializedName("on_at")
    @Expose
    private String onAt;
    private final static long serialVersionUID = 2375258872689747724L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderError() {
    }

    /**
     *
     * @param onAt
     * @param teacherCourseId
     * @param teacherUniqueNumber
     */
    public OrderError(String teacherUniqueNumber, String teacherCourseId, String onAt) {
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

    public String getTeacherCourseId() {
        return teacherCourseId;
    }

    public void setTeacherCourseId(String teacherCourseId) {
        this.teacherCourseId = teacherCourseId;
    }

    public String getOnAt() {
        return onAt;
    }

    public void setOnAt(String onAt) {
        this.onAt = onAt;
    }

}