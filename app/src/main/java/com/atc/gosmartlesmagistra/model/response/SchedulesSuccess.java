package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import java.util.List;

import com.atc.gosmartlesmagistra.model.Schedule;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchedulesSuccess implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Schedule> schedules = null;
    private final static long serialVersionUID = -6761143712229926346L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SchedulesSuccess() {
    }

    /**
     *
     * @param message
     * @param schedules
     * @param status
     */
    public SchedulesSuccess(Integer status, String message, List<Schedule> schedules) {
        super();
        this.status = status;
        this.message = message;
        this.schedules = schedules;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

}