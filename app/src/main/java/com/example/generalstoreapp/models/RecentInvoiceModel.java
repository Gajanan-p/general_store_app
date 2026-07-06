package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentInvoiceModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("due_amount")
    @Expose
    private String dueAmount;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("is_cancelled")
    @Expose
    private Boolean isCancelled;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
    public String getPaidAmount() { return paidAmount; }
    public void setPaidAmount(String paidAmount) { this.paidAmount = paidAmount; }
    public String getDueAmount() { return dueAmount; }
    public void setDueAmount(String dueAmount) { this.dueAmount = dueAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public Boolean getIsCancelled() { return isCancelled; }
    public void setIsCancelled(Boolean isCancelled) { this.isCancelled = isCancelled; }
}