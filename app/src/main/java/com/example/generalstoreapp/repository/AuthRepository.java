package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.LoginRequestModel;
import com.example.generalstoreapp.models.RefreshRequest;
import com.example.generalstoreapp.models.RefreshResponse;
import com.example.generalstoreapp.models.RegistrationRequest;
import com.example.generalstoreapp.models.RegistrationResponse;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.models.RoleResponse;
import com.example.generalstoreapp.models.UsersModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class AuthRepository {

    private final ApiService api;

    public AuthRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void login(LoginRequestModel request, ApiCallback<LoginModel> callback) {
        ApiExecutor.execute(api.fetchLoginDataFromServer(request), callback);
    }

    public void register(RegistrationRequest request, ApiCallback<RegistrationResponse> callback) {
        ApiExecutor.execute(api.callRegistrationDataFromServer(request), callback);
    }

    public void getRole(ApiCallback<ArrayList<GetRoleModel>> callback){
        ApiExecutor.execute(api.callRoleDataFromServer(),callback);
    }

    public void createRole(RoleRequest request, ApiCallback<RoleResponse> callback){
        ApiExecutor.execute(api.callAddRoleDataFromServer(request),callback);
    }

    public void getUsers(ApiCallback<UsersModel> callback){
        ApiExecutor.execute(api.callUsersDataFromServer(),callback);
    }

    public void refreshToken(RefreshRequest request, ApiCallback<RefreshResponse> callback) {
        ApiExecutor.execute(api.refreshToken(request), callback);
    }
}
