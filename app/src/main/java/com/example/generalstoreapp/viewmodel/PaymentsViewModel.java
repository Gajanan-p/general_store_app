package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddPaymentRequest;
import com.example.generalstoreapp.models.GetPaymentDataModel;
import com.example.generalstoreapp.repository.PaymentRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class PaymentsViewModel extends ViewModel {
    private PaymentRepository paymentRepository;
    private final MutableLiveData<List<GetPaymentDataModel>> paymentsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (paymentRepository == null) {
            paymentRepository = new PaymentRepository(context);
        }
    }

    public LiveData<List<GetPaymentDataModel>> getPaymentsLiveData() { return paymentsLiveData; }
    public LiveData<Boolean> getLoadingLiveData() { return loadingLiveData; }
    public LiveData<String> getErrorLiveData() { return errorLiveData; }
    public LiveData<Boolean> getSuccessLiveData() { return successLiveData; }

    public void fetchPayments(boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        paymentRepository.getPayments(LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetPaymentDataModel> items = result.data.getItems();
                List<GetPaymentDataModel> currentList = paymentsLiveData.getValue();
                if (isRefresh || currentList == null) {
                    paymentsLiveData.setValue(items);
                } else {
                    List<GetPaymentDataModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    paymentsLiveData.setValue(newList);
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

    public void createPayment(AddPaymentRequest request) {
        loadingLiveData.setValue(true);
        paymentRepository.createPayment(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deletePayment(int paymentId) {
        loadingLiveData.setValue(true);
        paymentRepository.cancelPayment(paymentId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}