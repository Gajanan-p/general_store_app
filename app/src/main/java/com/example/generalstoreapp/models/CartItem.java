package com.example.generalstoreapp.models;

public class CartItem {
    private GetProductDataModel product;
    private int quantity;
    private double discountPercent;
    private Double overridePrice;

    public CartItem(GetProductDataModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.discountPercent = 0;
    }

    public GetProductDataModel getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }
    public void setOverridePrice(double price) { this.overridePrice = price; }

    public double getPrice() {
        if (overridePrice != null) return overridePrice;
        return product.getSellPrice() != null ? product.getSellPrice().doubleValue() : 0;
    }

    public double getLineTotal() {
        return quantity * getPrice();
    }

    public double getDiscountAmount() {
        return getLineTotal() * (discountPercent / 100.0);
    }

    public double getTaxableAmount() {
        return getLineTotal() - getDiscountAmount();
    }

    public double getGstAmount() {
        return getTaxableAmount() * ((product.getGstPercent() != null ? product.getGstPercent() : 0) / 100.0);
    }

    public double getFinalAmount() {
        return getTaxableAmount() + getGstAmount();
    }
}
