package com.example.rutvik.ems;

import android.app.Application;

import extras.AppUtils;
import extras.Log;

/**
 * Created by rutvik on 22-04-2016 at 02:38 PM.
 */
public class App extends Application {

    private static final String TAG = AppUtils.APP_TAG + App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG,"APPLICATION CREATED!!!");

    }
}
