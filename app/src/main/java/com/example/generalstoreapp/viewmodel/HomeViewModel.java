package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.BillingResponse;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.repository.BillingRepository;
import com.example.generalstoreapp.repository.CustomerRepository;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {


    private BillingRepository billingRepository;
    private CustomerRepository customerRepository;

    private final MutableLiveData<ArrayList<GetCustomerDataModel>> customerListLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetCustomerDataModel> customerLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> customerErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> customerLoadingLiveData = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<GetBillingDataModel>> billingListLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetBillingDataModel> billingDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cancelSuccessLiveData = new MutableLiveData<>();

    private final MutableLiveData<Double> todaySaleAmount = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> todayReceivedAmount = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> todayPendingAmount = new MutableLiveData<>(0.0);

    public void init(Context context) {
        billingRepository = new BillingRepository(context);
        customerRepository = new CustomerRepository(context);
    }

    public LiveData<ArrayList<GetBillingDataModel>> getBillingListLiveData() {
        return billingListLiveData;
    }

    public LiveData<GetBillingDataModel> getBillingDetailLiveData() {
        return billingDetailLiveData;
    }

    public LiveData<Double> getTodaySaleAmount() { return todaySaleAmount; }
    public LiveData<Double> getTodayReceivedAmount() { return todayReceivedAmount; }
    public LiveData<Double> getTodayPendingAmount() { return todayPendingAmount; }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Boolean> getCancelSuccessLiveData() {
        return cancelSuccessLiveData;
    }

    // 🔹 Get Billing List
    public void fetchBillingList(Integer customerId, String fromDate, String toDate, int limit, int offset) {

        loadingLiveData.setValue(true);

        billingRepository.getBillingList(customerId, fromDate, toDate,limit, offset,
                new ApiCallback<ArrayList<GetBillingDataModel>>() {
                    @Override
                    public void onResult(ApiResult<ArrayList<GetBillingDataModel>> result) {
                        if (result.status == ApiResult.Status.SUCCESS) {
                            billingListLiveData.setValue(result.data);
                            calculateSummary(result.data);
                            loadingLiveData.setValue(false);
                        } else {
                            errorLiveData.setValue(result.message);
                            loadingLiveData.setValue(false);
                        }
                    }
                });
    }

    public void fetchBillingById(int billingId) {
        loadingLiveData.setValue(true);
        billingRepository.getBillingById(billingId, new ApiCallback<GetBillingDataModel>() {
            @Override
            public void onResult(ApiResult<GetBillingDataModel> result) {
                loadingLiveData.setValue(false);
                if (result.status == ApiResult.Status.SUCCESS) {
                    billingDetailLiveData.setValue(result.data);
                } else {
                    errorLiveData.setValue(result.message);
                }
            }
        });
    }

    public void cancelBilling(int billingId) {
        loadingLiveData.setValue(true);
        billingRepository.cancelBilling(billingId, new ApiCallback<BillingResponse>() {
            @Override
            public void onResult(ApiResult<BillingResponse> result) {
                loadingLiveData.setValue(false);
                if (result.status == ApiResult.Status.SUCCESS) {
                    cancelSuccessLiveData.setValue(true);
                } else {
                    errorLiveData.setValue(result.message);
                }
            }
        });
    }

    private void calculateSummary(ArrayList<GetBillingDataModel> list) {
        double sale = 0;
        double received = 0;
        double pending = 0;

        if (list != null) {
            for (GetBillingDataModel model : list) {
                sale += (model.getTotalAmount() != null ? model.getTotalAmount() : 0);
                received += (model.getPaidAmount() != null ? model.getPaidAmount() : 0);
                pending += (model.getDueAmount() != null ? model.getDueAmount() : 0);
            }
        }

        todaySaleAmount.setValue(sale);
        todayReceivedAmount.setValue(received);
        todayPendingAmount.setValue(pending);
    }

    public void createInvoice(BillingRequest request) {
        loadingLiveData.setValue(true);
        billingRepository.createInvoice(request, new ApiCallback<BillingResponse>() {
            @Override
            public void onResult(ApiResult<BillingResponse> result) {
                if (result.status == ApiResult.Status.SUCCESS) {
                    loadingLiveData.setValue(false);
                } else {
                    errorLiveData.setValue(result.message);
                    loadingLiveData.setValue(false);
                }
            }
        });
    }

    public void getCustomerDataBy(int customerId){
        customerLoadingLiveData.setValue(true);
        customerRepository.getCustomersByID(customerId, new ApiCallback<GetCustomerDataModel>() {
            @Override
            public void onResult(ApiResult<GetCustomerDataModel> result) {
                if (result.status == ApiResult.Status.SUCCESS) {
                    customerLiveData.setValue(result.data);
                    customerLoadingLiveData.setValue(false);
                } else {
                    customerErrorLiveData.setValue(result.message);
                    customerLoadingLiveData.setValue(false);
                }
            }
        });
    }
}
