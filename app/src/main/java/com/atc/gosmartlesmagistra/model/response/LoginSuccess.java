
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;

import com.atc.gosmartlesmagistra.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private User user;
    private final static long serialVersionUID = -6063553094077069824L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LoginSuccess() {
    }

    /**
     * 
     * @param message
     * @param status
     * @param user
     */
    public LoginSuccess(Integer status, String message, User user) {
        super();
        this.status = status;
        this.message = message;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
