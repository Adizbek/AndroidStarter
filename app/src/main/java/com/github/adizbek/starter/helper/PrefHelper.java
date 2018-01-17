package com.github.adizbek.starter.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adizbek on 1/15/18.
 */

public class PrefHelper {
    private static PrefHelper _instance;
    private final SharedPreferences preferences;

    private Context context;

    public static PrefHelper init(Context context) {
        if (_instance == null) {
            _instance = new PrefHelper(context);
        }

        return _instance;
    }

    public PrefHelper(Context context) {
        this.context = context;

        preferences = this.context.getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    public void saveString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void saveBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public String readString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public int readInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

}
