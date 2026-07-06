package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPaymentDataModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("customer")
    @Expose
    private GetCustomerDataModel customer;
    @SerializedName("invoice")
    @Expose
    private RecentInvoiceModel invoice;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public GetCustomerDataModel getCustomer() { return customer; }
    public void setCustomer(GetCustomerDataModel customer) { this.customer = customer; }
    public RecentInvoiceModel getInvoice() { return invoice; }
    public void setInvoice(RecentInvoiceModel invoice) { this.invoice = invoice; }
}