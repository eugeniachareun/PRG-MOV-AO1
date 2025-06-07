package com.chareun410.miagenda.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences implements IMySharedPreferences {

    private static MySharedPreferences instance;
    private SharedPreferences sharedPreferences;

    private MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("agenda", Context.MODE_PRIVATE);
    }

    public static MySharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new MySharedPreferences(context);
        }
        return instance;
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }
}
