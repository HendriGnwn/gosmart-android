package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherOnDetail implements Serializable
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
    private final static long serialVersionUID = 8943937065539780546L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherOnDetail() {
    }

    /**
     *
     * @param onAt
     * @param check
     * @param checkAt
     */
    public TeacherOnDetail(String onAt, Integer check, String checkAt) {
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
