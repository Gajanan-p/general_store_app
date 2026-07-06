package com.example.generalstoreapp.repository;

import android.content.Context;
import com.example.generalstoreapp.models.DashboardSummaryModel;
import com.example.generalstoreapp.models.LowStockDashboardModel;
import com.example.generalstoreapp.models.RecentInvoiceModel;
import com.example.generalstoreapp.models.TopCustomerModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;
import java.util.ArrayList;

public class DashboardRepository {
    private final ApiService api;

    public DashboardRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getSummary(ApiCallback<DashboardSummaryModel> callback) {
        ApiExecutor.execute(api.getDashboardSummary(), callback);
    }

    public void getRecentInvoices(Integer limit, ApiCallback<ArrayList<RecentInvoiceModel>> callback) {
        ApiExecutor.execute(api.getRecentInvoices(limit), callback);
    }

    public void getLowStock(Integer limit, ApiCallback<ArrayList<LowStockDashboardModel>> callback) {
        ApiExecutor.execute(api.getDashboardLowStock(limit), callback);
    }

    public void getTopCustomers(Integer limit, ApiCallback<ArrayList<TopCustomerModel>> callback) {
        ApiExecutor.execute(api.getTopCustomers(limit), callback);
    }
}