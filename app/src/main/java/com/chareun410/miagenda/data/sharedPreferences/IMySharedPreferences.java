package com.chareun410.miagenda.data.sharedPreferences;

public interface IMySharedPreferences {
    String getString(String key, String defaultValue);
    void putString(String key, String value);
}
