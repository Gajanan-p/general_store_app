package com.example.generalstoreapp.utils;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JwtUtils {

    public static long getExpiry(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            String payload = new String(
                    Base64.decode(parts[1], Base64.URL_SAFE),
                    StandardCharsets.UTF_8
            );

            JSONObject json = new JSONObject(payload);
            return json.getLong("exp") * 1000;
        } catch (Exception e) {
            return 0;
        }
    }
}
