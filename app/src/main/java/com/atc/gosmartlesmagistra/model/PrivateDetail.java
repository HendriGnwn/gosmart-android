package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateDetail implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("private_id")
    @Expose
    private Integer privateId;
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
    @SerializedName("student_details")
    @Expose
    private String studentDetails;
    @SerializedName("teacher_details")
    @Expose
    private String teacherDetails;
    @SerializedName("checklist")
    @Expose
    private Integer checklist;
    @SerializedName("checklist_at")
    @Expose
    private String checklistAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = 7645345016593927189L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateDetail() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param onAt
     * @param teacherCourseId
     * @param createdAt
     * @param studentDetails
     * @param checklistAt
     * @param sectionTime
     * @param teacherDetails
     * @param checklist
     * @param section
     * @param privateId
     */
    public PrivateDetail(Integer id, Integer privateId, Integer teacherCourseId, String onAt, Integer section, String sectionTime, String studentDetails, String teacherDetails, Integer checklist, String checklistAt, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.privateId = privateId;
        this.teacherCourseId = teacherCourseId;
        this.onAt = onAt;
        this.section = section;
        this.sectionTime = sectionTime;
        this.studentDetails = studentDetails;
        this.teacherDetails = teacherDetails;
        this.checklist = checklist;
        this.checklistAt = checklistAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrivateId() {
        return privateId;
    }

    public void setPrivateId(Integer privateId) {
        this.privateId = privateId;
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

    public String getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(String studentDetails) {
        this.studentDetails = studentDetails;
    }

    public String getTeacherDetails() {
        return teacherDetails;
    }

    public void setTeacherDetails(String teacherDetails) {
        this.teacherDetails = teacherDetails;
    }

    public Integer getChecklist() {
        return checklist;
    }

    public void setChecklist(Integer checklist) {
        this.checklist = checklist;
    }

    public String getChecklistAt() {
        return checklistAt;
    }

    public void setChecklistAt(String checklistAt) {
        this.checklistAt = checklistAt;
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

}