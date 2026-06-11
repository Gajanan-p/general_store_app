package com.example.generalstoreapp.utils;

import android.content.Context;
import com.example.generalstoreapp.models.UsersModel;
import java.util.List;

public class PermissionUtils {

    public static boolean hasPermission(Context context, String permission) {
        UsersModel user = SharedPreferencesUtils.getUserMeDataPreferences(context);
        if (user != null && user.getPermissions() != null) {
            return user.getPermissions().contains(permission);
        }
        return false;
    }

    public static boolean hasAnyPermission(Context context, String... permissions) {
        UsersModel user = SharedPreferencesUtils.getUserMeDataPreferences(context);
        if (user != null && user.getPermissions() != null) {
            List<String> userPermissions = user.getPermissions();
            for (String p : permissions) {
                if (userPermissions.contains(p)) return true;
            }
        }
        return false;
    }
}
