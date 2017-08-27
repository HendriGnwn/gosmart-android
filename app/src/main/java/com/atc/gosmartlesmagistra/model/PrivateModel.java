package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("section")
    @Expose
    private Integer section;
    @SerializedName("section_time")
    @Expose
    private String sectionTime;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("private_details")
    @Expose
    private List<PrivateDetail> privateDetails = null;
    private final static long serialVersionUID = -3885477888024162584L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateModel() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param startDate
     * @param status
     * @param createdAt
     * @param endDate
     * @param code
     * @param sectionTime
     * @param section
     * @param orderId
     * @param privateDetails
     */
    public PrivateModel(Integer id, Integer orderId, Integer section, String sectionTime, String code, String startDate, String endDate, Integer status, String createdAt, String updatedAt, List<PrivateDetail> privateDetails) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.section = section;
        this.sectionTime = sectionTime;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.privateDetails = privateDetails;
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

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(String sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<PrivateDetail> getPrivateDetails() {
        return privateDetails;
    }

    public void setPrivateDetails(List<PrivateDetail> privateDetails) {
        this.privateDetails = privateDetails;
    }

}
