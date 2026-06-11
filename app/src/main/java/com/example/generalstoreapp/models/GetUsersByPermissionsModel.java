package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUsersByPermissionsModel {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("role_ids")
    @Expose
    private List<Integer> roleIds;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetUsersByPermissionsModel() {
    }

    public GetUsersByPermissionsModel(User user, List<Integer> roleIds, List<String> permissions) {
        super();
        this.user = user;
        this.roleIds = roleIds;
        this.permissions = permissions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}