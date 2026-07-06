package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingItemsRequest {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
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

    public BillingItemsRequest() {}

    public BillingItemsRequest(Integer productId, Double quantity, Double rate, Double discountAmount, Double taxAmount) {
        this.productId = productId;
        this.quantity = quantity;
        this.rate = rate;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
    }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public Double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }
}