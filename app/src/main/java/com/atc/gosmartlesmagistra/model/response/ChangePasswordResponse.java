
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private ChangePasswordError changePasswordError;
    private final static long serialVersionUID = 8261992111483658110L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePasswordResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param changePasswordError
     */
    public ChangePasswordResponse(Integer status, String message, ChangePasswordError changePasswordError) {
        super();
        this.status = status;
        this.message = message;
        this.changePasswordError = changePasswordError;
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

    public ChangePasswordError getChangePasswordError() {
        return changePasswordError;
    }

    public void setChangePasswordError(ChangePasswordError changePasswordError) {
        this.changePasswordError = changePasswordError;
    }

}