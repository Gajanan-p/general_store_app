package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductRequest {
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("unit_id")
    @Expose
    private Integer unitId;
    @SerializedName("purchase_price")
    @Expose
    private Double purchasePrice;
    @SerializedName("selling_price")
    @Expose
    private Double sellingPrice;
    @SerializedName("current_stock")
    @Expose
    private Double currentStock;
    @SerializedName("low_stock_alert")
    @Expose
    private Double lowStockAlert;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("price_change_reason")
    @Expose
    private String priceChangeReason;

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Integer getUnitId() { return unitId; }
    public void setUnitId(Integer unitId) { this.unitId = unitId; }
    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }
    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }
    public Double getCurrentStock() { return currentStock; }
    public void setCurrentStock(Double currentStock) { this.currentStock = currentStock; }
    public Double getLowStockAlert() { return lowStockAlert; }
    public void setLowStockAlert(Double lowStockAlert) { this.lowStockAlert = lowStockAlert; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getPriceChangeReason() { return priceChangeReason; }
    public void setPriceChangeReason(String priceChangeReason) { this.priceChangeReason = priceChangeReason; }
}