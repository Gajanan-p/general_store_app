package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.models.RolesListResponse;
import com.example.generalstoreapp.repository.RoleRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.List;

public class RoleViewModel extends ViewModel {

    private RoleRepository repository;
    private final MutableLiveData<List<GetRoleModel>> rolesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    public void init(Context context) {
        repository = new RoleRepository(context);
    }

    public LiveData<List<GetRoleModel>> getRolesLiveData() {
        return rolesLiveData;
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

    public void fetchRoles() {
        loadingLiveData.setValue(true);
        repository.listRoles(result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                rolesLiveData.setValue(result.data.getRoles());
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateRolePermissions(int roleId, List<Integer> permissionIds) {
        loadingLiveData.setValue(true);
        RoleRequest request = new RoleRequest();
        request.setPermissionIds(permissionIds);
        repository.updateRolePermissions(roleId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void createRole(String name, String description) {
        loadingLiveData.setValue(true);
        RoleRequest request = new RoleRequest(name, description);
        // Using AuthRepository for now as it has createRole, or I should add it to RoleRepository
        // Actually I'll add it to RoleRepository for consistency
        repository.createRole(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
