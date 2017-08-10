package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherCourseError implements Serializable
{

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expected_cost")
    @Expose
    private String expectedCost;
    private final static long serialVersionUID = 5596013505928755821L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherCourseError() {
    }

    /**
     *
     * @param expectedCost
     * @param description
     * @param courseId
     */
    public TeacherCourseError(String courseId, String description, String expectedCost) {
        super();
        this.courseId = courseId;
        this.description = description;
        this.expectedCost = expectedCost;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedCost() {
        return expectedCost;
    }

    public void setExpectedCost(String expectedCost) {
        this.expectedCost = expectedCost;
    }

}