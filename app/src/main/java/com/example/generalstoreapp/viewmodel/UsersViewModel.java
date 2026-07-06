package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddUsersByRoleRequest;
import com.example.generalstoreapp.models.Users;
import com.example.generalstoreapp.repository.UsersRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    private UsersRepository repository;
    private final MutableLiveData<List<Users>> usersLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int limit = 20;
    private boolean isLastPage = false;

    public void init(Context context) {
        repository = new UsersRepository(context);
    }

    public LiveData<List<Users>> getUsersLiveData() {
        return usersLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getSuccessLiveData() {
        return successLiveData;
    }

    public void fetchUsers(boolean refresh) {
        if (refresh) {
            currentOffset = 0;
            isLastPage = false;
        }

        if (isLastPage) return;

        loadingLiveData.setValue(true);
        repository.listUsers(limit, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<Users> currentList = usersLiveData.getValue();
                if (refresh || currentList == null) {
                    currentList = new ArrayList<>();
                }
                
                List<Users> newItems = result.data.getItems();
                if (newItems != null) {
                    currentList.addAll(newItems);
                    if (newItems.size() < limit) {
                        isLastPage = true;
                    }
                    currentOffset += newItems.size();
                } else {
                    isLastPage = true;
                }
                
                usersLiveData.setValue(currentList);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void createUser(AddUsersByRoleRequest request) {
        loadingLiveData.setValue(true);
        repository.createUser(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
