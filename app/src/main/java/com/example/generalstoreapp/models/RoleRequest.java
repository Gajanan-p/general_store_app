package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("permission_ids")
    @Expose
    private java.util.List<Integer> permissionIds;

    /**
     * No args constructor for use in serialization
     *
     */
    public RoleRequest() {
    }

    public RoleRequest(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(java.util.List<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }

}