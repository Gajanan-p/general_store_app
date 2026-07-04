package com.example.generalstoreapp.services;

import android.content.Context;


import com.example.generalstoreapp.services.handlingservices.AuthInterceptor;
import com.example.generalstoreapp.utils.TokenAuthenticator;
import com.example.generalstoreapp.utils.TokenManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static ApiService apiService;

//    private static final String BASE_URL = "https://api-dev.genstore.cloud/";
    private static final String BASE_URL ="https://api-dev.genstore.cloud/";

    public static ApiService getApiService(Context context) {
        if (apiService == null) {
            TokenManager tokenManager = new TokenManager(context);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(tokenManager))
                    .authenticator(new TokenAuthenticator(tokenManager, getAuthApi(), context))
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .retryOnConnectionFailure(true)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    private static ApiService getAuthApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }
}
