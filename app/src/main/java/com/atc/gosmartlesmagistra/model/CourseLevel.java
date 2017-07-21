
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseLevel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;
    private final static long serialVersionUID = 4039426650651375636L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CourseLevel() {
    }

    /**
     *
     * @param id
     * @param courses
     * @param name
     */
    public CourseLevel(Integer id, String name, List<Course> courses) {
        super();
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}