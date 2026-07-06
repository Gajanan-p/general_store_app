package com.example.generalstoreapp.repository;

import android.content.Context;
import com.example.generalstoreapp.models.AddPaymentRequest;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetPaymentDataModel;
import com.example.generalstoreapp.models.PaymentsListResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class PaymentRepository {
    private final ApiService api;

    public PaymentRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void createPayment(AddPaymentRequest request, ApiCallback<GetPaymentDataModel> callback) {
        ApiExecutor.execute(api.createPayment(request), callback);
    }

    public void getPayments(Integer limit, Integer offset, ApiCallback<PaymentsListResponse> callback) {
        ApiExecutor.execute(api.listPayments(limit, offset), callback);
    }

    public void getPaymentById(int paymentId, ApiCallback<GetPaymentDataModel> callback) {
        ApiExecutor.execute(api.getPayment(paymentId), callback);
    }

    public void cancelPayment(int paymentId, ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.cancelPayment(paymentId), callback);
    }
}