package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderConfirmationResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private OrderConfirmationError orderConfirmationError;
    private final static long serialVersionUID = -1657253979499144966L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderConfirmationResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param orderConfirmationError
     */
    public OrderConfirmationResponse(Integer status, String message, OrderConfirmationError orderConfirmationError) {
        super();
        this.status = status;
        this.message = message;
        this.orderConfirmationError = orderConfirmationError;
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

    public OrderConfirmationError getOrderConfirmationError() {
        return orderConfirmationError;
    }

    public void setOrderConfirmationError(OrderConfirmationError orderConfirmationError) {
        this.orderConfirmationError = orderConfirmationError;
    }

}