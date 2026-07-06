package com.example.generalstoreapp.utils;

import android.content.Context;
import com.example.generalstoreapp.models.UsersModel;
import java.util.List;

public class PermissionUtils {

    public static boolean hasPermission(Context context, String permission) {
        UsersModel user = SharedPreferencesUtils.getUserMeDataPreferences(context);
        if (user == null) return false;
        
        // Superuser bypass via roles list
        if (user.getRoles() != null) {
            for (com.example.generalstoreapp.models.UserRole role : user.getRoles()) {
                if (role.getName() != null && (role.getName().toLowerCase().contains("owner") || role.getName().toLowerCase().contains("admin"))) {
                    return true;
                }
            }
        }

        // Superuser bypass via single user object
        if (user.getUser() != null && user.getUser().getRoleName() != null) {
            String roleName = user.getUser().getRoleName();
            if (roleName.toLowerCase().contains("owner") || roleName.toLowerCase().contains("admin")) {
                return true;
            }
        }

        if (user.getPermissions() != null) {
            for (String p : user.getPermissions()) {
                if (p != null && (p.equalsIgnoreCase(permission) || p.toUpperCase().contains(permission.toUpperCase()) || p.replace(":","_").equalsIgnoreCase(permission))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAnyPermission(Context context, String... permissions) {
        UsersModel user = SharedPreferencesUtils.getUserMeDataPreferences(context);
        if (user == null) return false;
        
        // Superuser bypass
        if (user.getRoles() != null) {
            for (com.example.generalstoreapp.models.UserRole role : user.getRoles()) {
                if (role.getName() != null && (role.getName().toLowerCase().contains("owner") || role.getName().toLowerCase().contains("admin"))) {
                    return true;
                }
            }
        }

        if (user.getUser() != null && user.getUser().getRoleName() != null) {
            String roleName = user.getUser().getRoleName();
            if (roleName.toLowerCase().contains("owner") || roleName.toLowerCase().contains("admin")) {
                return true;
            }
        }

        if (user.getPermissions() != null) {
            List<String> userPermissions = user.getPermissions();
            for (String p : permissions) {
                for (String up : userPermissions) {
                    if (up != null && (up.equalsIgnoreCase(p) || up.toUpperCase().contains(p.toUpperCase()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
