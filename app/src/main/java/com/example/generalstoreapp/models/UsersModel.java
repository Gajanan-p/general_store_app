package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersModel {
    @SerializedName("user")
    @Expose
    private Users user;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions;
    @SerializedName("roles")
    @Expose
    private List<UserRole> roles;

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    public List<UserRole> getRoles() { return roles; }
    public void setRoles(List<UserRole> roles) { this.roles = roles; }
}