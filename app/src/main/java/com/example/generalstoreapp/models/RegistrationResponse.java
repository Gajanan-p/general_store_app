package com.example.generalstoreapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("assigned_role")
    @Expose
    private String assignedRole;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegistrationResponse() {
    }

    public RegistrationResponse(Integer id, String email, String assignedRole) {
        super();
        this.id = id;
        this.email = email;
        this.assignedRole = assignedRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssignedRole() {
        return assignedRole;
    }

    public void setAssignedRole(String assignedRole) {
        this.assignedRole = assignedRole;
    }

}