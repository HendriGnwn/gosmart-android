package com.atc.gosmartlesmagistra.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACER on 27/08/2017.
 */

public class ReviewRequest {

    @SerializedName("unique_number")
    @Expose
    private String uniqueNumber;
    @SerializedName("private_id")
    @Expose
    private Integer privateId;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -1207704307475214506L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReviewRequest() {
    }

    public ReviewRequest(String uniqueNumber, Integer privateId, String rate, String description) {
        super();
        this.uniqueNumber = uniqueNumber;
        this.privateId = privateId;
        this.rate = rate;
        this.description = description;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public Integer getPrivateId() {
        return privateId;
    }

    public void setLastName(Integer privateId) {
        this.privateId = privateId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
