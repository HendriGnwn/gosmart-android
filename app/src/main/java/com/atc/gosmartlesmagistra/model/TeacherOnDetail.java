package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    @SerializedName("description")
    @Expose
    private String description;
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
    public TeacherOnDetail(String onAt, Integer check, String checkAt, String description) {
        super();
        this.onAt = onAt;
        this.check = check;
        this.checkAt = checkAt;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckText()
    {
        if (this.getCheck() == 1) {
            return "Ya";
        } else {
            return "Belum";
        }
    }

    public String getFormattedOnAt() {
        String choose = this.getOnAt();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(choose);
            SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy H:00", new Locale("id", "ID"));
            choose = formatted.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return choose;
    }

    public String getFormattedCheckAt() {
        String choose = this.getCheckAt();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(choose);
            SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy H:00", new Locale("id", "ID"));
            choose = formatted.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return choose;
    }

}
