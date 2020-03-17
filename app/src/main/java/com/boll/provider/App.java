package com.boll.provider;

import android.app.Application;

import com.boll.xirilib.HookUtil;
import com.boll.xirilib.SDK;

/**
 * @author: caijianhui
 * @date: 2019/5/8 12:28
 * @description:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDK.init();
    }
}
