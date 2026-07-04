package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.repository.BillingRepository;
import com.example.generalstoreapp.repository.CustomerRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class SalesViewModel extends ViewModel {

    private BillingRepository billingRepository;
    private CustomerRepository customerRepository;
    private final MutableLiveData<List<GetBillingDataModel>> billingListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GetCustomerDataModel>> customersLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(false);

    public void init(Context context) {
        if (billingRepository == null) {
            billingRepository = new BillingRepository(context);
        }
        if (customerRepository == null) {
            customerRepository = new CustomerRepository(context);
        }
    }

    public LiveData<List<GetBillingDataModel>> getBillingListLiveData() {
        return billingListLiveData;
    }

    public LiveData<List<GetCustomerDataModel>> getCustomersLiveData() {
        return customersLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void fetchBillingList(Integer customerId, String fromDate, String toDate) {
        loadingLiveData.setValue(true);
        billingRepository.getBillingList(customerId, fromDate, toDate, 100, 0, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                billingListLiveData.setValue(result.data);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void fetchCustomers() {
        customerRepository.getCustomers("", true, 100, 0, result -> {
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                customersLiveData.setValue(result.data.getItems());
            }
        });
    }
}
