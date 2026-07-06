package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductDataModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
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
    private String purchasePrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("current_stock")
    @Expose
    private String currentStock;
    @SerializedName("low_stock_alert")
    @Expose
    private String lowStockAlert;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("category")
    @Expose
    private GetCategoriesModel category;
    @SerializedName("unit")
    @Expose
    private GetUnitsDataModel unit;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Integer getUnitId() { return unitId; }
    public void setUnitId(Integer unitId) { this.unitId = unitId; }
    public String getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(String purchasePrice) { this.purchasePrice = purchasePrice; }
    public String getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(String sellingPrice) { this.sellingPrice = sellingPrice; }
    public String getCurrentStock() { return currentStock; }
    public void setCurrentStock(String currentStock) { this.currentStock = currentStock; }
    public String getLowStockAlert() { return lowStockAlert; }
    public void setLowStockAlert(String lowStockAlert) { this.lowStockAlert = lowStockAlert; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public GetCategoriesModel getCategory() { return category; }
    public void setCategory(GetCategoriesModel category) { this.category = category; }
    public GetUnitsDataModel getUnit() { return unit; }
    public void setUnit(GetUnitsDataModel unit) { this.unit = unit; }

    // Compatibility methods
    public Double getSellPrice() {
        try { return Double.parseDouble(sellingPrice); } catch (Exception e) { return 0.0; }
    }
    public Double getCostPrice() {
        try { return Double.parseDouble(purchasePrice); } catch (Exception e) { return 0.0; }
    }
    public Integer getStockQty() {
        try { return (int) Double.parseDouble(currentStock); } catch (Exception e) { return 0; }
    }
    public Integer getGstPercent() { return 0; } // Field missing in new API example, keeping for compat
}