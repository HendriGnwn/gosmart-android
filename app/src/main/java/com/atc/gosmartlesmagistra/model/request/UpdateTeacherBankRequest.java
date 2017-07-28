package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherBankRequest implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("behalf_of")
    @Expose
    private String behalfOf;
    private final static long serialVersionUID = -3348591730916267581L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherBankRequest() {
    }

    /**
     *
     * @param name
     * @param branch
     * @param number
     * @param behalfOf
     */
    public UpdateTeacherBankRequest(String name, String number, String branch, String behalfOf) {
        super();
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.behalfOf = behalfOf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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