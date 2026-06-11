package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteResponse {
    @SerializedName("status")
    @Expose
    private String status;

/**
 * No args constructor for use in serialization
 *
 */
    public DeleteResponse() {
    }

    public DeleteResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
