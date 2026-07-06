package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPaymentRequest {
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}