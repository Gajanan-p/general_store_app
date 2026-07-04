package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitsRequest {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_code")
    @Expose
    private String shortCode;
    @SerializedName("allow_decimal")
    @Expose
    private Boolean allowDecimal;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
    public Boolean getAllowDecimal() { return allowDecimal; }
    public void setAllowDecimal(Boolean allowDecimal) { this.allowDecimal = allowDecimal; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}