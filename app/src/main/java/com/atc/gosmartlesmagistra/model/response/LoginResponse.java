
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private LoginError loginError;
    private final static long serialVersionUID = -4495271601579441379L;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param loginError
     */
    public LoginResponse(Integer status, String message, LoginError loginError) {
        super();
        this.status = status;
        this.message = message;
        this.loginError = loginError;
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

    public LoginError getLoginError() {
        return loginError;
    }

    public void setLoginError(LoginError loginError) {
        this.loginError = loginError;
    }

}