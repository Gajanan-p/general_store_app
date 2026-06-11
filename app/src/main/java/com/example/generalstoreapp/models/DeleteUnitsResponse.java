package com.example.generalstoreapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteUnitsResponse {
    @SerializedName("status")
    @Expose
    private String status;

    public DeleteUnitsResponse() {
    }

    /**
 * No args constructor for use in serialization
 *
 */


    public DeleteUnitsResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
