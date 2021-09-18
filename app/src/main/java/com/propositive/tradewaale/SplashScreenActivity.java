package com.propositive.tradewaale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.propositive.tradewaale.login.HttpParse;
import com.propositive.tradewaale.login.LoginActivity;
import com.propositive.tradewaale.register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String UserEmail = "";
    private static final String TAG = "splash screen";
    //TextInputEditText mail, pass;
    TextView forgot;
    ImageView logo;
    TextView name;
    LinearLayout linearLayout;
    Button login, register, gotoMain;
    Animation top, bottom;
    BottomSheetDialog loginSheet;
    ProgressBar progressBar;
    EditText mail, password;
    String PasswordHolder, EmailHolder;
    String finalResult;
    String HttpURL = "https://192.168.14.211/traderh/session_login/user_Auth.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    private String LOGIN_URL = "http://192.168.14.211/trader/api/Auth_login.php";

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

                        TextView forgotpass = loginSheetView.findViewById(R.id.forgottxt);
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

                        forgot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SplashScreenActivity.this);
                                View view = getLayoutInflater().inflate(R.layout.otp_verify_layout, null);
                                dialogBuilder.setView(view);

                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                            }
                        });

                        gotoMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //authUser(EmailHolder, PasswordHolder);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

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
                        //startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    }
                });

            }
        }, 2000);
    }


    private void UserLoginFunction(String email, String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response -->" + response);
                if (response.equals("record found")) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        /**
         class UserLoginClass extends AsyncTask<String,Void,String> {

        @Override protected void onPostExecute(String httpResponseMsg) {

        Log.d(TAG, "onPostExecute: response is ----> " + httpResponseMsg);

        if (httpResponseMsg.equals("record found")){
        Toast.makeText(getApplicationContext(), "login successfull!..", Toast.LENGTH_SHORT).show();
        }else{
        Toast.makeText(getApplicationContext(), "login failed!...", Toast.LENGTH_SHORT).show();
        }

        //                super.onPostExecute(httpResponseMsg);
        //
        //                progressDialog.dismiss();
        //
        //                Log.d(TAG, "onPostExecute: response true --->" + httpResponseMsg);
        //
        //                if(!httpResponseMsg.equalsIgnoreCase("record found")){
        //
        //                    Toast.makeText(getApplicationContext(), "launched!...", Toast.LENGTH_SHORT).show();
        //
        //                    progressBar.setVisibility(View.GONE);
        //                    gotoMain.setVisibility(View.VISIBLE);
        //
        //                    finish();
        //
        //                    loginSheet.dismiss();
        //
        //
        //                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        //
        //                    intent.putExtra(UserEmail,email);
        //
        //                    startActivity(intent);
        //
        //                }
        //                else{
        //                    Log.d(TAG, "onPostExecute: response false --->" + httpResponseMsg);
        //                    Toast.makeText(getApplicationContext(), "not launched!...", Toast.LENGTH_SHORT).show();
        //                    progressBar.setVisibility(View.GONE);
        //                    gotoMain.setVisibility(View.VISIBLE);
        //                    Toast.makeText(SplashScreenActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
        //                }

        }

        @Override protected String doInBackground(String... params) {

        hashMap.put("email",params[0]);

        hashMap.put("password",params[1]);

        finalResult = httpParse.postRequest(hashMap, HttpURL);

        return finalResult;
        }
        }

         UserLoginClass userLoginClass = new UserLoginClass();

         userLoginClass.execute(email,password);
         **/

    }


    private void clearField() {
        mail.setText("");
        password.setText("");
    }

    private void authUser(String user, String pass) {
        StringRequest request = new StringRequest(Request.Method.POST, HttpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: response--->"  + response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                if (response.equals("record found")) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String uMail = object.getString("name").trim();
//                            String uPass = object.getString("pass").trim();

                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                    gotoMain.setVisibility(View.VISIBLE);
                    //}
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                gotoMain.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "error ---> " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: error" + error.toString());
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

}