package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import java.util.List;

import com.atc.gosmartlesmagistra.model.CourseLevel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseLevelSpinnerSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CourseLevel> courseLevels = null;
    private final static long serialVersionUID = 5231170335525438980L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CourseLevelSpinnerSuccess() {
    }

    /**
     *
     * @param message
     * @param status
     * @param courseLevels
     */
    public CourseLevelSpinnerSuccess(Integer status, String message, List<CourseLevel> courseLevels) {
        super();
        this.status = status;
        this.message = message;
        this.courseLevels = courseLevels;
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

    public List<CourseLevel> getCourseLevels() {
        return courseLevels;
    }

    public void setCourseLevels(List<CourseLevel> courseLevels) {
        this.courseLevels = courseLevels;
    }

}