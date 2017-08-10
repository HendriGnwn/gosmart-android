package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherCourseResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private TeacherCourseError teacherCourseError;
    private final static long serialVersionUID = -424724902950906408L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherCourseResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param teacherCourseError
     */
    public TeacherCourseResponse(Integer status, String message, TeacherCourseError teacherCourseError) {
        super();
        this.status = status;
        this.message = message;
        this.teacherCourseError = teacherCourseError;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TeacherCourseError getTeacherCourseError() {
        return teacherCourseError;
    }

    public void setTeacherCourseError(TeacherCourseError teacherCourseError) {
        this.teacherCourseError = teacherCourseError;
    }

}