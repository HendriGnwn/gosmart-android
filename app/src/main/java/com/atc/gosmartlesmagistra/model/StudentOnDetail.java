package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
