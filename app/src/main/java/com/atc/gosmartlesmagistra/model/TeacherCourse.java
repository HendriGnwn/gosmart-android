
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherCourse implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expected_cost")
    @Expose
    private String expectedCost;
    @SerializedName("expected_cost_updated_at")
    @Expose
    private String expectedCostUpdatedAt;
    @SerializedName("additional_cost")
    @Expose
    private String additionalCost;
    @SerializedName("admin_fee")
    @Expose
    private String adminFee;
    @SerializedName("final_cost")
    @Expose
    private String finalCost;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("course")
    @Expose
    private Course course;
    private final static long serialVersionUID = 3941109321883854335L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TeacherCourse() {
    }

    /**
     * 
     * @param course
     * @param id
     * @param module
     * @param additionalCost
     * @param finalCost
     * @param expectedCost
     * @param description
     * @param adminFee
     * @param courseId
     * @param expectedCostUpdatedAt
     */
    public TeacherCourse(Integer id, Integer courseId, String description, String expectedCost, String expectedCostUpdatedAt, String additionalCost, String adminFee, String finalCost, String module, Course course) {
        super();
        this.id = id;
        this.courseId = courseId;
        this.description = description;
        this.expectedCost = expectedCost;
        this.expectedCostUpdatedAt = expectedCostUpdatedAt;
        this.additionalCost = additionalCost;
        this.adminFee = adminFee;
        this.finalCost = finalCost;
        this.module = module;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getExpectedCostUpdatedAt() {
        return expectedCostUpdatedAt;
    }

    public void setExpectedCostUpdatedAt(String expectedCostUpdatedAt) {
        this.expectedCostUpdatedAt = expectedCostUpdatedAt;
    }

    public String getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(String additionalCost) {
        this.additionalCost = additionalCost;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(String finalCost) {
        this.finalCost = finalCost;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
