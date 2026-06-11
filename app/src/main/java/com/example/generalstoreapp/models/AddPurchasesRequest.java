package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPurchasesRequest {

    @SerializedName("supplier_id")
    @Expose
    private Integer supplierId;
    @SerializedName("supplier_invoice_no")
    @Expose
    private String supplierInvoiceNo;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("items")
    @Expose
    private List<ItemRequest> items;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddPurchasesRequest() {
    }

    public AddPurchasesRequest(Integer supplierId, String supplierInvoiceNo, String purchaseDate, Integer discountAmount,
                               String notes, List<ItemRequest> items) {
        super();
        this.supplierId = supplierId;
        this.supplierInvoiceNo = supplierInvoiceNo;
        this.purchaseDate = purchaseDate;
        this.discountAmount = discountAmount;
        this.notes = notes;
        this.items = items;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierInvoiceNo() {
        return supplierInvoiceNo;
    }

    public void setSupplierInvoiceNo(String supplierInvoiceNo) {
        this.supplierInvoiceNo = supplierInvoiceNo;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }

}