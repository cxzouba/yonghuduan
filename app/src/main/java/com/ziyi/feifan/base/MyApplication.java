package com.ziyi.feifan.base;

import android.app.Application;

import com.vondear.rxtool.RxTool;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        RxTool.init(this);
        super.onCreate();
    }
}
