package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddUsersByRoleRequest;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.Users;
import com.example.generalstoreapp.models.UsersListResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class UsersRepository {

    private final ApiService api;

    public UsersRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void listUsers(int limit, int offset, ApiCallback<UsersListResponse> callback) {
        ApiExecutor.execute(api.listUsers(limit, offset), callback);
    }

    public void createUser(AddUsersByRoleRequest request, ApiCallback<Users> callback) {
        ApiExecutor.execute(api.createStaffUser(request), callback);
    }

    public void updateUser(int userId, AddUsersByRoleRequest request, ApiCallback<Users> callback) {
        ApiExecutor.execute(api.updateStaffUser(userId, request), callback);
    }

    public void deleteUser(int userId, ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteStaffUser(userId), callback);
    }
}
