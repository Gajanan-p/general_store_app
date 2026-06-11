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
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_txn_ref")
    @Expose
    private String paymentTxnRef;
    @SerializedName("items")
    @Expose
    private List<BillingItemsRequest> items;

    /**
     * No args constructor for use in serialization
     *
     */
    public BillingRequest() {
    }

    public BillingRequest(Integer customerId, String invoiceDate, Integer discountAmount,
                          String notes, Integer paidAmount, String paymentMethod,
                          String paymentTxnRef, List<BillingItemsRequest> items) {
        super();
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.discountAmount = discountAmount;
        this.notes = notes;
        this.paidAmount = paidAmount;
        this.paymentMethod = paymentMethod;
        this.paymentTxnRef = paymentTxnRef;
        this.items = items;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentTxnRef() {
        return paymentTxnRef;
    }

    public void setPaymentTxnRef(String paymentTxnRef) {
        this.paymentTxnRef = paymentTxnRef;
    }

    public List<BillingItemsRequest> getItems() {
        return items;
    }

    public void setItems(List<BillingItemsRequest> items) {
        this.items = items;
    }

}