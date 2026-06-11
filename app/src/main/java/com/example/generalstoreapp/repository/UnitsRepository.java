package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.DeleteUnitsResponse;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.UnitsRequest;
import com.example.generalstoreapp.models.UnitsResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class UnitsRepository {
    private final ApiService api;

    public UnitsRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }
    public void getUnits(ApiCallback<ArrayList<GetUnitsDataModel>> callback) {
        ApiExecutor.execute(
                api.getUnitsDataFromServer(),
                callback
        );
    }

    public void getUnitById(int id, ApiCallback<GetUnitsDataModel> callback) {
        ApiExecutor.execute(api.getUnitsByIdDataFromServer(id), callback);
    }

    public void addUnits(UnitsRequest request,
                         ApiCallback<UnitsResponse> callback) {
        ApiExecutor.execute(api.saveUnitsDataFromServer(request), callback);

    }

    public void updateUnits(int unitId, UnitsRequest request,
                             ApiCallback<UnitsResponse> callback){
        ApiExecutor.execute(api.updateUnitsDataFromServer(unitId, request), callback);
    }

    public void deleteUnits(int unitId,
                             ApiCallback<DeleteUnitsResponse> callback) {
        ApiExecutor.execute(api.deleteUnitsDataFromServer(unitId), callback);
    }

}
