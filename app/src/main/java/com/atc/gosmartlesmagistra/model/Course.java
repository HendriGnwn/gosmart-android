package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;

import com.atc.gosmartlesmagistra.model.CourseLevel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("course_level_id")
    @Expose
    private Integer courseLevelId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("section")
    @Expose
    private Integer section;
    @SerializedName("section_time")
    @Expose
    private String sectionTime;
    @SerializedName("teacher_availability")
    @Expose
    private Integer teacherAvailability;
    @SerializedName("course_level")
    @Expose
    private CourseLevel courseLevel;
    private final static long serialVersionUID = -8139161846886396207L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Course() {
    }

    /**
     *
     * @param id
     * @param courseLevel
     * @param teacherAvailability
     * @param courseLevelId
     * @param description
     * @param name
     * @param sectionTime
     * @param section
     */
    public Course(Integer id, Integer courseLevelId, String name, String description, Integer section, String sectionTime, Integer teacherAvailability, CourseLevel courseLevel) {
        super();
        this.id = id;
        this.courseLevelId = courseLevelId;
        this.name = name;
        this.description = description;
        this.section = section;
        this.sectionTime = sectionTime;
        this.teacherAvailability = teacherAvailability;
        this.courseLevel = courseLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseLevelId() {
        return courseLevelId;
    }

    public void setCourseLevelId(Integer courseLevelId) {
        this.courseLevelId = courseLevelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getTeacherAvailability() {
        return teacherAvailability;
    }

    public void setTeacherAvailability(Integer teacherAvailability) {
        this.teacherAvailability = teacherAvailability;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

}