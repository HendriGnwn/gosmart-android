
package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest implements Serializable
{

    @SerializedName("current_password")
    @Expose
    private String currentPassword;
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword;
    private final static long serialVersionUID = 374997243259195031L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePasswordRequest() {
    }

    /**
     *
     * @param confirmPassword
     * @param newPassword
     * @param currentPassword
     */
    public ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
        super();
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}