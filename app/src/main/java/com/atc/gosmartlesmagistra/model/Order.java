package com.atc.gosmartlesmagistra.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order implements Serializable
{
    public static Integer statusCanceled = 0;
    public static Integer statusDraft = 1;
    public static Integer statusWaitingForPayment = 3;
    public static Integer statusConfirmed = 5;
    public static Integer statusPaid = 10;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("section")
    @Expose
    private Integer section;
    @SerializedName("section_time")
    @Expose
    private String sectionTime;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("admin_fee")
    @Expose
    private String adminFee;
    @SerializedName("final_amount")
    @Expose
    private String finalAmount;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("confirmed_at")
    @Expose
    private String confirmedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("teacher")
    @Expose
    private User user;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("order_confirmation")
    @Expose
    private OrderConfirmation orderConfirmation;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;
    private final static long serialVersionUID = -1126829416159670746L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Order() {
    }

    /**
     *
     * @param startDate
     * @param orderDetails
     * @param payment
     * @param status
     * @param paymentId
     * @param endDate
     * @param code
     * @param sectionTime
     * @param section
     * @param statusMessage
     * @param updatedAt
     * @param id
     * @param finalAmount
     * @param createdAt
     * @param adminFee
     * @param orderConfirmation
     * @param user
     * @param confirmedAt
     */
    public Order(Integer id, String code, Integer section, String sectionTime, String startDate, String endDate, String adminFee, String finalAmount, Integer paymentId, Integer status, String confirmedAt, String createdAt, String updatedAt, String statusMessage, User user, Payment payment, OrderConfirmation orderConfirmation, List<OrderDetail> orderDetails) {
        super();
        this.id = id;
        this.code = code;
        this.section = section;
        this.sectionTime = sectionTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adminFee = adminFee;
        this.finalAmount = finalAmount;
        this.paymentId = paymentId;
        this.status = status;
        this.confirmedAt = confirmedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusMessage = statusMessage;
        this.user = user;
        this.payment = payment;
        this.orderConfirmation = orderConfirmation;
        this.orderDetails = orderDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(String sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public OrderConfirmation getOrderConfirmation() {
        return orderConfirmation;
    }

    public void setOrderConfirmation(OrderConfirmation orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getFormattedFinalAmount() {
        return App.getFormattedCurrencyRupiah(this.getFinalAmount());
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

    public boolean getStatusIsFormConfirmation() {
        if (this.getStatus() != Order.statusPaid || this.getStatus() != Order.statusConfirmed) {
            return false;
        }

        return true;
    }

}