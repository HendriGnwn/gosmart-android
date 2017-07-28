package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherProfileResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private UpdateTeacherProfileError updateTeacherProfileError;
    private final static long serialVersionUID = 3671086493935622019L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherProfileResponse() {
    }

    /**
     *
     * @param message
     * @param updateTeacherProfileError
     * @param status
     */
    public UpdateTeacherProfileResponse(Integer status, String message, UpdateTeacherProfileError updateTeacherProfileError) {
        super();
        this.status = status;
        this.message = message;
        this.updateTeacherProfileError = updateTeacherProfileError;
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

    public UpdateTeacherProfileError getUpdateTeacherProfileError() {
        return updateTeacherProfileError;
    }

    public void setUpdateTeacherProfileError(UpdateTeacherProfileError updateTeacherProfileError) {
        this.updateTeacherProfileError = updateTeacherProfileError;
    }

}
