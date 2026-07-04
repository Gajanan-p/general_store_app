package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.CategoriesListResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class CategoryRepository {

    private final ApiService api;

    public CategoryRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getCategories(Integer limit, Integer offset, ApiCallback<CategoriesListResponse> callback) {
        ApiExecutor.execute(api.getCategoryDataFromServer(limit, offset), callback);
    }

    public void getCategoryById(int id, ApiCallback<GetCategoriesModel> callback) {
        ApiExecutor.execute(api.getCategoryByIdDataFromServer(id), callback);
    }

    public void addCategory(CategoriesRequest request,
                            ApiCallback<GetCategoriesModel> callback) {
        ApiExecutor.execute(api.saveCategoryDataFromServer(request), callback);
    }

    public void updateCategory(int categoryId, CategoriesRequest request,
                               ApiCallback<GetCategoriesModel> callback) {
        ApiExecutor.execute(api.updateCategoryDataFromServer(categoryId, request), callback);
    }

    public void deleteCategory(int categoryId,
                               ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteCategoryDataFromServer(categoryId), callback);
    }
}