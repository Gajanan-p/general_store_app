package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.models.RoleResponse;
import com.example.generalstoreapp.models.RolesListResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class RoleRepository {

    private final ApiService api;

    public RoleRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void listRoles(ApiCallback<RolesListResponse> callback) {
        ApiExecutor.execute(api.listRoles(), callback);
    }

    public void getRole(int roleId, ApiCallback<GetRoleModel> callback) {
        ApiExecutor.execute(api.getRole(roleId), callback);
    }

    public void updateRolePermissions(int roleId, RoleRequest request, ApiCallback<RoleResponse> callback) {
        ApiExecutor.execute(api.updateRolePermissions(roleId, request), callback);
    }

    public void createRole(RoleRequest request, ApiCallback<RoleResponse> callback) {
        ApiExecutor.execute(api.callAddRoleDataFromServer(request), callback);
    }
}
