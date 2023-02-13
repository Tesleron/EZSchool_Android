package com.tesleron.ezschool.MyUtils;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FireBaseOperations.getInstance();
        MySignal.init(this);
    }
}
