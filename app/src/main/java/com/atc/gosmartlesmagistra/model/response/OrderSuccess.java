package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;

import com.atc.gosmartlesmagistra.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Order order;
    private final static long serialVersionUID = -5126805727096904374L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderSuccess() {
    }

    /**
     *
     * @param message
     * @param order
     * @param status
     */
    public OrderSuccess(Integer status, String message, Order order) {
        super();
        this.status = status;
        this.message = message;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}