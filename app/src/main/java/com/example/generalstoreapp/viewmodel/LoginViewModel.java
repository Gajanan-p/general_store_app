package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.LoginRequestModel;
import com.example.generalstoreapp.repository.AuthRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    private final MutableLiveData<ApiResult<LoginModel>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(Context context) {
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

    public void login(String username, String password) {
        isLoading.setValue(true);
        LoginRequestModel requestModel = new LoginRequestModel(username, password);
        authRepository.login(requestModel, result -> {
            isLoading.setValue(false);
            loginResult.setValue(result);
        });
    }
}
