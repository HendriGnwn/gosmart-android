package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestHonorResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("validators")
    @Expose
    private RequestHonorError requestHonorError;
    private final static long serialVersionUID = -1474382498714899613L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestHonorResponse() {
    }

    /**
     *
     * @param message
     * @param requestHonorError
     * @param status
     */
    public RequestHonorResponse(Integer status, String message, RequestHonorError requestHonorError) {
        super();
        this.status = status;
        this.message = message;
        this.requestHonorError = requestHonorError;
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

    public RequestHonorError getRequestHonorError() {
        return requestHonorError;
    }

    public void setRequestHonorError(RequestHonorError requestHonorError) {
        this.requestHonorError = requestHonorError;
    }

}