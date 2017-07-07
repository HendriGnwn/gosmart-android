
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherTotalHistory implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("private_id")
    @Expose
    private Integer privateId;
    @SerializedName("operation")
    @Expose
    private Integer operation;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = 182041310247650252L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TeacherTotalHistory() {
    }

    /**
     * 
     * @param updatedAt
     * @param total
     * @param id
     * @param operation
     * @param createdAt
     * @param privateId
     */
    public TeacherTotalHistory(Integer id, Integer privateId, Integer operation, String total, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.privateId = privateId;
        this.operation = operation;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrivateId() {
        return privateId;
    }

    public void setPrivateId(Integer privateId) {
        this.privateId = privateId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
