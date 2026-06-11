package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddPurchasesRequest;
import com.example.generalstoreapp.models.AddPurchasesResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetPurchasesDataModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class PurchaseRepository {

    private final ApiService api;

    public PurchaseRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getPurchases(int supplierId, String fromDate, String toDate,
                             String status, int limit, int offset,
                             ApiCallback<ArrayList<GetPurchasesDataModel>> callback) {

        ApiExecutor.execute(
                api.getPurchasesListDataFromServer(
                        supplierId, fromDate, toDate, status, limit, offset),
                callback
        );
    }

    public void addPurchase(AddPurchasesRequest request,
                            ApiCallback<AddPurchasesResponse> callback) {
        ApiExecutor.execute(api.savePurchasesDataFromServer(request), callback);
    }

    public void updatePurchase(int purchaseId, AddPurchasesRequest request,
                               ApiCallback<AddPurchasesResponse> callback) {
        ApiExecutor.execute(api.updatePurchasesDataFromServer(purchaseId, request), callback);
    }

    public void deletePurchase(int purchaseId,
                               ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deletePurchasesDataFromServer(purchaseId), callback);
    }
}
