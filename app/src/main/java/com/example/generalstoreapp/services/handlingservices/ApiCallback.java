package com.example.generalstoreapp.services.handlingservices;



public interface ApiCallback<T> {
    void onResult(ApiResult<T> result);
}

