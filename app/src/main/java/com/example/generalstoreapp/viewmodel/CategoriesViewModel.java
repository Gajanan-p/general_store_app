package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.repository.CategoryRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class CategoriesViewModel extends ViewModel {

    private CategoryRepository categoriesRepository;

    private final MutableLiveData<List<GetCategoriesModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (categoriesRepository == null) {
            categoriesRepository = new CategoryRepository(context);
        }
    }

    public LiveData<List<GetCategoriesModel>> getCategoriesLiveData() {
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
        fetchCategories(true);
    }

    public void fetchCategories(boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        categoriesRepository.getCategories(LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetCategoriesModel> items = result.data.getItems();
                List<GetCategoriesModel> currentList = categoriesLiveData.getValue();
                if (isRefresh || currentList == null) {
                    categoriesLiveData.setValue(items);
                } else {
                    List<GetCategoriesModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    categoriesLiveData.setValue(newList);
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

    public void createNewCategory(String name, String description) {
        CategoriesRequest request = new CategoriesRequest();
        request.setName(name);
        request.setDescription(description);
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
        CategoriesRequest request = new CategoriesRequest();
        request.setName(name);
        request.setDescription(description);
        request.setIsActive(true);
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