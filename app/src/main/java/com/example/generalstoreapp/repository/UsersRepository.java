package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddUsersByRoleRequest;
import com.example.generalstoreapp.models.AddUsersByRoleResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetUsersByPermissionsModel;
import com.example.generalstoreapp.models.GetUsersByRoleModel;
import com.example.generalstoreapp.models.GetUsersModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class UsersRepository {

    private final ApiService api;

    public UsersRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getUsers(ApiCallback<ArrayList<GetUsersModel>> callback) {
        ApiExecutor.execute(api.getUsersDataFromServer(), callback);
    }

    public void getUserRoles(int userId,
                             ApiCallback<GetUsersByRoleModel> callback) {
        ApiExecutor.execute(api.getUsersByRoleDataFromServer(userId), callback);
    }

    public void getUserPermissions(int userId,
                                   ApiCallback<GetUsersByPermissionsModel> callback) {
        ApiExecutor.execute(api.getUsersByPermissionDataFromServer(userId), callback);
    }

    public void addUsersByRole(int userId, AddUsersByRoleRequest request,
                               ApiCallback<AddUsersByRoleResponse> callback) {
        ApiExecutor.execute(api.addUsersByRoleDataFromServer(userId, request), callback);
    }
    public void updateUsersByRole(int userId, AddUsersByRoleRequest request,
                                   ApiCallback<AddUsersByRoleResponse> callback) {
        ApiExecutor.execute(api.updateUsersDataFromServer(userId, request), callback);

    }

    public void deleteUsers(int userId,
                             ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteUsersDataFromServer(userId), callback);
    }

}
