package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.UnitsRequest;
import com.example.generalstoreapp.models.UnitsResponse;
import com.example.generalstoreapp.models.DeleteUnitsResponse;
import com.example.generalstoreapp.repository.UnitsRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;

public class UnitsViewModel extends ViewModel {

    private UnitsRepository unitsRepository;
    private final MutableLiveData<ArrayList<GetUnitsDataModel>> unitsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    public void init(Context context) {
        if (unitsRepository == null) {
            unitsRepository = new UnitsRepository(context);
        }
    }

    public LiveData<ArrayList<GetUnitsDataModel>> getUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Boolean> getSuccessLiveData() {
        return successLiveData;
    }

    public void fetchUnits() {
        loadingLiveData.setValue(true);
        unitsRepository.getUnits(result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                unitsLiveData.setValue(result.data);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void addUnit(String name, String symbol) {
        loadingLiveData.setValue(true);
        UnitsRequest request = new UnitsRequest(name, symbol);
        unitsRepository.addUnits(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateUnit(int unitId, String name, String symbol) {
        loadingLiveData.setValue(true);
        UnitsRequest request = new UnitsRequest(name, symbol);
        unitsRepository.updateUnits(unitId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deleteUnit(int unitId) {
        loadingLiveData.setValue(true);
        unitsRepository.deleteUnits(unitId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
