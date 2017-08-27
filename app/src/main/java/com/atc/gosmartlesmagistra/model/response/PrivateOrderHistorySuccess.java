
package com.atc.gosmartlesmagistra.model.response;

import java.io.Serializable;
import java.util.List;

import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateOrderHistorySuccess implements Serializable
{

    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("data")
    @Expose
    private List<PrivateModel> privateModels = null;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("last_page")
    @Expose
    private Integer lastPage;
    @SerializedName("next_page_url")
    @Expose
    private Object nextPageUrl;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("prev_page_url")
    @Expose
    private Object prevPageUrl;
    @SerializedName("to")
    @Expose
    private Integer to;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 9185083394021036112L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrivateOrderHistorySuccess() {
    }

    /**
     *
     * @param message
     * @param total
     * @param to
     * @param lastPage
     * @param nextPageUrl
     * @param status
     * @param prevPageUrl
     * @param path
     * @param privateModels
     * @param perPage
     * @param from
     * @param currentPage
     */
    public PrivateOrderHistorySuccess(Integer currentPage, List<PrivateModel> privateModels, Integer from, Integer lastPage, Object nextPageUrl, String path, Integer perPage, Object prevPageUrl, Integer to, Integer total, Integer status, String message) {
        super();
        this.currentPage = currentPage;
        this.privateModels = privateModels;
        this.from = from;
        this.lastPage = lastPage;
        this.nextPageUrl = nextPageUrl;
        this.path = path;
        this.perPage = perPage;
        this.prevPageUrl = prevPageUrl;
        this.to = to;
        this.total = total;
        this.status = status;
        this.message = message;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<PrivateModel> getPrivateModels() {
        return privateModels;
    }

    public void setPrivateModels(List<PrivateModel> privateModels) {
        this.privateModels = privateModels;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Object getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(Object prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

}
