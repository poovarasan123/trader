package com.propositive.tradewaale.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.notification.NotifyListActivity;

import java.util.Objects;

public class FirebaseMessageingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "101";
    private static final int NOTIFICATION_ID = 102;

    private static final String TAG = "fcm values";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String click_action = remoteMessage.getNotification().getClickAction();

        Log.d(TAG, "onMessageReceived: title " + title);
        Log.d(TAG, "onMessageReceived: body " + body);
        Log.d(TAG, "onMessageReceived: click_action " + click_action);

        Intent i = new Intent(click_action);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.traderwaale_blue)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    @Override
    public void onNewToken(@NonNull String s) {

    }
}
