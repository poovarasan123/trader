package com.propositive.tradewaale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.propositive.tradewaale.FCMnotification.Constants;
import com.propositive.tradewaale.FCMnotification.FcmVolley;
import com.propositive.tradewaale.login.LoginActivity;
import com.propositive.tradewaale.register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    //TextInputEditText mail, pass;

    private static final String TAG = "splash screen";
    TextView forgot;
    String token;

    ImageView logo;
    TextView name;

    LinearLayout linearLayout;

    Button login, register, gotoMain;

    Animation top, bottom;
    BottomSheetDialog loginSheet;
    ProgressBar progressBar;
    EditText mail, password;
    private String LOGIN_URL = "http://192.168.239.211/trader/api/Auth_login.php";
    private ProgressDialog progressDialog;

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

        // linearLayout.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loginSheet = new BottomSheetDialog(SplashScreenActivity.this, R.style.BottomSheetDialogTheme);
                        View loginSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(R.layout.login_sheet, findViewById(R.id.loginBottomSheet));

                        //TextView forgotpass = loginSheetView.findViewById(R.id.forgottxt);
                        //forgotpass.setVisibility(View.INVISIBLE);
                        //forgotpass.setClickable(false);

                        progressBar = loginSheetView.findViewById(R.id.progressBar);
                        //progressBar.setVisibility(View.INVISIBLE);

                        forgot = loginSheetView.findViewById(R.id.forgottxt);
                        //forgot.setVisibility(View.INVISIBLE);
                        //forgot.setClickable(false);

                        gotoMain = loginSheetView.findViewById(R.id.loginbtn);

                        mail = loginSheetView.findViewById(R.id.username);
                        password = loginSheetView.findViewById(R.id.pass);

                        gotoMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String user = mail.getText().toString();
                                String pass = password.getText().toString();

                                if (!user.isEmpty() && !pass.isEmpty()) {
                                    gotoMain.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    //authUser(user, pass);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    loginSheet.dismiss();
                                    finish();

                                    progressBar.setVisibility(View.GONE);
                                    gotoMain.setVisibility(View.VISIBLE);

                                    clearField();

                                    Log.d(TAG, "onClick: mail --> " + user);
                                    Log.d(TAG, "onClick: pass --> " + pass);
                                } else {
                                    if (user.isEmpty())
                                        mail.setError("Enter the vaild mail ID!...");
                                    if (pass.isEmpty())
                                        password.setError("Enter the valid password!...");

                                }

//                                if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
//                                    login(user,pass);
//                                    registerToken(mail);
//                                }else {
//                                    Toast.makeText(getApplicationContext(), "Please Enter the details!...", Toast.LENGTH_SHORT).show();
//                                }
                                //forgotpass.setOnClickListener(view -> ResetPassword());
                            }
                        });
                        loginSheet.setContentView(loginSheetView);
                        loginSheet.show();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    }
                });

            }
        }, 2000);
    }

    private void clearField() {
        mail.setText("");
        password.setText("");
    }

    private void authUser(String user, String pass) {
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String uMail = object.getString("name").trim();
                            String uPass = object.getString("pass").trim();

                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            gotoMain.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    gotoMain.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "error ---> " + e.toString(), Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onResponse: error" + e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                gotoMain.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "error ---> " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mail", user);
                params.put("pass", pass);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void ResetPassword() {
        Toast.makeText(getApplicationContext(), "under construction!...", Toast.LENGTH_SHORT).show();
    }

    public void registerToken(String userMail) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        //final String token = SharedPreference.getInstance(getApplicationContext()).getDeviceToken();
        final String token = getToken();
        final String email = mail.getText().toString();

        Log.e(TAG, "onClick: mail from input " + email);
        Log.e(TAG, "onClick: token new fcm --->" + token);

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Token not generated", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
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
}