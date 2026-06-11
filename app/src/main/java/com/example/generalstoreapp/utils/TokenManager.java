package com.example.generalstoreapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF = "auth_prefs";
    private static final String KEY_ACCESS = "access";
    private static final String KEY_REFRESH = "refresh";
    private static final String KEY_EXP = "exp";

    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void saveTokens(String access, String refresh) {
        long expMillis = JwtUtils.getExpiry(access);

        prefs.edit()
                .putString(KEY_ACCESS, access)
                .putString(KEY_REFRESH, refresh)
                .putLong(KEY_EXP, expMillis)
                .apply();
    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS, null);
    }

    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH, null);
    }

    public boolean isTokenExpired() {
        long exp = prefs.getLong(KEY_EXP, 0);
        return System.currentTimeMillis() >= exp;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
