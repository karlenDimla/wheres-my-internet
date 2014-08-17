package com.app.WebgeekFinal.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kdimla on 8/17/14.
 */
public class SharedPrefUtility {
    public static final String FILENAME = ".webgeekPref";

    private static SharedPrefUtility sInstance;
    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sPreferencesEditor;

    private SharedPrefUtility(Context context) {
        sSharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sPreferencesEditor = sSharedPreferences.edit();
    }

    public static SharedPrefUtility getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefUtility(context);
        }

        return sInstance;
    }

    public String getString(String key, String defaultValue) {
        return sSharedPreferences.getString(key, defaultValue);
    }

    public SharedPrefUtility putString(String key, String value) {
        sPreferencesEditor.putString(key, value);
        sPreferencesEditor.commit();
        return sInstance;
    }

    public int getInt(String key, int defaultValue) {
        return sSharedPreferences.getInt(key, defaultValue);
    }

    public SharedPrefUtility putInt(String key, int value) {
        sPreferencesEditor.putInt(key, value);
        sPreferencesEditor.commit();
        return sInstance;
    }

    public Long getLong(String key, Long defaultValue) {
        return sSharedPreferences.getLong(key, defaultValue);
    }

    public SharedPrefUtility putLong(String key, Long value) {
        sPreferencesEditor.putLong(key, value);
        sPreferencesEditor.commit();
        return sInstance;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sSharedPreferences.getBoolean(key, defaultValue);
    }

    public SharedPrefUtility putBoolean(String key, Boolean value) {
        sPreferencesEditor.putBoolean(key, value);
        sPreferencesEditor.commit();
        return sInstance;
    }
}
