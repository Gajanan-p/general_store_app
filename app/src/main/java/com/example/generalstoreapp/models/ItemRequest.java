package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ItemRequest {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("cost_price")
    @Expose
    private Integer costPrice;
    @SerializedName("gst_percent")
    @Expose
    private Integer gstPercent;

    /**
     * No args constructor for use in serialization
     *
     */
    public ItemRequest() {
    }

    public ItemRequest(Integer productId, Integer qty, Integer costPrice, Integer gstPercent) {
        super();
        this.productId = productId;
        this.qty = qty;
        this.costPrice = costPrice;
        this.gstPercent = gstPercent;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(Integer gstPercent) {
        this.gstPercent = gstPercent;
    }

}