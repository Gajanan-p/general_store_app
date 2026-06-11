package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersModel {

    @SerializedName("user")
    @Expose
    private Users user;
    @SerializedName("roles")
    @Expose
    private List<UserRole> roles;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions;

    /**
     * No args constructor for use in serialization
     *
     */
    public UsersModel() {
    }

    public UsersModel(Users user, List<UserRole> roles, List<String> permissions) {
        super();
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}