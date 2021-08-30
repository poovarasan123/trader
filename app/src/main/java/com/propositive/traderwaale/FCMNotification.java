package com.propositive.traderwaale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.propositive.traderwaale.login.LoginActivity;

import java.net.URI;

public class FCMNotification extends FirebaseMessagingService {

    private static final String TAG = "fcm";

    private static  final String CHANNEL_ID = "101";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived: title " + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "onMessageReceived: message " + remoteMessage.getNotification().getBody());

        Log.d(TAG, "onMessageReceived: called");

        Log.d(TAG, "onMessageReceived: message received from " + remoteMessage.getFrom());


        if (remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Uri alarmSound = RingtoneManager.getDefaultUri(R.raw.confident);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d(TAG, "onMessageReceived: if block runed!...");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.traderwaale_white);
                builder.setColor(Color.BLUE);
                builder.setContentTitle(title);
                builder.setContentText(body);
                Uri uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.confident);
                builder.setSound(uri);
                long[] pattern = {500,500,500,500,500,500,500,500,500};
                builder.setVibrate(pattern);
                builder   .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(1, builder.build());

            }else{

                Log.d(TAG, "onMessageReceived: else block runed!...");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.traderwaale_white);
                builder.setColor(Color.BLUE);
                builder.setContentTitle(title);
                builder.setContentText(body);
                Uri uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.piece_of_cake);
                builder.setSound(uri);
                long[] pattern = {500,500,500,500,500,500,500,500,500};
                builder.setVibrate(pattern);
                builder   .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(1, builder.build());
            }

        }

        if (remoteMessage.getData().size()> 0){
            Log.d(TAG, "onMessageReceived: data size: --->" + remoteMessage.getData().size());

            for (String key: remoteMessage.getData().keySet()){
                Log.d(TAG, "onMessageReceived: key: " + key + "Data" + remoteMessage.getData().get(key));
            }

            Log.d(TAG, "onMessageReceived: data:--> " + remoteMessage.getData().toString());
        }

        //showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }

//    public void showNotification(String title, String message){
//        Intent i = new Intent(this, LoginActivity.class);
//        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, i,0);
//    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages: called");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: called" + s);
    }
}
