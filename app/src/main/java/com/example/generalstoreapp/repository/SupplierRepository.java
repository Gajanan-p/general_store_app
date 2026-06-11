package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddSuppliersRequest;
import com.example.generalstoreapp.models.AddSuppliersResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetSuppliersDataModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class SupplierRepository {

    private final ApiService api;

    public SupplierRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getSuppliers(String q, int isActive, int limit, int offset,
                             ApiCallback<ArrayList<GetSuppliersDataModel>> callback) {
        ApiExecutor.execute(
                api.getSupplierListDataFromServer(q, isActive, limit, offset),
                callback
        );
    }

    public void addSupplier(AddSuppliersRequest request,
                            ApiCallback<AddSuppliersResponse> callback) {
        ApiExecutor.execute(api.saveSuppliersDataFromServer(request), callback);
    }

    public void updateSupplier(int supplierId, AddSuppliersRequest request,
                               ApiCallback<AddSuppliersResponse> callback) {
        ApiExecutor.execute(api.updateSupplierDataFromServer(supplierId, request), callback);
    }

    public void deleteSupplier(int supplierId,
                               ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteSupplierDataFromServer(supplierId), callback);
    }
}
