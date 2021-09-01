package com.propositive.tradewaale.FCMnotification;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIDService";

    @Override
    public void onNewToken(@NonNull String s) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String token = task.getResult();
                Log.d(TAG, "onComplete: token: --->" + token);
                storeToken(String.valueOf(token));
            }
        });
    }



    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPreference.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
