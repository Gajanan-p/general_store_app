package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitsRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;

    /**
     * No args constructor for use in serialization
     *
     */
    public UnitsRequest() {
    }

    public UnitsRequest(String name, String symbol) {
        super();
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}