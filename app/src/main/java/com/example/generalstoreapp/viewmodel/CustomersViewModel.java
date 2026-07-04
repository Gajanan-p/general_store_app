package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.repository.CustomerRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class CustomersViewModel extends ViewModel {
    private CustomerRepository customerRepository;
    private final MutableLiveData<List<GetCustomerDataModel>> customersLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (customerRepository == null) {
            customerRepository = new CustomerRepository(context);
        }
    }

    public LiveData<List<GetCustomerDataModel>> getCustomersLiveData() {
        return customersLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getSuccessLiveData() {
        return successLiveData;
    }

    public void fetchCustomers(String q, Boolean isActive, boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        customerRepository.getCustomers(q, isActive, LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetCustomerDataModel> items = result.data.getItems();
                List<GetCustomerDataModel> currentList = customersLiveData.getValue();
                if (isRefresh || currentList == null) {
                    customersLiveData.setValue(items);
                } else {
                    List<GetCustomerDataModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    customersLiveData.setValue(newList);
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

    public void createCustomer(AddCustomerRequest request) {
        loadingLiveData.setValue(true);
        customerRepository.addCustomer(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateCustomer(int customerId, AddCustomerRequest request) {
        loadingLiveData.setValue(true);
        customerRepository.updateCustomer(customerId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deleteCustomer(int customerId) {
        loadingLiveData.setValue(true);
        customerRepository.deleteCustomer(customerId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}
