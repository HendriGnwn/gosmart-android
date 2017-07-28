
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherBankResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private TeacherBankError teacherBankError;
    private final static long serialVersionUID = -7530531141385813260L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherBankResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param teacherBankError
     */
    public UpdateTeacherBankResponse(Integer status, String message, TeacherBankError teacherBankError) {
        super();
        this.status = status;
        this.message = message;
        this.teacherBankError = teacherBankError;
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

    public TeacherBankError getTeacherBankError() {
        return teacherBankError;
    }

    public void setTeacherBankError(TeacherBankError teacherBankError) {
        this.teacherBankError = teacherBankError;
    }

}