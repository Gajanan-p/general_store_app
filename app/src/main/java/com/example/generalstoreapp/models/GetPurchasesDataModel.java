package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPurchasesDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("purchase_no")
    @Expose
    private String purchaseNo;
    @SerializedName("supplier_id")
    @Expose
    private Integer supplierId;
    @SerializedName("supplier_invoice_no")
    @Expose
    private String supplierInvoiceNo;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("tax_amount")
    @Expose
    private Integer taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("items")
    @Expose
    private List<Items> items;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetPurchasesDataModel() {
    }

    public GetPurchasesDataModel(Integer id, String purchaseNo, Integer supplierId, String supplierInvoiceNo,
                                 String purchaseDate, String status, Integer subtotal, Integer discountAmount,
                                 Integer taxAmount, Integer totalAmount, String notes, String createdDate,
                                 String updatedDate, List<Items> items) {
        super();
        this.id = id;
        this.purchaseNo = purchaseNo;
        this.supplierId = supplierId;
        this.supplierInvoiceNo = supplierInvoiceNo;
        this.purchaseDate = purchaseDate;
        this.status = status;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

}