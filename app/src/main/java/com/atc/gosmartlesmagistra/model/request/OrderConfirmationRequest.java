package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderConfirmationRequest implements Serializable
{

    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("bank_number")
    @Expose
    private Double bankNumber;
    @SerializedName("bank_holder_name")
    @Expose
    private String bankHolderName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("evidence")
    @Expose
    private String evidence;
    private final static long serialVersionUID = 7953441066389453980L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderConfirmationRequest() {
    }

    /**
     *
     * @param amount
     * @param evidence
     * @param bankHolderName
     * @param bankNumber
     * @param bankId
     */
    public OrderConfirmationRequest(Integer bankId, Double bankNumber, String bankHolderName, String amount, String evidence) {
        super();
        this.bankId = bankId;
        this.bankNumber = bankNumber;
        this.bankHolderName = bankHolderName;
        this.amount = amount;
        this.evidence = evidence;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Double getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(Double bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankHolderName() {
        return bankHolderName;
    }

    public void setBankHolderName(String bankHolderName) {
        this.bankHolderName = bankHolderName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

}