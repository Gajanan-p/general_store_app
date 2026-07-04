package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PermissionsResponse {
    @SerializedName("items")
    @Expose
    private List<PermissionsModel> items;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<PermissionsModel> getItems() { return items; }
    public void setItems(List<PermissionsModel> items) { this.items = items; }
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
}