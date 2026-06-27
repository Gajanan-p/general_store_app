package com.example.generalstoreapp.repository;

import android.content.Context;


import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.BillingResponse;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class BillingRepository {

    private final ApiService api;

    public BillingRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getBillingList(int customerId, String fromDate, String toDate, int limit, int offset,
                               ApiCallback<ArrayList<GetBillingDataModel>> callback) {
        ApiExecutor.execute(
                api.getBillingListDataFromServer(
                        customerId, fromDate, toDate, limit, offset),
                callback
        );
    }

    public void getBillingById(int billingId, ApiCallback<GetBillingDataModel> callback) {
        ApiExecutor.execute(api.getBillingByIdDataFromServer(billingId), callback);
    }

    public void cancelBilling(int billingId, ApiCallback<BillingResponse> callback) {
        ApiExecutor.execute(api.cancelBillingDataFromServer(billingId), callback);
    }

    public void createInvoice(BillingRequest request,
                              ApiCallback<BillingResponse> callback) {
        ApiExecutor.execute(api.createInvoiceDataFromServer(request), callback);
    }
}
