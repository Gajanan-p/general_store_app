package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardSummaryModel {
    @SerializedName("today_sales")
    @Expose
    private String todaySales;
    @SerializedName("total_sales")
    @Expose
    private String totalSales;
    @SerializedName("today_payments")
    @Expose
    private String todayPayments;
    @SerializedName("total_payments")
    @Expose
    private String totalPayments;
    @SerializedName("today_expenses")
    @Expose
    private String todayExpenses;
    @SerializedName("total_expenses")
    @Expose
    private String totalExpenses;
    @SerializedName("total_outstanding")
    @Expose
    private String totalOutstanding;
    @SerializedName("low_stock_count")
    @Expose
    private Integer lowStockCount;
    @SerializedName("unpaid_invoice_count")
    @Expose
    private Integer unpaidInvoiceCount;
    @SerializedName("total_customers")
    @Expose
    private Integer totalCustomers;
    @SerializedName("total_products")
    @Expose
    private Integer totalProducts;

    public String getTodaySales() { return todaySales; }
    public void setTodaySales(String todaySales) { this.todaySales = todaySales; }
    public String getTotalSales() { return totalSales; }
    public void setTotalSales(String totalSales) { this.totalSales = totalSales; }
    public String getTodayPayments() { return todayPayments; }
    public void setTodayPayments(String todayPayments) { this.todayPayments = todayPayments; }
    public String getTotalPayments() { return totalPayments; }
    public void setTotalPayments(String totalPayments) { this.totalPayments = totalPayments; }
    public String getTodayExpenses() { return todayExpenses; }
    public void setTodayExpenses(String todayExpenses) { this.todayExpenses = todayExpenses; }
    public String getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(String totalExpenses) { this.totalExpenses = totalExpenses; }
    public String getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(String totalOutstanding) { this.totalOutstanding = totalOutstanding; }
    public Integer getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(Integer lowStockCount) { this.lowStockCount = lowStockCount; }
    public Integer getUnpaidInvoiceCount() { return unpaidInvoiceCount; }
    public void setUnpaidInvoiceCount(Integer unpaidInvoiceCount) { this.unpaidInvoiceCount = unpaidInvoiceCount; }
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }
}