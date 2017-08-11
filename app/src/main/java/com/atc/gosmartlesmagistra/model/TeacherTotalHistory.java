
package com.atc.gosmartlesmagistra.model;

import android.content.Context;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
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
    @SerializedName("evidence")
    @Expose
    private String evidence;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("approved_at")
    @Expose
    private String approvedAt;
    @SerializedName("done_at")
    @Expose
    private String doneAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("private_model")
    @Expose
    private PrivateModel privateModel;
    private final static long serialVersionUID = -3518989595867204791L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherTotalHistory() {
    }

    /**
     *
     * @param updatedAt
     * @param doneAt
     * @param total
     * @param id
     * @param operation
     * @param status
     * @param createdAt
     * @param evidence
     * @param privateModel
     * @param approvedAt
     * @param privateId
     */
    public TeacherTotalHistory(Integer id, Integer privateId, Integer operation, String total, String evidence, Integer status, String approvedAt, String doneAt, String createdAt, String updatedAt, PrivateModel privateModel) {
        super();
        this.id = id;
        this.privateId = privateId;
        this.operation = operation;
        this.total = total;
        this.evidence = evidence;
        this.status = status;
        this.approvedAt = approvedAt;
        this.doneAt = doneAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.privateModel = privateModel;
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

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(String doneAt) {
        this.doneAt = doneAt;
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

    public PrivateModel getPrivateModel() {
        return privateModel;
    }

    public void setPrivateModel(PrivateModel privateModel) {
        this.privateModel = privateModel;
    }

    public String getStatusText() {
        switch (this.getStatus()) {
            case 0:
                return "Rejected";
            case 1:
                return "Approved";
            case 5:
                return "Waiting for Approve";
            case 10:
                return "Done";
        }

        return "-";
    }

    public Integer getStatusColor(Context context) {
        switch (this.getStatus()) {
            case 0:
                return context.getResources().getColor(R.color.colorAccent);
            case 1:
                return context.getResources().getColor(R.color.colorPrimary);
            case 5:
                return context.getResources().getColor(R.color.colorAccent);
            case 10:
                return context.getResources().getColor(R.color.colorGreen);
        }

        return context.getResources().getColor(R.color.colorBlackSecondary);
    }

    public String getDescriptionOperationTimeAt(Context context) {

        if (this.getOperation() == 1) {

            return "Created At " + this.getFormattedCreatedAt();

        } else {

            switch (this.getStatus()) {
                case 0:
                    return "Rejected At " + this.getFormattedUpdatedAt();
                case 1:
                    return "Approved At " + this.getFormattedApprovedAt();
                case 5:
                    return "Created At " + this.getFormattedCreatedAt();
                case 10:
                    return "Done At " + this.getFormattedDoneAt();
            }
        }

        return "-";

    }

    public String getFormattedTotal() {
        return App.getFormattedCurrencyRupiah(this.getTotal());
    }

    public String getFormattedCreatedAt() {
        String formattedDate;
        if (this.getCreatedAt() == null) {
            return "-";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(this.getCreatedAt());

            SimpleDateFormat outGoing = new SimpleDateFormat("dd MMM yyyy h:m a", new Locale("id", "ID"));
            formattedDate = outGoing.format(date);
        } catch (ParseException e) {
            formattedDate = this.getCreatedAt();
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String getFormattedApprovedAt() {
        String formattedDate;
        if (this.getApprovedAt() == null) {
            return "-";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(this.getApprovedAt());

            SimpleDateFormat outGoing = new SimpleDateFormat("dd MMM yyyy h:m a", new Locale("id", "ID"));
            formattedDate = outGoing.format(date);
        } catch (ParseException e) {
            formattedDate = this.getApprovedAt();
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String getFormattedUpdatedAt() {
        String formattedDate;
        if (this.getUpdatedAt() == null) {
            return "-";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(this.getUpdatedAt());

            SimpleDateFormat outGoing = new SimpleDateFormat("dd MMM yyyy h:m a", new Locale("id", "ID"));
            formattedDate = outGoing.format(date);
        } catch (ParseException e) {
            formattedDate = this.getUpdatedAt();
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String getFormattedDoneAt() {
        String formattedDate;
        if (this.getDoneAt() == null) {
            return "-";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(this.getDoneAt());

            SimpleDateFormat outGoing = new SimpleDateFormat("dd MMM yyyy h:m a", new Locale("id", "ID"));
            formattedDate = outGoing.format(date);
        } catch (ParseException e) {
            formattedDate = this.getDoneAt();
            e.printStackTrace();
        }

        return formattedDate;
    }



}