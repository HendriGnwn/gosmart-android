
package com.atc.gosmartlesmagistra.model.response;

import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private PrivateModel privateModel;
    private final static long serialVersionUID = -6063553094077069824L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewSuccess() {
    }

    /**
     *
     * @param message
     * @param status
     * @param privateModel
     */
    public ReviewSuccess(Integer status, String message, PrivateModel privateModel) {
        super();
        this.status = status;
        this.message = message;
        this.privateModel = privateModel;
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

    public PrivateModel getPrivateModel() {
        return privateModel;
    }

    public void setPrivateModel(PrivateModel privateModel) {
        this.privateModel = privateModel;
    }

}