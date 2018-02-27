package com.ashish.errorhandling.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ashish.errorhandling.AppConstants;

/**
 * @author ashish
 * @since 27/02/18
 */
public final class PrefUtil {

    private static final String DEFAULT_STRING = "";
    private static final String PREFERENCE_NAME = AppConstants.PREFERENCES;

    // single instance
    private static PrefUtil sInstance;
    private final SharedPreferences sharedPref;

    private PrefUtil(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (null == sInstance) {
            sInstance = new PrefUtil(context);
        }
    }

    private static PrefUtil currentInstance() {
        if (null == sInstance) {
            throw new IllegalStateException("PreferenceUtil init must be called first");
        }
        return sInstance;
    }

    private SharedPreferences getSharedPref() {
        return sharedPref;
    }

    private SharedPreferences.Editor getSharedPrefEditor() {
        return sharedPref.edit();
    }

    public static String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    private static String getString(String key, String defValue) {
        return currentInstance().getSharedPref().getString(key, defValue);
    }

    public static void setString(String key, String value) {
        currentInstance().getSharedPrefEditor().putString(key, value).apply();
    }

}
