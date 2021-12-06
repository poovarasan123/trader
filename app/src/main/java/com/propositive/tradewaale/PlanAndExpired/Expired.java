package com.propositive.tradewaale.PlanAndExpired;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.test;

import java.util.HashMap;
import java.util.Map;

public class Expired extends AppCompatActivity {

    private static final String TAG = "Expired Activity";
    private String UserMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired);

        SharedPreferences shared = getSharedPreferences("Log_cred", MODE_PRIVATE);
        UserMail = (shared.getString("mail", ""));
    }

    public void go_to_plans(View view) {
        startActivity(new Intent(Expired.this, Plans.class));
    }

    @Override
    public void onBackPressed() {
        exitDialog();
        //UpdateSession(UserMail);
        //finish();

    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Confirm exit")
                .setMessage("Do you really want to exit the app?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateSession(UserMail);
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    //TODO: Session Management
    private void UpdateSession(String userMail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.LOG_CLEAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "clear successfull "+ response, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: clear response" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "clear faild "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: clear response error" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", userMail);
                return params;
            }
        };
        MySingleton.getMySingleton(Expired.this).addToRequestQue(stringRequest);
    }

}