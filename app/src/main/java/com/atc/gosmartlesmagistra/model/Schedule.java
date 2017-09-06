package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule implements Serializable
{

    @SerializedName("private_model")
    @Expose
    private PrivateModel privateModel;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_detail")
    @Expose
    private DateDetail dateDetail = null;
    private final static long serialVersionUID = 7862126579740171755L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Schedule() {
    }

    /**
     *
     * @param message
     * @param privateModel
     * @param date
     */
    public Schedule(PrivateModel privateModel, String message, String date, DateDetail dateDetail) {
        super();
        this.privateModel = privateModel;
        this.message = message;
        this.date = date;
        this.dateDetail = dateDetail;
    }

    public PrivateModel getPrivateModel() {
        return privateModel;
    }

    public void setPrivateModel(PrivateModel privateModel) {
        this.privateModel = privateModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateDetail getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(DateDetail dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getFormattedDate() {
        String choose = this.getDate();
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