package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherCourseRequest implements Serializable
{

    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expected_cost")
    @Expose
    private String expectedCost;
    private final static long serialVersionUID = 4507650494863837188L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherCourseRequest() {
    }

    /**
     *
     * @param expectedCost
     * @param description
     * @param courseId
     */
    public TeacherCourseRequest(Integer courseId, String description, String expectedCost) {
        super();
        this.courseId = courseId;
        this.description = description;
        this.expectedCost = expectedCost;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
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