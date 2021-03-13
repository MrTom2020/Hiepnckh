package com.example.nckh;

import android.app.Application;

public class WifiApp extends Application
{
    static WifiApp wifiApp;

    @Override
    public void onCreate()
    {
        super.onCreate();
        wifiApp = this;
    }
    public static synchronized WifiApp getInstance()
    {
        return wifiApp;
    }
}
