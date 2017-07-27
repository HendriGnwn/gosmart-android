
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherTermCondition implements Serializable
{

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 8978480900690221730L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherTermCondition() {
    }

    /**
     *
     * @param description
     */
    public TeacherTermCondition(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}