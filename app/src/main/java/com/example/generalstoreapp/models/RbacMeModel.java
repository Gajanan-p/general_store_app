package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RbacMeModel {
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("role_code")
    @Expose
    private String roleCode;
    @SerializedName("role_name")
    @Expose
    private String roleName;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions;

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}