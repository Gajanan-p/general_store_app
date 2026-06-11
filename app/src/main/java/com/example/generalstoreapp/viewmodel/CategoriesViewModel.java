package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.CategoriesResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.repository.CategoryRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;

public class CategoriesViewModel extends ViewModel {

    private CategoryRepository categoriesRepository;

    private final MutableLiveData<ArrayList<GetCategoriesModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    public void init(Context context) {
        if (categoriesRepository == null) {
            categoriesRepository = new CategoryRepository(context);
        }
    }

    public LiveData<ArrayList<GetCategoriesModel>> getCategoriesLiveData() {
        return categoriesLiveData;
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

    public void fetchCategories() {
        loadingLiveData.setValue(true);
        categoriesRepository.getCategories(result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                categoriesLiveData.setValue(result.data);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void createNewCategory(String name, String description) {
        CategoriesRequest request = new CategoriesRequest(name, description);
        loadingLiveData.setValue(true);
        categoriesRepository.addCategory(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateCategory(int categoryId, String name, String description) {
        CategoriesRequest request = new CategoriesRequest(name, description);
        loadingLiveData.setValue(true);
        categoriesRepository.updateCategory(categoryId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deleteCategory(int categoryId) {
        loadingLiveData.setValue(true);
        categoriesRepository.deleteCategory(categoryId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
