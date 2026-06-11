package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.AddCustomerResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class CustomerRepository {

    private final ApiService api;

    public CustomerRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getCustomers(String q, int isActive, int limit, int offset,
                             ApiCallback<ArrayList<GetCustomerDataModel>> callback) {
        ApiExecutor.execute(
                api.getCustomersListDataFromServer(q, isActive, limit, offset),
                callback
        );
    }
    public void getCustomersByID(int id,
                             ApiCallback<GetCustomerDataModel> callback) {
        ApiExecutor.execute(
                api.getCustomersByIdDataFromServer(id),
                callback
        );
    }

    public void addCustomer(AddCustomerRequest request,
                            ApiCallback<AddCustomerResponse> callback) {
        ApiExecutor.execute(api.saveCustomersDataFromServer(request), callback);
    }

    public void updateCustomer(int customerId, AddCustomerRequest request,
                               ApiCallback<AddCustomerResponse> callback) {
        ApiExecutor.execute(api.updateCustomersDataFromServer(customerId, request), callback);
    }

    public void deleteCustomer(int customerId,
                               ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteCustomersDataFromServer(customerId), callback);
    }
}
