package com.example.generalstoreapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.DashboardSummaryModel;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.models.RecentInvoiceModel;
import com.example.generalstoreapp.models.SalesInvoiceListResponse;
import com.example.generalstoreapp.repository.BillingRepository;
import com.example.generalstoreapp.repository.CustomerRepository;
import com.example.generalstoreapp.repository.DashboardRepository;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {


    private BillingRepository billingRepository;
    private CustomerRepository customerRepository;
    private DashboardRepository dashboardRepository;

    private final MutableLiveData<List<GetCustomerDataModel>> customerListLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetCustomerDataModel> customerLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> customerErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> customerLoadingLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<GetBillingDataModel>> billingListLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetBillingDataModel> billingDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cancelSuccessLiveData = new MutableLiveData<>();

    private final MutableLiveData<Double> todaySaleAmount = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> todayReceivedAmount = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> todayPendingAmount = new MutableLiveData<>(0.0);

    private final MutableLiveData<DashboardSummaryModel> dashboardSummary = new MutableLiveData<>();
    private final MutableLiveData<List<RecentInvoiceModel>> recentInvoices = new MutableLiveData<>();

    public void init(Context context) {
        billingRepository = new BillingRepository(context);
        customerRepository = new CustomerRepository(context);
        dashboardRepository = new DashboardRepository(context);
    }

    public LiveData<DashboardSummaryModel> getDashboardSummary() { return dashboardSummary; }
    public LiveData<List<RecentInvoiceModel>> getRecentInvoices() { return recentInvoices; }

    public void fetchDashboardData() {
        loadingLiveData.setValue(true);
        dashboardRepository.getSummary(result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                dashboardSummary.setValue(result.data);
            }
        });

        dashboardRepository.getRecentInvoices(10, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                recentInvoices.setValue(result.data);
                
                // If the main billing list hasn't been populated or is empty,
                // map these recent invoices to the main list format for display
                if (billingListLiveData.getValue() == null || billingListLiveData.getValue().isEmpty()) {
                    List<GetBillingDataModel> mappedList = new ArrayList<>();
                    for (RecentInvoiceModel ri : result.data) {
                        GetBillingDataModel b = new GetBillingDataModel();
                        b.setId(ri.getId());
                        b.setInvoiceNo(ri.getInvoiceNo());
                        b.setInvoiceDate(ri.getInvoiceDate());
                        b.setTotalAmount(parseDouble(ri.getTotalAmount()));
                        b.setPaidAmount(parseDouble(ri.getPaidAmount()));
                        b.setDueAmount(parseDouble(ri.getDueAmount()));
                        b.setIsCancelled(ri.getIsCancelled());
                        
                        GetCustomerDataModel c = new GetCustomerDataModel();
                        c.setId(ri.getCustomerId());
                        c.setName(ri.getCustomerName());
                        b.setCustomer(c);
                        
                        mappedList.add(b);
                    }
                    billingListLiveData.setValue(mappedList);
                }
            }
        });
    }

    private Double parseDouble(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public LiveData<List<GetBillingDataModel>> getBillingListLiveData() {
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

        billingRepository.getBillingList(customerId, fromDate, toDate, false, limit, offset,
                new ApiCallback<SalesInvoiceListResponse>() {
                    @Override
                    public void onResult(ApiResult<SalesInvoiceListResponse> result) {
                        if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                            billingListLiveData.setValue(result.data.getItems());
                            calculateSummary(result.data.getItems());
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
        billingRepository.cancelBilling(billingId, new ApiCallback<GetBillingDataModel>() {
            @Override
            public void onResult(ApiResult<GetBillingDataModel> result) {
                loadingLiveData.setValue(false);
                if (result.status == ApiResult.Status.SUCCESS) {
                    cancelSuccessLiveData.setValue(true);
                } else {
                    errorLiveData.setValue(result.message);
                }
            }
        });
    }

    private void calculateSummary(List<GetBillingDataModel> list) {
        double sale = 0;
        double received = 0;
        double pending = 0;

        if (list != null) {
            for (GetBillingDataModel model : list) {
                if (model.getIsCancelled() != null && model.getIsCancelled()) continue;
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
        billingRepository.createInvoice(request, new ApiCallback<GetBillingDataModel>() {
            @Override
            public void onResult(ApiResult<GetBillingDataModel> result) {
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