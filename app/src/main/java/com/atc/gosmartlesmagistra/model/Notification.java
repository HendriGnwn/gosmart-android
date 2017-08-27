package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("read_at")
    @Expose
    private String readAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("category_message")
    @Expose
    private String categoryMessage;
    private final static long serialVersionUID = -1076557271935905854L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Notification() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param category
     * @param createdAt
     * @param description
     * @param name
     * @param readAt
     * @param categoryMessage
     */
    public Notification(Integer id, String name, String description, Integer category, String readAt, String createdAt, String updatedAt, String categoryMessage) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.readAt = readAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.categoryMessage = categoryMessage;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategoryMessage() {
        return categoryMessage;
    }

    public void setCategoryMessage(String categoryMessage) {
        this.categoryMessage = categoryMessage;
    }

    public String getFormattedCreatedAt() {
        String formattedDate;
        if (this.getCreatedAt() == null) {
            return "-";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(this.getCreatedAt());

            SimpleDateFormat outGoing = new SimpleDateFormat("dd MMM yyyy H:m", new Locale("id", "ID"));
            formattedDate = outGoing.format(date);
        } catch (ParseException e) {
            formattedDate = this.getCreatedAt();
            e.printStackTrace();
        }

        return formattedDate;
    }

}