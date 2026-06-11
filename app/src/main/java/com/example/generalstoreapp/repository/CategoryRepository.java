package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.CategoriesResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class CategoryRepository {

    private final ApiService api;

    public CategoryRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getCategories(ApiCallback<ArrayList<GetCategoriesModel>> callback) {
        ApiExecutor.execute(api.getCategoryDataFromServer(), callback);
    }

    public void getCategoryById(int id, ApiCallback<GetCategoriesModel> callback) {
        ApiExecutor.execute(api.getCategoryByIdDataFromServer(id), callback);
    }

    public void addCategory(CategoriesRequest request,
                            ApiCallback<CategoriesResponse> callback) {
        ApiExecutor.execute(api.saveCategoryDataFromServer(request), callback);
    }

    public void updateCategory(int categoryId, CategoriesRequest request,
                               ApiCallback<CategoriesResponse> callback) {
        ApiExecutor.execute(api.updateCategoryDataFromServer(categoryId, request), callback);
    }

    public void deleteCategory(int categoryId,
                               ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteCategoryDataFromServer(categoryId), callback);
    }
}
