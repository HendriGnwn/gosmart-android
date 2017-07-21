
package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherBank implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("behalf_of")
    @Expose
    private String behalfOf;
    private final static long serialVersionUID = 3857946235811860314L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TeacherBank() {
    }

    /**
     *
     * @param id
     * @param name
     * @param branch
     * @param number
     * @param behalfOf
     */
    public TeacherBank(Integer id, String name, String number, String branch, String behalfOf) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.branch = branch;
        this.behalfOf = behalfOf;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBehalfOf() {
        return behalfOf;
    }

    public void setBehalfOf(String behalfOf) {
        this.behalfOf = behalfOf;
    }

}