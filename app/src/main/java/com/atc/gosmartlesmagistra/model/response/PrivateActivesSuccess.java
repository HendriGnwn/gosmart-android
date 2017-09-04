package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import java.util.List;

import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateActivesSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<PrivateModel> privateModels = null;
    private final static long serialVersionUID = 3703095698724539851L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateActivesSuccess() {
    }

    /**
     *
     * @param message
     * @param status
     * @param privateModels
     */
    public PrivateActivesSuccess(Integer status, String message, List<PrivateModel> privateModels) {
        super();
        this.status = status;
        this.message = message;
        this.privateModels = privateModels;
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

    public List<PrivateModel> getPrivateModels() {
        return privateModels;
    }

    public void setPrivateModels(List<PrivateModel> privateModels) {
        this.privateModels = privateModels;
    }

}