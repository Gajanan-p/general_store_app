package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingItemsRequest {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("sell_price")
    @Expose
    private Integer sellPrice;
    @SerializedName("gst_percent")
    @Expose
    private Integer gstPercent;

    /**
     * No args constructor for use in serialization
     *
     */
    public BillingItemsRequest() {
    }

    public BillingItemsRequest(Integer productId, Integer qty, Integer sellPrice, Integer gstPercent) {
        super();
        this.productId = productId;
        this.qty = qty;
        this.sellPrice = sellPrice;
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

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(Integer gstPercent) {
        this.gstPercent = gstPercent;
    }

}