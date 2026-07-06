package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddSuppliersRequest;
import com.example.generalstoreapp.models.GetSuppliersDataModel;
import com.example.generalstoreapp.repository.SupplierRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class SupplierViewModel extends ViewModel {
    private SupplierRepository supplierRepository;
    private final MutableLiveData<List<GetSuppliersDataModel>> suppliersLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (supplierRepository == null) {
            supplierRepository = new SupplierRepository(context);
        }
    }

    public LiveData<List<GetSuppliersDataModel>> getSuppliersLiveData() { return suppliersLiveData; }
    public LiveData<Boolean> getLoadingLiveData() { return loadingLiveData; }
    public LiveData<String> getErrorLiveData() { return errorLiveData; }
    public LiveData<Boolean> getSuccessLiveData() { return successLiveData; }

    public void fetchSuppliers(String q, Boolean isActive, boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        supplierRepository.getSuppliers(q, isActive, LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetSuppliersDataModel> items = result.data.getItems();
                List<GetSuppliersDataModel> currentList = suppliersLiveData.getValue();
                if (isRefresh || currentList == null) {
                    suppliersLiveData.setValue(items);
                } else {
                    List<GetSuppliersDataModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    suppliersLiveData.setValue(newList);
                }
                
                if (items.size() < LIMIT) {
                    isLastPage = true;
                } else {
                    currentOffset += LIMIT;
                }
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void createSupplier(AddSuppliersRequest request) {
        loadingLiveData.setValue(true);
        supplierRepository.addSupplier(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateSupplier(int supplierId, AddSuppliersRequest request) {
        loadingLiveData.setValue(true);
        supplierRepository.updateSupplier(supplierId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deleteSupplier(int supplierId) {
        loadingLiveData.setValue(true);
        supplierRepository.deleteSupplier(supplierId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}