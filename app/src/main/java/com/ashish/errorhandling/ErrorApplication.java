package com.ashish.errorhandling;

import android.app.Application;

import com.ashish.errorhandling.utils.PrefUtil;

public class ErrorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PrefUtil.init(this);
    }
}
