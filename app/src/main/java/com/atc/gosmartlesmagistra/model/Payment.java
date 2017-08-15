package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("banks")
    @Expose
    private List<Bank> banks = null;
    private final static long serialVersionUID = 5243461052704265827L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Payment() {
    }

    /**
     *
     * @param id
     * @param order
     * @param name
     * @param banks
     */
    public Payment(Integer id, String name, Integer order, List<Bank> banks) {
        super();
        this.id = id;
        this.name = name;
        this.order = order;
        this.banks = banks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

}