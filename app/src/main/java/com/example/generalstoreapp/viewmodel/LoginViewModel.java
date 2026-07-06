package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.LoginRequestModel;
import com.example.generalstoreapp.repository.AuthRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;
import com.example.generalstoreapp.utils.SharedPreferencesUtils;

public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    private Context context;
    private final MutableLiveData<ApiResult<LoginModel>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(Context context) {
        this.context = context;
        if (authRepository == null) {
            authRepository = new AuthRepository(context);
        }
    }

    public LiveData<ApiResult<LoginModel>> getLoginResult() {
        return loginResult;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void login(String email, String password) {
        isLoading.setValue(true);
        LoginRequestModel requestModel = new LoginRequestModel(email, password);
        authRepository.login(requestModel, result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                // Save login data first so AuthInterceptor can use the token
                SharedPreferencesUtils.setLoginDataPreferences(context, result.data);
                
                authRepository.getUsers(userResult -> {
                    isLoading.setValue(false);
                    if (userResult.status == ApiResult.Status.SUCCESS) {
                        SharedPreferencesUtils.setUserMeDataPreferences(context, userResult.data);
                        loginResult.setValue(result);
                    } else {
                        loginResult.setValue(result);
                    }
                });
            } else {
                isLoading.setValue(false);
                loginResult.setValue(result);
            }
        });
    }
}
