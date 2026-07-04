package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service")
    @Expose
    private String service;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
}