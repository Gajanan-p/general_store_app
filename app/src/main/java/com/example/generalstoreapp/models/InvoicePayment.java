package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoicePayment {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}