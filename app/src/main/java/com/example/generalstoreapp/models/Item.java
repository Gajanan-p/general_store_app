package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("line_subtotal")
    @Expose
    private Integer lineSubtotal;
    @SerializedName("line_tax")
    @Expose
    private Integer lineTax;
    @SerializedName("line_total")
    @Expose
    private Integer lineTotal;

    /**
     * No args constructor for use in serialization
     *
     */
    public Item() {
    }

    public Item(Integer id, Integer productId, Integer qty, Integer costPrice, Integer gstPercent, Integer lineSubtotal, Integer lineTax, Integer lineTotal) {
        super();
        this.id = id;
        this.productId = productId;
        this.qty = qty;
        this.costPrice = costPrice;
        this.gstPercent = gstPercent;
        this.lineSubtotal = lineSubtotal;
        this.lineTax = lineTax;
        this.lineTotal = lineTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLineSubtotal() {
        return lineSubtotal;
    }

    public void setLineSubtotal(Integer lineSubtotal) {
        this.lineSubtotal = lineSubtotal;
    }

    public Integer getLineTax() {
        return lineTax;
    }

    public void setLineTax(Integer lineTax) {
        this.lineTax = lineTax;
    }

    public Integer getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(Integer lineTotal) {
        this.lineTotal = lineTotal;
    }

}