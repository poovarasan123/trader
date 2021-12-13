package com.propositive.tradewaale.PlanAndExpired;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.R;

import java.util.HashMap;
import java.util.Map;

public class Expired extends AppCompatActivity {

    private static final String TAG = "Expired Activity";

    String UserMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired);

        SharedPreferences shared = getSharedPreferences("Log_cred", MODE_PRIVATE);
        UserMail = (shared.getString("mail", ""));
    }

    public void go_to_plans(View view) {
        startActivity(new Intent(Expired.this, Plans.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Do you really want to exit the app?")
                .setPositiveButton("Exit", (dialog, which) -> {
                    UpdateSession(UserMail);
                    dialog.dismiss();
                    finish();
                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void UpdateSession(String userMail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.LOG_CLEAR, response -> {
            Log.e(TAG, "onResponse: clear response" + response);
        }, error -> {
            Log.e(TAG, "onResponse: clear response error" + error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", userMail);
                return params;
            }
        };
        MySingleton.getMySingleton(Expired.this).addToRequestQue(stringRequest);
    }

}