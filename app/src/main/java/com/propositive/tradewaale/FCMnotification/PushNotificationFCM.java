package com.propositive.tradewaale.FCMnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.SplashScreenActivity;
import com.propositive.tradewaale.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushNotificationFCM extends FirebaseMessagingService {

    private static final String TAG = "PushNotificationFCM";

    private static  final String CHANNEL_ID = "101";

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    @Override
    public void onNewToken(@NonNull String s) {

        //firebase token created
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.d(TAG, "onComplete: task not success" + task);
                }
                String token = task.getResult();
                Log.d(TAG, "onComplete: token " + token);
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0){
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                //TODO: my push code

                showSmallNotification(title,message, intent);

            }else{
                //if there is an image
                //displaying a big notification
                showBigNotification(title, message, imageUrl, intent);
                Log.e(TAG, "sendPushNotification: large notification called!... " );
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    private void showBigNotification(String title, String message, String imageUrl, Intent intent) {
        PendingIntent resultPendingIntent =  PendingIntent.getActivity(this, ID_BIG_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri = Uri.parse("android.resource://"+ this.getApplicationContext() +"/"+R.raw.confident);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.traderwaale_white);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setContentTitle(title);
        mBuilder.setSound(uri);
        mBuilder.setSmallIcon(R.drawable.traderwaale_blue);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setContentText(message);
        mBuilder.build();

        ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, mBuilder.build());
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error " + error );
            }
        });
        MySingleton.getMySingleton(this).addToRequestQue(imageRequest);
    }

    private void showSmallNotification(String title, String message, Intent intent) {

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, ID_SMALL_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d(TAG, "onMessageReceived: if block runed!...");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.traderwaale_blue);
        //builder.setColor(Color.BLUE);
        builder.setContentIntent(resultPendingIntent);
        builder.setContentTitle(title);
        builder.setContentText(message);
        Uri uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.confident);
        builder.setSound(uri);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());
    }
}
