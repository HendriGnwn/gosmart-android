package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionCheckRequest implements Serializable
{

    @SerializedName("private_detail_id")
    @Expose
    private Integer privateDetailId;
    @SerializedName("on_at")
    @Expose
    private String onAt;
    @SerializedName("checklist")
    @Expose
    private Integer checklist;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -6474420693210629307L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SectionCheckRequest() {
    }

    /**
     *
     * @param onAt
     * @param description
     * @param privateDetailId
     * @param checklist
     */
    public SectionCheckRequest(Integer privateDetailId, String onAt, Integer checklist, String description) {
        super();
        this.privateDetailId = privateDetailId;
        this.onAt = onAt;
        this.checklist = checklist;
        this.description = description;
    }

    public Integer getPrivateDetailId() {
        return privateDetailId;
    }

    public void setPrivateDetailId(Integer privateDetailId) {
        this.privateDetailId = privateDetailId;
    }

    public String getOnAt() {
        return onAt;
    }

    public void setOnAt(String onAt) {
        this.onAt = onAt;
    }

    public Integer getChecklist() {
        return checklist;
    }

    public void setChecklist(Integer checklist) {
        this.checklist = checklist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}