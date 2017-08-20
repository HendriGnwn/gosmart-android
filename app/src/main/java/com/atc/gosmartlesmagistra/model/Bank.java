package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("behalf_of")
    @Expose
    private String behalfOf;
    private final static long serialVersionUID = -5058391119523592316L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Bank() {
    }

    /**
     *
     * @param id
     * @param description
     * @param paymentId
     * @param name
     * @param branch
     * @param image
     * @param behalfOf
     */
    public Bank(Integer id, Integer paymentId, String name, String number, String image, String description, String branch, String behalfOf) {
        super();
        this.id = id;
        this.paymentId = paymentId;
        this.name = name;
        this.number = number;
        this.image = image;
        this.description = description;
        this.branch = branch;
        this.behalfOf = behalfOf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return "2240026255";
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBehalfOf() {
        return behalfOf;
    }

    public void setBehalfOf(String behalfOf) {
        this.behalfOf = behalfOf;
    }

}