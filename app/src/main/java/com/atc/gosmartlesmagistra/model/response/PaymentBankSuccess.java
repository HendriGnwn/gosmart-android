package com.atc.gosmartlesmagistra.model.response;

import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.Payment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentBankSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Payment> payments = null;
    private final static long serialVersionUID = -5126805727096904374L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentBankSuccess() {
    }

    /**
     *
     * @param message
     * @param payments
     * @param status
     */
    public PaymentBankSuccess(Integer status, String message, List<Payment> payments) {
        super();
        this.status = status;
        this.message = message;
        this.payments = payments;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}