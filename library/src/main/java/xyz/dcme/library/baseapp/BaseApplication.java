package xyz.dcme.library.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    public static Context getAppContext() {
        return mBaseApplication;
    }

    public static Resources getAppResources() {
        return mBaseApplication.getResources();
    }
}
