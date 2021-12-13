package com.propositive.tradewaale.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.MainActivity;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.connection.NetworkChangeListener;
import com.propositive.tradewaale.openAccount.WebviewActivity;
import com.propositive.tradewaale.privacypolicy.PrivacyPolicyActivity;
import com.propositive.tradewaale.termOfuse.TermofUseActivity;
import com.propositive.tradewaale.test;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "login Activity";

    EditText uMail, uPassword;
    TextView forgot, register;
    Button login;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgot = findViewById(R.id.forgottxt);


        uMail = findViewById(R.id.username);
        uPassword = findViewById(R.id.pass);

        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_text);


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FORGOT_PASSWORD));
                startActivity(browserIntent);

//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
//                View view = getLayoutInflater().inflate(R.layout.forgot_password_layout, null);
//                dialogBuilder.setView(view);
//
//                AlertDialog alertDialog = dialogBuilder.create();
//                alertDialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = uMail.getText().toString();
                String password = uPassword.getText().toString();

                if (!mail.isEmpty() && !password.isEmpty()){
                    FunLogin(mail, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ID and Password is empty!...", Toast.LENGTH_SHORT).show();
                }



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.REGISTER_URL));
                startActivity(browserIntent);
            }
        });

    }

    private void FunLogin(String mail, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: response -> " + response);

                Log.e(TAG, "onResponse: username--->" + mail );
                Log.e(TAG, "onResponse: password--->" + password );


                if (response.equals("record found")){
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Intent intent = new Intent(getApplicationContext(), test.class);
                    intent.putExtra("mail", mail);
                    startActivity(intent);
                    finish();
                    StoreCred(mail,password);
                    //clearField();
                }

                if (response.equals("User not exist!...")){
                    Toast.makeText(getApplicationContext(), "User not exist!...", Toast.LENGTH_SHORT).show();
                }

                if (response.equals("Mail ID and password is empty!...")){
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }

                if (response.equals("User already active")){
                    Toast.makeText(getApplicationContext(), "User already active!...", Toast.LENGTH_SHORT).show();
                }

                if (response.equals("Password Incorrect!...")){
                    Toast.makeText(getApplicationContext(), "Enter valid mail ID and password!...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error 123---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", mail);
                params.put("password", password);
                return params;
            }
        };
        MySingleton.getMySingleton(LoginActivity.this).addToRequestQue(stringRequest);
    }

    private void StoreCred(String mail, String password) {
        SharedPreferences sharedPreference = getSharedPreferences("Log_cred", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("mail",mail);
        editor.putString("pass",password);
        editor.apply();
    }

    private void clearField() {
        uMail.setText("");
        uPassword.setText("");
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();

        checkCred();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void checkCred() {
        SharedPreferences shared = getSharedPreferences("Log_cred", MODE_PRIVATE);
        String UserMail = (shared.getString("mail", ""));
        String UserPass = (shared.getString("pass", ""));

        Log.e(TAG, "checkCred: share_user" + UserMail );
        Log.e(TAG, "checkCred: share_pass" + UserPass );

        if (!UserMail.isEmpty() && !UserPass.isEmpty()){
            FunLogin(UserMail, UserPass);
        }
    }

    public void open_policy(View view) {
        Intent intent = new Intent(LoginActivity.this, PrivacyPolicyActivity.class);
        intent.putExtra("url",Constants.PRIVACY_POLICY_URL);
        startActivity(intent);
    }

    public void open_term(View view) {
        Intent intent = new Intent(LoginActivity.this, TermofUseActivity.class);
        intent.putExtra("url",Constants.TERM_URL);
        startActivity(intent);
    }
}