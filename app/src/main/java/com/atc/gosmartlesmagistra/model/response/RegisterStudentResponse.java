
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterStudentResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private RegisterStudentError registerStudentError;
    private final static long serialVersionUID = -3772374456243406644L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterStudentResponse() {
    }

    /**
     *
     * @param message
     * @param registerStudentError
     * @param status
     */
    public RegisterStudentResponse(Integer status, String message, RegisterStudentError registerStudentError) {
        super();
        this.status = status;
        this.message = message;
        this.registerStudentError = registerStudentError;
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

    public RegisterStudentError getRegisterStudentError() {
        return registerStudentError;
    }

    public void setRegisterStudentError(RegisterStudentError registerStudentError) {
        this.registerStudentError = registerStudentError;
    }

}