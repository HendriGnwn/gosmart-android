package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentOnDetail implements Serializable
{

    @SerializedName("on_at")
    @Expose
    private String onAt;
    @SerializedName("check")
    @Expose
    private Integer check;
    @SerializedName("check_at")
    @Expose
    private String checkAt;
    private final static long serialVersionUID = 4794788667329386300L;

    /**
     * No args constructor for use in serialization
     *
     */
    public StudentOnDetail() {
    }

    /**
     *
     * @param onAt
     * @param check
     * @param checkAt
     */
    public StudentOnDetail(String onAt, Integer check, String checkAt) {
        super();
        this.onAt = onAt;
        this.check = check;
        this.checkAt = checkAt;
    }

    public String getOnAt() {
        return onAt;
    }

    public void setOnAt(String onAt) {
        this.onAt = onAt;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public String getCheckAt() {
        return checkAt;
    }

    public void setCheckAt(String checkAt) {
        this.checkAt = checkAt;
    }

}
