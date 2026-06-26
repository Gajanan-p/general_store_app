package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBillingDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer")
    @Expose
    private GetCustomerDataModel customer;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("tax_amount")
    @Expose
    private Integer taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("due_amount")
    @Expose
    private Integer dueAmount;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("items")
    @Expose
    private List<BillingItem> items;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetBillingDataModel() {
    }

    public GetBillingDataModel(Integer id, String invoiceNo, Integer customerId, GetCustomerDataModel customer, String invoiceDate, String status,
                               Integer subtotal, Integer discountAmount, Integer taxAmount, Integer totalAmount,
                               Integer paidAmount, Integer dueAmount, String notes, List<BillingItem> items) {
        super();
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.customerId = customerId;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
        this.status = status;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.notes = notes;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public GetCustomerDataModel getCustomer() {
        return customer;
    }

    public void setCustomer(GetCustomerDataModel customer) {
        this.customer = customer;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Integer dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<BillingItem> getItems() {
        return items;
    }

    public void setItems(List<BillingItem> items) {
        this.items = items;
    }

}
