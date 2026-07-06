package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LowStockDashboardModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("unit_code")
    @Expose
    private String unitCode;
    @SerializedName("current_stock")
    @Expose
    private String currentStock;
    @SerializedName("low_stock_alert")
    @Expose
    private String lowStockAlert;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getUnitCode() { return unitCode; }
    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }
    public String getCurrentStock() { return currentStock; }
    public void setCurrentStock(String currentStock) { this.currentStock = currentStock; }
    public String getLowStockAlert() { return lowStockAlert; }
    public void setLowStockAlert(String lowStockAlert) { this.lowStockAlert = lowStockAlert; }
    public String getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(String sellingPrice) { this.sellingPrice = sellingPrice; }
}