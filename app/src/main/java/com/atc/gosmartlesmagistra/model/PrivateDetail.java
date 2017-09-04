package com.atc.gosmartlesmagistra.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @SerializedName("on_details")
    @Expose
    private List<String> onDetails = null;
    @SerializedName("student_on_details")
    @Expose
    private List<StudentOnDetail> studentOnDetails = null;
    @SerializedName("teacher_on_details")
    @Expose
    private List<TeacherOnDetail> teacherOnDetails = null;
    @SerializedName("teacher_course")
    @Expose
    private TeacherCourse teacherCourse;
    private final static long serialVersionUID = 6532793039185014513L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateDetail() {
    }

    /**
     *
     * @param teacherCourse
     * @param teacherCourseId
     * @param teacherOnDetails
     * @param studentDetails
     * @param sectionTime
     * @param teacherDetails
     * @param section
     * @param checklist
     * @param id
     * @param updatedAt
     * @param onAt
     * @param onDetails
     * @param createdAt
     * @param studentOnDetails
     * @param checklistAt
     * @param privateId
     */
    public PrivateDetail(Integer id, Integer privateId, Integer teacherCourseId, String onAt, Integer section, String sectionTime, String studentDetails, String teacherDetails, Integer checklist, String checklistAt, String createdAt, String updatedAt, List<String> onDetails, List<StudentOnDetail> studentOnDetails, List<TeacherOnDetail> teacherOnDetails, TeacherCourse teacherCourse) {
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
        this.onDetails = onDetails;
        this.studentOnDetails = studentOnDetails;
        this.teacherOnDetails = teacherOnDetails;
        this.teacherCourse = teacherCourse;
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

    public List<String> getOnDetails() {
        return onDetails;
    }

    public void setOnDetails(List<String> onDetails) {
        this.onDetails = onDetails;
    }

    public List<StudentOnDetail> getStudentOnDetails() {
        return studentOnDetails;
    }

    public void setStudentOnDetails(List<StudentOnDetail> studentOnDetails) {
        this.studentOnDetails = studentOnDetails;
    }

    public List<TeacherOnDetail> getTeacherOnDetails() {
        return teacherOnDetails;
    }

    public void setTeacherOnDetails(List<TeacherOnDetail> teacherOnDetails) {
        this.teacherOnDetails = teacherOnDetails;
    }

    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    public String getChecklistText()
    {
        if (this.getChecklist() == 1) {
            return "Ya";
        } else {
            return "Belum";
        }
    }



    public String getFormattedChecklistAt() {
        String choose = this.getChecklistAt();
        if (TextUtils.isEmpty(choose)) {
            return "-";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(choose);
            SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy H:00", new Locale("id", "ID"));
            choose = formatted.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return choose;
    }

}