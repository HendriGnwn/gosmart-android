
package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    private final static long serialVersionUID = 1998714031189797379L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ForgotPasswordRequest() {
    }

    /**
     *
     * @param email
     */
    public ForgotPasswordRequest(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}