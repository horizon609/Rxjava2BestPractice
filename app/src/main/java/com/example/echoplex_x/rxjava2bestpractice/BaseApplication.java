package com.example.echoplex_x.rxjava2bestpractice;

import android.app.Application;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApplication getInstance(){
        return mInstance;
    }
}
