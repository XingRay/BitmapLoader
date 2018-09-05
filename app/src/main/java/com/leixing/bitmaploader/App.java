package com.leixing.bitmaploader;

import android.app.Application;
import android.content.Context;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 19:34
 */
public class App extends Application {
    private static volatile App INSTANCE;
    private Context mContext;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mContext = getApplicationContext();
    }

    public Context getContext() {
        return mContext;
    }
}
