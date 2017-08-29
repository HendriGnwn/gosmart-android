package com.atc.gosmartlesmagistra.model.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestHonorRequest implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 8282620332984903046L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestHonorRequest() {
    }

    /**
     *
     * @param total
     */
    public RequestHonorRequest(Integer total) {
        super();
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}