package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.UnitsRequest;
import com.example.generalstoreapp.models.DeleteUnitsResponse;
import com.example.generalstoreapp.repository.UnitsRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class UnitsViewModel extends ViewModel {

    private UnitsRepository unitsRepository;
    private final MutableLiveData<List<GetUnitsDataModel>> unitsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (unitsRepository == null) {
            unitsRepository = new UnitsRepository(context);
        }
    }

    public LiveData<List<GetUnitsDataModel>> getUnitsLiveData() {
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
        fetchUnits(true);
    }

    public void fetchUnits(boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        unitsRepository.getUnits(LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetUnitsDataModel> items = result.data.getItems();
                List<GetUnitsDataModel> currentList = unitsLiveData.getValue();
                if (isRefresh || currentList == null) {
                    unitsLiveData.setValue(items);
                } else {
                    List<GetUnitsDataModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    unitsLiveData.setValue(newList);
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

    public void addUnit(String name, String shortCode, Boolean allowDecimal, String description) {
        loadingLiveData.setValue(true);
        UnitsRequest request = new UnitsRequest();
        request.setName(name);
        request.setShortCode(shortCode);
        request.setAllowDecimal(allowDecimal);
        request.setDescription(description);
        unitsRepository.addUnits(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateUnit(int unitId, String name, String shortCode, Boolean allowDecimal, String description) {
        loadingLiveData.setValue(true);
        UnitsRequest request = new UnitsRequest();
        request.setName(name);
        request.setShortCode(shortCode);
        request.setAllowDecimal(allowDecimal);
        request.setDescription(description);
        request.setIsActive(true);
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