package com.tesleron.ezschool.MyUtils;

import android.app.Application;
import android.app.NotificationManager;

import com.tesleron.ezschool.R;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FireBaseOperations.getInstance();
        MySignal.init(this, getSystemService(NotificationManager.class));
    }
}
