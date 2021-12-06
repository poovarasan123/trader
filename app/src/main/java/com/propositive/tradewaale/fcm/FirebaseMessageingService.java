package com.propositive.tradewaale.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.notification.NotifyListActivity;

import java.util.Locale;
import java.util.Objects;

public class FirebaseMessageingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "101";
    private static final String NOTIFICATION_ID = "102";

    private static final String TAG = "fcm values";
    private static final String NOTIFICATION_CHANNEL_NAME = "Advisory";

    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null ) {

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String click_action = remoteMessage.getNotification().getClickAction();

            Log.d(TAG, "onMessageReceived: title " + title);
            Log.d(TAG, "onMessageReceived: body " + body);
            Log.d(TAG, "onMessageReceived: click_action " + click_action);

            Intent i = new Intent(click_action);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationManager notificationManager = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );
            if (!Objects.equals ( null, remoteMessage.getNotification () )) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel ( NOTIFICATION_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH );
                    notificationManager.createNotificationChannel ( notificationChannel );
                }
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder ( this, NOTIFICATION_ID );
                notificationBuilder.setAutoCancel ( true )
                        .setStyle ( new NotificationCompat.BigTextStyle ().bigText ( remoteMessage.getNotification ().getBody () ) )
                        .setDefaults ( Notification.DEFAULT_ALL )
                        .setWhen ( System.currentTimeMillis () )
                        .setSmallIcon ( R.drawable.traderwaale_blue )
                        .setTicker ( remoteMessage.getNotification ().getTitle () )
                        .setPriority ( Notification.PRIORITY_MAX )
                        .setContentIntent(pendingIntent)
                        .setContentTitle ( remoteMessage.getNotification ().getTitle () )
                        .setContentText ( remoteMessage.getNotification ().getBody () );
                notificationManager.notify ( 1, notificationBuilder.build () );
            }


//            Notification notification = new NotificationCompat.Builder(this)
//                    .setContentTitle(title)
//                    .setContentText(body)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setSmallIcon(R.drawable.traderwaale_blue)
//                    .setContentIntent(pendingIntent)
//                    .build();
//
//            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//            manager.notify(123, notification);


//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setContentTitle(title)
//                    .setContentText(body)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.traderwaale_blue)
//                    .setAutoCancel(true);
//
//            notificationManagerCompat = NotificationManagerCompat.from(this);
//            notificationManagerCompat.notify(Integer.parseInt(NOTIFICATION_ID), builder.build());

        }

    }

    @Override
    public void onNewToken(@NonNull String s) {

    }
}
