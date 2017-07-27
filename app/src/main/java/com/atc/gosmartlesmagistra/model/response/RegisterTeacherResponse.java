
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterTeacherResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private RegisterTeacherError registerTeacherError;
    private final static long serialVersionUID = 5757046851442873128L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterTeacherResponse() {
    }

    /**
     *
     * @param message
     * @param registerTeacherError
     * @param status
     */
    public RegisterTeacherResponse(Integer status, String message, RegisterTeacherError registerTeacherError) {
        super();
        this.status = status;
        this.message = message;
        this.registerTeacherError = registerTeacherError;
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

    public RegisterTeacherError getRegisterTeacherError() {
        return registerTeacherError;
    }

    public void setRegisterTeacherError(RegisterTeacherError registerTeacherError) {
        this.registerTeacherError = registerTeacherError;
    }

}