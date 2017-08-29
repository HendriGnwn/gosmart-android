package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestHonorError implements Serializable
{

    @SerializedName("total")
    @Expose
    private String total;
    private final static long serialVersionUID = 2806983365419554275L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestHonorError() {
    }

    /**
     *
     * @param total
     */
    public RequestHonorError(String total) {
        super();
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}