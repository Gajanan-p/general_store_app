package com.example.generalstoreapp.utils;



import com.example.generalstoreapp.models.RefreshRequest;
import com.example.generalstoreapp.models.RefreshResponse;
import com.example.generalstoreapp.services.ApiService;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


import android.content.Context;
import android.content.Intent;
import com.example.generalstoreapp.ui.activity.LoginActivity;

public class TokenAuthenticator implements Authenticator {

    private final TokenManager tokenManager;
    private final ApiService apiService;
    private final Context context;

    public TokenAuthenticator(TokenManager tokenManager, ApiService apiService, Context context) {
        this.tokenManager = tokenManager;
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        // Prevent infinite loop
        if (responseCount(response) >= 2) {
            handleLogout();
            return null; // logout
        }

        String refreshToken = tokenManager.getRefreshToken();
        if (refreshToken == null) {
            handleLogout();
            return null;
        }

        retrofit2.Response<RefreshResponse> refreshResponse =
                apiService.refreshToken(new RefreshRequest(refreshToken)).execute();

        if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {

            String newAccess = refreshResponse.body().getAccessToken();
            String newRefresh = refreshResponse.body().getRefreshToken();

            tokenManager.saveTokens(newAccess, newRefresh);

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccess)
                    .build();
        }

        handleLogout();
        return null;
    }

    private void handleLogout() {
        tokenManager.clear();
        SharedPreferencesUtils.clearLoginDataPreferences(context);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) {
            count++;
        }
        return count;
    }
}
