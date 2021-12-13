package com.propositive.tradewaale.FastNetwork;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class FastNetwork extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

    }
}
