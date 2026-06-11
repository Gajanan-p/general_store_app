package com.example.generalstoreapp.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUsersByRoleModel {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("roles")
    @Expose
    private List<Role> roles;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetUsersByRoleModel() {
    }

    public GetUsersByRoleModel(User user, List<Role> roles) {
        super();
        this.user = user;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}