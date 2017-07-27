
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordError implements Serializable
{

    @SerializedName("current_password")
    @Expose
    private Object currentPassword;
    @SerializedName("new_password")
    @Expose
    private Object newPassword;
    @SerializedName("confirm_password")
    @Expose
    private Object confirmPassword;
    private final static long serialVersionUID = 5268385469074064828L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePasswordError() {
    }

    /**
     *
     * @param confirmPassword
     * @param newPassword
     * @param currentPassword
     */
    public ChangePasswordError(Object currentPassword, Object newPassword, Object confirmPassword) {
        super();
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public Object getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(Object currentPassword) {
        this.currentPassword = currentPassword;
    }

    public Object getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(Object newPassword) {
        this.newPassword = newPassword;
    }

    public Object getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(Object confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}