package com.example.generalstoreapp.utils;

import android.content.Context;

public class TokenProvider {

    private static TokenManager instance;

    public static TokenManager get(Context context) {
        if (instance == null) {
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }
}
