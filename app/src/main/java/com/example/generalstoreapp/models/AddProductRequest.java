package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddProductRequest {

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
    @SerializedName("opening_stock")
    @Expose
    private Integer openingStock;
    @SerializedName("low_stock_alert")
    @Expose
    private Integer lowStockAlert;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddProductRequest() {
    }

    public AddProductRequest(String name, String sku, String barcode, Integer categoryId, Integer unitId, Integer gstPercent, Integer costPrice, Integer sellPrice, Integer mrp, Integer openingStock, Integer lowStockAlert, Integer isActive) {
        super();
        this.name = name;
        this.sku = sku;
        this.barcode = barcode;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.gstPercent = gstPercent;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.mrp = mrp;
        this.openingStock = openingStock;
        this.lowStockAlert = lowStockAlert;
        this.isActive = isActive;
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

    public Integer getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Integer openingStock) {
        this.openingStock = openingStock;
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

}