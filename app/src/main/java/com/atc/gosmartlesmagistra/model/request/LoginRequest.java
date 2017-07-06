
package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    private final static long serialVersionUID = 2576787184871920443L;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginRequest() {
    }

    /**
     *
     * @param email
     * @param password
     */
    public LoginRequest(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}