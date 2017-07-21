
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;

import com.atc.gosmartlesmagistra.model.TeacherTermCondition;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherTermConditionSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TeacherTermCondition teacherTermCondition;
    private final static long serialVersionUID = -8401805715928346713L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherTermConditionSuccess() {
    }

    /**
     *
     * @param message
     * @param teacherTermCondition
     * @param status
     */
    public TeacherTermConditionSuccess(Integer status, String message, TeacherTermCondition teacherTermCondition) {
        super();
        this.status = status;
        this.message = message;
        this.teacherTermCondition = teacherTermCondition;
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

    public TeacherTermCondition getTeacherTermCondition() {
        return teacherTermCondition;
    }

    public void setTeacherTermCondition(TeacherTermCondition teacherTermCondition) {
        this.teacherTermCondition = teacherTermCondition;
    }

}