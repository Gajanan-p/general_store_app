package com.example.generalstoreapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.UsersModel;
import com.google.gson.Gson;

public class SharedPreferencesUtils {

    //region declarations and variables
    private static String TAG="appDataPreferences";
    public static String appPreferences="appPreferences";
    public static String appLoginPreferences="appLoginPreferences";;
    public static String appLoginPreferencesKey="appLoginPreferencesKey";
    public static String appSettingPreferences="appSettingPreferences";
    public static String appUserMePreferences = "appUserMePreferences";

    public static android.content.SharedPreferences sharedLoginPreferences;
    public static android.content.SharedPreferences sharedSettingPreferences;
    public static android.content.SharedPreferences sharedAppPreferences;

    public static android.content.SharedPreferences getAppSharedPreferences(Context context){
        if(sharedAppPreferences==null){
            sharedAppPreferences=context.getSharedPreferences(SharedPreferencesUtils.appPreferences
                    , Context.MODE_PRIVATE);
        }
        return sharedAppPreferences;
    }

    public static android.content.SharedPreferences getLoginSharedPreferences(Context context){
        if(sharedLoginPreferences==null){
            sharedLoginPreferences=context.getSharedPreferences(SharedPreferencesUtils.appLoginPreferences, Context.MODE_PRIVATE);
        }
        return sharedLoginPreferences;
    }

    public static android.content.SharedPreferences getSettingSharedPreferences(Context context){
        if(sharedSettingPreferences==null){
            sharedSettingPreferences=context.getSharedPreferences(SharedPreferencesUtils.appSettingPreferences, Context.MODE_PRIVATE);
        }
        return sharedSettingPreferences;
    }

    public static boolean clearSettingDataPreferences(Context context){
        android.content.SharedPreferences.Editor editor=getSettingSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
        editor.commit();
        Log.i(TAG,"Clear setting Preferences");
        return true;
    }

    public static boolean clearLoginDataPreferences(Context context){
        android.content.SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
        editor.commit();
        Log.i(TAG,"Clear login Preferences");
        return true;
    }

    public static void setLoginDataPreferences(Context context,LoginModel userModel){
        Gson gson = new Gson();
        String data=gson.toJson(userModel);
        Log.i(TAG,"Set user model data");
        Log.i(TAG,data);
        android.content.SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.putString(appLoginPreferencesKey,data);
        editor.commit();
    }

    public static LoginModel getLoginDataPreferences(Context context){
        String data=getLoginSharedPreferences(context).getString(appLoginPreferencesKey,"");
        Gson gson = new Gson();
        LoginModel userModel= gson.fromJson(data, LoginModel.class);
        Log.i(TAG,"get user model data");
        Log.i(TAG,data);
        return userModel;
    }

    public static void setUserMeDataPreferences(Context context, UsersModel userModel){
        Gson gson = new Gson();
        String data=gson.toJson(userModel);
        Log.i(TAG,"Set user model data");
        Log.i(TAG,data);
        android.content.SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.putString(appUserMePreferences,data);
        editor.commit();
    }

    public static UsersModel getUserMeDataPreferences(Context context){
        String data=getLoginSharedPreferences(context).getString(appUserMePreferences,"");
        Gson gson = new Gson();
        UsersModel userModel= gson.fromJson(data,UsersModel.class);
        Log.i(TAG,"get user model data");
        Log.i(TAG,data);
        return userModel;
    }

}
