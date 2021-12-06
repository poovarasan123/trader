package com.propositive.tradewaale.FastNetwork;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.androidnetworking.AndroidNetworking;

public class FastNetwork extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

    }
}
