package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingRequest {
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("items")
    @Expose
    private List<BillingItemsRequest> items;
    @SerializedName("paid_amount")
    @Expose
    private Double paidAmount;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod; // Compatibility

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }
    public List<BillingItemsRequest> getItems() { return items; }
    public void setItems(List<BillingItemsRequest> items) { this.items = items; }
    public Double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Double paidAmount) { this.paidAmount = paidAmount; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}