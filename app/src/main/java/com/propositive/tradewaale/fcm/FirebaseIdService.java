package com.propositive.tradewaale.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseIdService extends FirebaseMessagingService {

    private static final String TAG = "Firebase devices token";

    @Override
    public void onNewToken(@NonNull String s) {


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                String token = task.getResult();
                Log.d(TAG, "onComplete: service_token: --->" + task);

            }
        });
    }
}
