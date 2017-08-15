package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private OrderError orderError;
    private final static long serialVersionUID = -6099682760559360293L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResponse() {
    }

    /**
     *
     * @param message
     * @param orderError
     * @param status
     */
    public OrderResponse(Integer status, String message, OrderError orderError) {
        super();
        this.status = status;
        this.message = message;
        this.orderError = orderError;
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

    public OrderError getOrderError() {
        return orderError;
    }

    public void setOrderError(OrderError orderError) {
        this.orderError = orderError;
    }

}