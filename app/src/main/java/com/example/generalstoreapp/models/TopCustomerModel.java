package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopCustomerModel {
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("total_sales")
    @Expose
    private String totalSales;
    @SerializedName("invoice_count")
    @Expose
    private Integer invoiceCount;
    @SerializedName("outstanding_balance")
    @Expose
    private String outstandingBalance;

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getTotalSales() { return totalSales; }
    public void setTotalSales(String totalSales) { this.totalSales = totalSales; }
    public Integer getInvoiceCount() { return invoiceCount; }
    public void setInvoiceCount(Integer invoiceCount) { this.invoiceCount = invoiceCount; }
    public String getOutstandingBalance() { return outstandingBalance; }
    public void setOutstandingBalance(String outstandingBalance) { this.outstandingBalance = outstandingBalance; }
}