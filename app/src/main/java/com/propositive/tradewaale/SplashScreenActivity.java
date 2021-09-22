package com.propositive.tradewaale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.propositive.tradewaale.FCMnotification.MySingleton;
import com.propositive.tradewaale.connection.NetworkChangeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "splash screen";
    TextView forgot;
    ImageView logo;
    TextView name;
    LinearLayout linearLayout;
    Button login, register, gotoMain;
    Animation top, bottom;
    BottomSheetDialog loginSheet;
    ProgressBar progressBar;
    EditText uMail, uPassword;
    final String HttpURL = "https://192.168.33.211/traderh/session_login/user_Auth.php";
    String LOGIN_URL = "http://192.168.33.211/trader/api/Auth_login.php";

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo_image);
        name = findViewById(R.id.text_name);

        linearLayout = findViewById(R.id.entry_sheet);

        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);

        top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo_anim);
        bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text_anim);

        logo.setAnimation(top);
        name.setAnimation(bottom);
        linearLayout.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loginSheet = new BottomSheetDialog(SplashScreenActivity.this, R.style.BottomSheetDialogTheme);
                        View loginSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(R.layout.login_sheet, findViewById(R.id.loginBottomSheet));

                        TextView forgotpass = loginSheetView.findViewById(R.id.forgottxt);
                        //forgotpass.setVisibility(View.INVISIBLE);
                        //forgotpass.setClickable(false);

                        progressBar = loginSheetView.findViewById(R.id.progressBar);
                        //progressBar.setVisibility(View.INVISIBLE);

                        forgot = loginSheetView.findViewById(R.id.forgottxt);
                        //forgot.setVisibility(View.INVISIBLE);
                        //forgot.setClickable(false);

                        gotoMain = loginSheetView.findViewById(R.id.loginbtn);
                        uMail = loginSheetView.findViewById(R.id.username);
                        uPassword = loginSheetView.findViewById(R.id.pass);

                        forgot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SplashScreenActivity.this);
                                View view = getLayoutInflater().inflate(R.layout.forgot_password_layout, null);
                                dialogBuilder.setView(view);

                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                            }
                        });

                        gotoMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String mail = uMail.getText().toString();
                                String password = uPassword.getText().toString();

                                FunLogin(mail, password);
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                loginSheet.dismiss();
                                //finish();

                            }
                        });
                        loginSheet.setContentView(loginSheetView);
                        loginSheet.show();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://propositive.in/register"));
                        startActivity(browserIntent);
                    }
                });

            }
        }, 2000);
    }

    private void FunLogin(String mail, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: response -> " + response);

                try {
                    Log.e(TAG, "onResponse: response -> " + response);
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("request_code");

                    Log.d(TAG, "onResponse: response_code ---> " + code);
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: error from tc ---> " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error ---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mail", mail);
                params.put("password", password);
                return params;
            }
        };
        MySingleton.getMySingleton(SplashScreenActivity.this).addToRequestQue(stringRequest);
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
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}