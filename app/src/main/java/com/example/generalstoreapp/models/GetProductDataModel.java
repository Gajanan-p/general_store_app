package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("unit_id")
    @Expose
    private Integer unitId;
    @SerializedName("gst_percent")
    @Expose
    private Integer gstPercent;
    @SerializedName("cost_price")
    @Expose
    private Integer costPrice;
    @SerializedName("sell_price")
    @Expose
    private Integer sellPrice;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("stock_qty")
    @Expose
    private Integer stockQty;
    @SerializedName("low_stock_alert")
    @Expose
    private Integer lowStockAlert;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetProductDataModel() {
    }

    public GetProductDataModel(Integer id, String name, String sku, String barcode, Integer categoryId, Integer unitId, Integer gstPercent, Integer costPrice, Integer sellPrice, Integer mrp, Integer stockQty, Integer lowStockAlert, Integer isActive, String createdDate, String updatedDate) {
        super();
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.barcode = barcode;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.gstPercent = gstPercent;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.mrp = mrp;
        this.stockQty = stockQty;
        this.lowStockAlert = lowStockAlert;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(Integer gstPercent) {
        this.gstPercent = gstPercent;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public Integer getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(Integer lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}