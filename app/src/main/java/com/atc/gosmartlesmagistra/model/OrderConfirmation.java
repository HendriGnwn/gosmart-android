package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderConfirmation implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("bank_number")
    @Expose
    private String bankNumber;
    @SerializedName("bank_behalf_of")
    @Expose
    private String bankBehalfOf;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("upload_bukti")
    @Expose
    private String uploadBukti;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = 7881388513786510803L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderConfirmation() {
    }

    /**
     *
     * @param updatedAt
     * @param amount
     * @param id
     * @param bankBehalfOf
     * @param uploadBukti
     * @param createdAt
     * @param description
     * @param orderId
     * @param bankNumber
     * @param bankId
     */
    public OrderConfirmation(Integer id, Integer orderId, Integer bankId, String bankNumber, String bankBehalfOf, String amount, String uploadBukti, String description, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.bankId = bankId;
        this.bankNumber = bankNumber;
        this.bankBehalfOf = bankBehalfOf;
        this.amount = amount;
        this.uploadBukti = uploadBukti;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankBehalfOf() {
        return bankBehalfOf;
    }

    public void setBankBehalfOf(String bankBehalfOf) {
        this.bankBehalfOf = bankBehalfOf;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUploadBukti() {
        return uploadBukti;
    }

    public void setUploadBukti(String uploadBukti) {
        this.uploadBukti = uploadBukti;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}