package com.propositive.tradewaale.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.propositive.tradewaale.FCMnotification.Constants;
import com.propositive.tradewaale.FCMnotification.FcmVolley;
import com.propositive.tradewaale.MainActivity;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String CHANNEL_ID = "101";
    TextInputEditText mail, pass;
    Button login;
    TextView register, forgot, loginText;
    String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        createNotification();
        getToken();

        mail = findViewById(R.id.username);
        pass = findViewById(R.id.pass);

        login = findViewById(R.id.loginbtn);

        register = findViewById(R.id.registertxt);
        forgot = findViewById(R.id.forgottxt);

        loginText = findViewById(R.id.register_text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Registering Device...");
                progressDialog.show();

                //final String token = SharedPreference.getInstance(getApplicationContext()).getDeviceToken();
                final String token = getToken();
                final String email = mail.getText().toString();

                Log.e(TAG, "onClick: mail from input " + email);
                Log.e(TAG, "onClick: token new fcm --->" + token);

                if (token == null) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Token not generated", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_DEVICE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("error from response1", response);
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e("error from response2", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onErrorResponse: volley error" + error.getMessage());
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("token", token);
                        return params;
                    }
                };
                FcmVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Toast.makeText(LoginActivity.this, "under construction!...", Toast.LENGTH_SHORT).show();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "under construction!...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                token = task.getResult();
                Log.d(TAG, "onComplete: token: --->" + token);
            }

        });
        return token;
    }


    public void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebaseNotification";
            String description = "receive firebse notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}