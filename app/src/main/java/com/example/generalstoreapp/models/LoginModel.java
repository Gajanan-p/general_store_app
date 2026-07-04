package com.example.generalstoreapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("user")
    @Expose
    private Users user;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("permissions")
    @Expose
    private List<String> permissions;

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}