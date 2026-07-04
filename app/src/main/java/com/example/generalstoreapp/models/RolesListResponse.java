package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RolesListResponse {
    @SerializedName("roles")
    @Expose
    private List<GetRoleModel> roles;

    public List<GetRoleModel> getRoles() { return roles; }
    public void setRoles(List<GetRoleModel> roles) { this.roles = roles; }
}