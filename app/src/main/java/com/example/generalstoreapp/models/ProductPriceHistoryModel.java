package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPriceHistoryModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("old_purchase_price")
    @Expose
    private String oldPurchasePrice;
    @SerializedName("new_purchase_price")
    @Expose
    private String newPurchasePrice;
    @SerializedName("old_selling_price")
    @Expose
    private String oldSellingPrice;
    @SerializedName("new_selling_price")
    @Expose
    private String newSellingPrice;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("changed_by")
    @Expose
    private Integer changedBy;
    @SerializedName("change_source")
    @Expose
    private String changeSource;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getOldPurchasePrice() { return oldPurchasePrice; }
    public void setOldPurchasePrice(String oldPurchasePrice) { this.oldPurchasePrice = oldPurchasePrice; }
    public String getNewPurchasePrice() { return newPurchasePrice; }
    public void setNewPurchasePrice(String newPurchasePrice) { this.newPurchasePrice = newPurchasePrice; }
    public String getOldSellingPrice() { return oldSellingPrice; }
    public void setOldSellingPrice(String oldSellingPrice) { this.oldSellingPrice = oldSellingPrice; }
    public String getNewSellingPrice() { return newSellingPrice; }
    public void setNewSellingPrice(String newSellingPrice) { this.newSellingPrice = newSellingPrice; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Integer getChangedBy() { return changedBy; }
    public void setChangedBy(Integer changedBy) { this.changedBy = changedBy; }
    public String getChangeSource() { return changeSource; }
    public void setChangeSource(String changeSource) { this.changeSource = changeSource; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}