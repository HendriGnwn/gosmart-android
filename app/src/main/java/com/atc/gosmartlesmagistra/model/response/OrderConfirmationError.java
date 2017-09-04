package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderConfirmationError implements Serializable
{

    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("bank_number")
    @Expose
    private String bankNumber;
    @SerializedName("bank_holder_name")
    @Expose
    private String bankHolderName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("evidence")
    @Expose
    private String evidence;
    private final static long serialVersionUID = 5853220686440857564L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderConfirmationError() {
    }

    /**
     *
     * @param amount
     * @param evidence
     * @param bankHolderName
     * @param bankNumber
     * @param bankId
     */
    public OrderConfirmationError(String bankId, String bankNumber, String bankHolderName, String amount, String evidence) {
        super();
        this.bankId = bankId;
        this.bankNumber = bankNumber;
        this.bankHolderName = bankHolderName;
        this.amount = amount;
        this.evidence = evidence;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
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