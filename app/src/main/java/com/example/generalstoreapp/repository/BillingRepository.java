package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.SalesInvoiceListResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class BillingRepository {

    private final ApiService api;

    public BillingRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getBillingList(Integer customerId, String fromDate, String toDate, Boolean includeCancelled, int limit, int offset,
                               ApiCallback<SalesInvoiceListResponse> callback) {
        ApiExecutor.execute(
                api.getBillingListDataFromServer(customerId, fromDate, toDate, includeCancelled, limit, offset),
                callback
        );
    }

    public void getBillingById(int billingId, ApiCallback<GetBillingDataModel> callback) {
        ApiExecutor.execute(api.getBillingByIdDataFromServer(billingId), callback);
    }

    public void cancelBilling(int billingId, ApiCallback<GetBillingDataModel> callback) {
        ApiExecutor.execute(api.cancelBillingDataFromServer(billingId), callback);
    }

    public void createInvoice(BillingRequest request,
                              ApiCallback<GetBillingDataModel> callback) {
        ApiExecutor.execute(api.createInvoiceDataFromServer(request), callback);
    }
}