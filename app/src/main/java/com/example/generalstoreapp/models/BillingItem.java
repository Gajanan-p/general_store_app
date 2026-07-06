package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingItem {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoiceId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name_snapshot")
    @Expose
    private String productNameSnapshot;
    @SerializedName("sku_snapshot")
    @Expose
    private String skuSnapshot;
    @SerializedName("unit_name_snapshot")
    @Expose
    private String unitNameSnapshot;
    @SerializedName("unit_code_snapshot")
    @Expose
    private String unitCodeSnapshot;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("line_total")
    @Expose
    private Double lineTotal;
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
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProductNameSnapshot() { return productNameSnapshot; }
    public void setProductNameSnapshot(String productNameSnapshot) { this.productNameSnapshot = productNameSnapshot; }
    public String getSkuSnapshot() { return skuSnapshot; }
    public void setSkuSnapshot(String skuSnapshot) { this.skuSnapshot = skuSnapshot; }
    public String getUnitNameSnapshot() { return unitNameSnapshot; }
    public void setUnitNameSnapshot(String unitNameSnapshot) { this.unitNameSnapshot = unitNameSnapshot; }
    public String getUnitCodeSnapshot() { return unitCodeSnapshot; }
    public void setUnitCodeSnapshot(String unitCodeSnapshot) { this.unitCodeSnapshot = unitCodeSnapshot; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public Double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }
    public Double getLineTotal() { return lineTotal; }
    public void setLineTotal(Double lineTotal) { this.lineTotal = lineTotal; }
    
    // Compatibility getters for UI
    public Double getQty() { return quantity; }
    public Double getSellPrice() { return rate; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}