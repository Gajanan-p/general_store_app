package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.GetPurchasesDataModel;
import com.example.generalstoreapp.repository.PurchaseRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class PurchaseViewModel extends ViewModel {

    private PurchaseRepository repository;
    private final MutableLiveData<List<GetPurchasesDataModel>> purchasesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void init(Context context) {
        repository = new PurchaseRepository(context);
    }

    public LiveData<List<GetPurchasesDataModel>> getPurchasesLiveData() {
        return purchasesLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchPurchases(int supplierId, String fromDate, String toDate, String status) {
        loadingLiveData.setValue(true);
        repository.getPurchases(supplierId, fromDate, toDate, status, 50, 0, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                purchasesLiveData.setValue(result.data);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
