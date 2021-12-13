package com.propositive.tradewaale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.propositive.tradewaale.Indices.IndicesActivity;
import com.propositive.tradewaale.PlanAndExpired.Expired;
import com.propositive.tradewaale.advisory.bottomAdvisoryActivity;
import com.propositive.tradewaale.connection.NetworkChangeListener;
import com.propositive.tradewaale.home.NewsActivity;
import com.propositive.tradewaale.livefeed.EventActivity;
import com.propositive.tradewaale.login.LoginActivity;
import com.propositive.tradewaale.market.bottomMarketActivity;
import com.propositive.tradewaale.openAccount.OpenAccountActivity;
import com.propositive.tradewaale.openAccount.WebviewActivity;
import com.propositive.tradewaale.privacypolicy.PrivacyPolicyActivity;
import com.propositive.tradewaale.profile.ProfileActivity;
import com.propositive.tradewaale.termOfuse.TermofUseActivity;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class test extends AppCompatActivity {

    String holiday;
    String UserMail;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private static final String TAG = "test class";

    int netAmout;
    int moremenu = 0;

    String amount;
    String current_plan_status;
    String date;
    String token;

    Button pay;

    Checkout checkout;
    ImageView moreMenus;

    private BottomSheetDialog moreMenuSheet;
    private BottomSheetDialog supportSheet;

    LinearLayout menusback;

    private static final String SHARE_CONTENT = "Check out Tradewaale app. Get access to SEBI-Registered Trading Ideas, Live Intraday Screeners, All-Day Expert Market Analysis and more! " + "\n" + "ahjrgbahbdfgashdfbg\n" + "https://marketfeed.app/ \n";

    TextView nifty, banknifty;

    String nifty_Close, banknifty_Close;

    String prevDate, minOneDay, minTwoDay;

    WebView webView;

    ImageView NiftyTrend, BankNiftyTrend;

    TextView niftyDayCalc, bankNiftyDayClac;

    TextView niftyPersent, bankniftyPersent;

    DecimalFormat df;

    TickData tickData = new TickData();

    oneBeforeTickData MoneBeforeTickData = new oneBeforeTickData();
    twoBeforeTickData MtwoBeforeTickData = new twoBeforeTickData();

    final Handler handler = new Handler();
    Date d = new Date();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        prev_day();
        Nifty_prev_close("");
        BankNifty_prev_close("");
        //socketData();

        Log.e(TAG, "onCreate: today = " + getToday() );

        SharedPreferences shared = getSharedPreferences("Log_cred", MODE_PRIVATE);
        UserMail = (shared.getString("mail", ""));

        Log.d(TAG, "onCreate: usermail" + UserMail);

        //loadProfile(UserMail);

        niftyDayCalc = findViewById(R.id.nifty_day_calc);
        bankNiftyDayClac = findViewById(R.id.banknifty_day_calc);

        niftyPersent = findViewById(R.id.nifty_persent_text);
        bankniftyPersent = findViewById(R.id.banknifty_persent_text);

        NiftyTrend = findViewById(R.id.nifty_trend_status);
        BankNiftyTrend = findViewById(R.id.banknifty_trend_status);

        moreMenus = findViewById(R.id.open_more_menu_sheet);
        menusback = findViewById(R.id.menus_back);

        nifty = findViewById(R.id.nifty_data);
        banknifty = findViewById(R.id.bank_nifty_data);

        df = new DecimalFormat("0.00");

        moreMenus.setOnClickListener(v -> {

            moremenu++;
            if (moremenu % 2 == 1) {
                menusback.setVisibility(View.VISIBLE);
            } else {
                menusback.setVisibility(View.INVISIBLE);
            }
        });

        webView = findViewById(R.id.seven_days_trede);
        loadSevenDaysTrend();

    }

    private void ValidatePlan(String mail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server:---> " + response);

                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        current_plan_status = jsonobject.getString("user_plan_status");

                        Log.d(TAG, "onResponse: id: " + current_plan_status);

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (current_plan_status.equals("expired")) {
                    startActivity(new Intent(test.this, Expired.class));
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Something went wrong
                Log.d(TAG, "onResponse: error from server:---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Log.d(TAG, "getParams: check mail:---> " + mail);
                params.put("email", mail);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                //.setTitle("Confirm exit")
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);

        UserId();
        ValidatePlan(UserMail);

        if (d.getDay() == 0 || d.getDay() == 6) {
            //no morket saturday and sunday
            Log.e(TAG, "onStart: inside if:" + d.getDay() );
            switch(d.getDay()){
                case 0:
                    Log.e(TAG, "onStart: case 0" );
                    new Thread(MtwoBeforeTickData).start();
                    break;
                case 6:
                    Log.e(TAG, "onStart: case 6" );
                    new Thread(MoneBeforeTickData).start();
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "else week days: " + d.getDay(), Toast.LENGTH_SHORT).show();
            new Thread(tickData).start();
        }
    }

    private void loadSevenDaysTrend() {
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("http://www.screener.tradewaale.com/mobile-daily-oi-analyzer");
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void UserId() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server:---> " + response);

                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                        current_plan_status = jsonobject.getString("uid");

                        SharedPreferences.Editor sharedPreferences = getSharedPreferences("user_cred", MODE_PRIVATE).edit();
                        sharedPreferences.putString("userId", jsonobject.getString("uid"));
                        sharedPreferences.putString("userfname", jsonobject.getString("first_name"));
                        sharedPreferences.putString("userlname", jsonobject.getString("last_name"));
                        sharedPreferences.putString("usermail", jsonobject.getString("email_id"));
                        sharedPreferences.putString("usernumber", jsonobject.getString("phone"));
                        sharedPreferences.apply();

                        Log.d(TAG, "onResponse: current plan status: " + current_plan_status);
                        Log.d(TAG, "onResponse: current fname: " +  jsonobject.getString("first_name"));
                        Log.d(TAG, "onResponse: current lname: " +  jsonobject.getString("last_name"));
                        Log.d(TAG, "onResponse: current mail: " +  jsonobject.getString("email_id"));
                        Log.d(TAG, "onResponse: current phone: " +  jsonobject.getString("phone"));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //checkPlanValidity(current_plan_status);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Something went wrong
                Log.d(TAG, "onResponse: error from server:---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Log.d(TAG, "getParams: check mail:---> " + mail);
                params.put("email", UserMail);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    private void checkPlanValidity(String id) {

        Log.e(TAG, "checkPlanValidity: id check :" + id);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PLAN_VADATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server plan checking :---> " + response);

                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                        date = jsonobject.getString("end_date");

                        Log.d(TAG, "onResponse: date: " + date);

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//                if (Date.parse(date) < Date.parse(getDate())){
//
//                    Log.d(TAG, "onResponse: date" + Date.parse(date));
//                    Log.d(TAG, "onResponse: date" + Date.parse(getDate()));
//
//
//                    //Toast.makeText(getApplicationContext(), "date not equal!...", Toast.LENGTH_SHORT).show();
////                    startActivity(new Intent(test.this, Expired.class));
////                    finish();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error from server:---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Log.d(TAG, "getParams: check mail:---> " + mail);
                params.put("uid", id);

                return params;
            }
        };
        MySingleton.getMySingleton(test.this).addToRequestQue(stringRequest);
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
        MySingleton.getMySingleton(test.this).addToRequestQue(stringRequest);
    }

    public void go_to_home(View view) {
        startActivity(new Intent(test.this, NewsActivity.class));
    }

    public void go_to_market(View view) {
        startActivity(new Intent(test.this, bottomMarketActivity.class));
    }

    public void go_to_advisory(View view) {
        startActivity(new Intent(test.this, bottomAdvisoryActivity.class));
    }

    public void go_to_event(View view) {
        Intent intent = new Intent(test.this, EventActivity.class);
        startActivity(intent);
    }

    public void go_to_future_oi_analyser(View view) {
        Intent intent = new Intent(test.this, WebviewActivity.class);
        intent.putExtra("url", Constants.FUTURE_OI_ANALYSER);
        startActivity(intent);
    }

    public void go_to_future_daily_oi_analyser(View view) {
        Intent intent = new Intent(test.this, WebviewActivity.class);
        intent.putExtra("url", Constants.DAILY_OI_ANALYSER);
        startActivity(intent);
    }

    public void OPTION_TREND_ANALYSER(View view) {
        Intent intent = new Intent(test.this, WebviewActivity.class);
        intent.putExtra("url", Constants.OPTION_TREND_ANALYSER);
        startActivity(intent);
    }

    public void OPTION_OI_ANALYSER(View view) {
        Intent intent = new Intent(test.this, WebviewActivity.class);
        intent.putExtra("url", Constants.OPTION_OI_ANALYSER);
        startActivity(intent);
    }

    public void openBottomSheet(View view) {
        moreMenuSheet = new BottomSheetDialog(test.this, R.style.BottomSheetDialogTheme);
        View loginSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.follow_us, findViewById(R.id.more_menu_sheet));

//        circleImageView = loginSheetView.findViewById(R.id.profile_image);
//        name_text = loginSheetView.findViewById(R.id.username_txt);
//        number_text = loginSheetView.findViewById(R.id.mobile_txt);

        //theme = loginSheetView.findViewById(R.id.theme_menu);

//        profile_menu = loginSheetView.findViewById(R.id.profile_menu);
//        openDematAccpunt = loginSheetView.findViewById(R.id.open_account_menu);
//        privacypolicy = loginSheetView.findViewById(R.id.privacy_menu);
//        termsOfuse = loginSheetView.findViewById(R.id.term_menu);
//        support = loginSheetView.findViewById(R.id.support_menu);
//        share = loginSheetView.findViewById(R.id.share_menu);
//        logout = loginSheetView.findViewById(R.id.logout_menu);
//
////        Log.e(TAG, "openBottomSheet: test constants: " + com.propositive.tradewaale.Constants.PROFILE_PATH + prof_pic );
////
////        Picasso.get().load(com.propositive.tradewaale.Constants.PROFILE_PATH + prof_pic).into(circleImageView);
////        name_text.setText(fname);
////        number_text.setText(mobile);
//
////       theme switching
//        /**
//         themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//        if (buttonView.isChecked()) {
//        Toast.makeText(getApplicationContext(), "checked!...", Toast.LENGTH_SHORT).show();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        editor.putBoolean("state",isChecked);
//        editor.commit();
//        moreMenuSheet.dismiss();
//        MainActivity.this.recreate();
//        }else {
//        Toast.makeText(getApplicationContext(), "not checked!...", Toast.LENGTH_SHORT).show();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        editor.putBoolean("state",isChecked);
//        editor.commit();
//        moreMenuSheet.dismiss();
//        MainActivity.this.recreate();
//        }
//        }
//        });
//         **/
//
//        profile_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(test.this, ProfileActivity.class));
//                moreMenuSheet.dismiss();
//            }
//        });
//
//        openDematAccpunt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(test.this, OpenAccountActivity.class));
//                moreMenuSheet.dismiss();
//            }
//        });
//
//        privacypolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(test.this, PrivacyPolicyActivity.class));
//                moreMenuSheet.dismiss();
//            }
//        });
//
//        termsOfuse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(test.this, TermofUseActivity.class));
//                moreMenuSheet.dismiss();
//            }
//        });
//
//        support.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SupportSheet();
//            }
//        });
//
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, SHARE_CONTENT);
//                sendIntent.setType("text/plain");
//                Intent.createChooser(sendIntent, "Share via");
//                startActivity(sendIntent);
//            }
//        });
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moreMenuSheet.dismiss();
//                SharedPreferences sharedpreferences = getSharedPreferences("Log_cred", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.clear();
//                editor.apply();
//                SharedPreferences.Editor sharedPreferences2 = getSharedPreferences("socket_token", Context.MODE_PRIVATE).edit();
//                sharedPreferences2.clear();
//                sharedPreferences2.apply();
//                UpdateSession(UserMail);
//                startActivity(new Intent(test.this, LoginActivity.class));
//                finish();
//            }
//        });

        moreMenuSheet.setContentView(loginSheetView);
        moreMenuSheet.show();
    }

    private void SupportSheet() {
        supportSheet = new BottomSheetDialog(test.this, R.style.BottomSheetDialogTheme);
        View supportSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.support_layout, findViewById(R.id.support_sheet));

        LinearLayout closeBox = supportSheetView.findViewById(R.id.close_img);

        LinearLayout mail = supportSheetView.findViewById(R.id.mail_us);
        LinearLayout whatsapp = supportSheetView.findViewById(R.id.talk_to_us);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+91 95665 65278 &text= Hi, I am " + "\n" + "I am using Tradewaale android app and I have a query. \n"));
                startActivity(i);
            }
        });

        closeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportSheet.dismiss();
            }
        });

        supportSheet.setContentView(supportSheetView);
        supportSheet.show();
    }

    public void Logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("Log_cred", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        UpdateSession(UserMail);
        startActivity(new Intent(test.this, LoginActivity.class));
        finish();
    }

    public void openProfile(View view) {
        startActivity(new Intent(test.this, ProfileActivity.class));
    }

    public void openAccount(View view) {
        startActivity(new Intent(test.this, OpenAccountActivity.class));
    }

    public void openPolicy(View view) {
        Intent intent = new Intent(test.this, PrivacyPolicyActivity.class);
        intent.putExtra("url", Constants.PRIVACY_POLICY_URL);
        startActivity(intent);
        //startActivity(new Intent(test.this, PrivacyPolicyActivity.class));
    }

    public void openTerm(View view) {
        Intent intent = new Intent(test.this, TermofUseActivity.class);
        intent.putExtra("url", Constants.TERM_URL);
        startActivity(intent);

        //startActivity(new Intent(test.this, TermofUseActivity.class));
    }

    public void openSupport(View view) {
        SupportSheet();
    }

    public void openShare(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, SHARE_CONTENT);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }

    public void go_to_indices(View view) {
        startActivity(new Intent(getApplicationContext(), IndicesActivity.class));
    }

    //TODO: previous close value
    public void Nifty_prev_close(String date) {
        String url = "https://android.tradewaale.com/get_yes_nifty.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: response from nifty previous close :---> " + response);

                nifty_Close = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error from server:---> " + error.getMessage());
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Log.d(TAG, "getParams: check mail:---> " + mail);
                if (!date.isEmpty()){
                    params.put("date", date);
                } else {
                    params.put("date", prev_day());
                }
                return params;
            }
        };
        MySingleton.getMySingleton(test.this).addToRequestQue(stringRequest);
    }

    public void BankNifty_prev_close(String date) {
        String url = "https://android.tradewaale.com/get_yes_banknifty.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from banknifty previous close :---> " + response);
                banknifty_Close = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error from server:---> " + error.getMessage());
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Log.d(TAG, "getParams: check mail:---> " + mail);

                if (!date.isEmpty()){
                    params.put("date", date);
                } else {
                    params.put("date", prev_day());
                }

                return params;
            }
        };
        MySingleton.getMySingleton(test.this).addToRequestQue(stringRequest);
    }

    private String getToday() {
        return new SimpleDateFormat("dd-MMM-yyy").format(Calendar.getInstance().getTime());
    }

    //TODO: previous day
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String prev_day() {
        LocalDate today = LocalDate.now();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        if (dayOfTheWeek.equals("Monday")) {
            prevDate = (today.minusDays(3)).format(DateTimeFormatter.ISO_DATE);
        } else {
            prevDate = (today.minusDays(1)).format(DateTimeFormatter.ISO_DATE);
        }
        Log.e(TAG, "prev_day: " + prevDate);

        return prevDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String oneDaymin(){
        LocalDate date = LocalDate.parse(getSocToday());
        LocalDate date1 = date.minusDays(1);
        String date2 = String.valueOf(date1);

        BankNifty_prev_close(String.valueOf(date.minusDays(2)));
        Nifty_prev_close(String.valueOf(date.minusDays(2)));

        String date3 = date2.substring(2, 10);
        minOneDay = date3.replace("-", "");
        Log.e(TAG, "one_day_min: " + minOneDay + ": " + date );
        return minOneDay;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String twoDaymin(){
        LocalDate date = LocalDate.parse(getSocToday());
        LocalDate date1 = date.minusDays(2);
        String date2 = String.valueOf(date1);

        Log.e(TAG, "twoDaymin: " + date2 );

        BankNifty_prev_close(String.valueOf(date.minusDays(3)));
        Nifty_prev_close(String.valueOf(date.minusDays(3)));

        Log.e(TAG, "twoDaymin: 06 banknifty close: " + banknifty_Close );
        Log.e(TAG, "twoDaymin: 06 nifty close: " + nifty_Close );


        String date3 = date2.substring(2, 10);
        minTwoDay = date3.replace("-", "");
        Log.e(TAG, "two_day_min: " + minTwoDay + ": " + date );
        return minTwoDay;
    }

//    private String StoreNiftyData(String data) {
//        SharedPreferences.Editor sharedPreferences = getSharedPreferences("nifty_data", MODE_PRIVATE).edit();
//        sharedPreferences.putString("close_nifty_data", data);
//        sharedPreferences.apply();
//        return data;
//    }

    private String StoreBankNiftyData(String data) {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("banknifty_data", MODE_PRIVATE).edit();
        sharedPreferences.putString("close_banknifty_data", data);
        sharedPreferences.apply();
        return data;
    }

    public String NSE_HOLIDAY(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.NSE_HOLIDAYS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: holiday:---> " + response);
                holiday = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: holiday:---> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("h_date", getToday());

                return params;
            }
        };
        MySingleton.getMySingleton(test.this).addToRequestQue(stringRequest);

        return holiday;
    }

    public String socketData() {
        Log.e(TAG, "socketData: request called");
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "username=tdws141&password=propositive@141&grant_type=password");
        Log.e(TAG, "socketData: request created");

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://auth.truedata.in/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            okhttp3.Response response = client.newCall(request).execute();

            String res = response.body().string();

            Log.e(TAG, "socketData: res: " + res );

            if (res.substring(0, 15) != "<!DOCTYPE html>"){
                try {
                    JSONObject call_history = new JSONObject(res);

                    token = call_history.getString("access_token");

                    SharedPreferences.Editor sharedPreferences = getSharedPreferences("socket_token", MODE_PRIVATE).edit();
                    sharedPreferences.putString("token", token);
                    sharedPreferences.apply();
                    //Log.e(TAG, "socketData: token from response " + token);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
                Toast.makeText(getApplicationContext(), "Live date not connected...", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    private void refresh(int i) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new Thread(tickData).start();
            }
        };
        handler.postDelayed(runnable, i);
    }

    private String getSocToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    private String getBankNiftyToday() {
        return new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
    }

    class TickData implements Runnable {

        @Override
        public void run() {

            if (token != null){

                //TODO: Nifty live data
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY-I&from=" + getBankNiftyToday() + "T09:15:00&to=" + getBankNiftyToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        //Log.e(TAG, "TickData: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - Float.parseFloat(nifty_Close);
                        float res = val / Float.parseFloat(nifty_Close);
                        float persent = res * 100;

                        nifty.setText(bn);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyDayCalc.setText(df.format(val));
                                niftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        refresh(1000);

//                    Log.e(TAG, "onPostExecute: nifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: nifty bc " + nifty_Close);
//                    Log.e(TAG, "onPostExecute: nifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: nifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: nifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO: BankNifty live data

                OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request2 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=BANKNIFTY-I&from=" + getBankNiftyToday() + "T09:15:00&to=" + getBankNiftyToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client2.newCall(request2).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - Float.parseFloat(banknifty_Close);
                        float res = val / Float.parseFloat(banknifty_Close);
                        float persent = res * 100;

                        banknifty.setText(bn);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bankNiftyDayClac.setText(df.format(val));
                                bankniftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.green));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.red));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });

//                    Log.e(TAG, "onPostExecute: banknifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: banknifty bc " + banknifty_Close);
//                    Log.e(TAG, "onPostExecute: banknifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: banknifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: banknifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                socketData();
                new Thread(tickData).start();
            }
        }

    }

    class oneBeforeTickData implements Runnable {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {

            if (token != null){

                //TODO: Nifty live data
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY-I&from=" + oneDaymin() + "T09:15:00&to=" + oneDaymin() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        //Log.e(TAG, "TickData: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - Float.parseFloat(nifty_Close);
                        float res = val / Float.parseFloat(nifty_Close);
                        float persent = res * 100;

                        nifty.setText(bn);

                        Log.e(TAG, "run: onedaybefire nifty: " + recordsList.get(0));

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyDayCalc.setText(df.format(val));
                                niftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1000);

//                    Log.e(TAG, "onPostExecute: nifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: nifty bc " + nifty_Close);
//                    Log.e(TAG, "onPostExecute: nifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: nifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: nifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO: BankNifty live data

                OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request2 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=BANKNIFTY-I&from=" + oneDaymin() + "T09:15:00&to=" + oneDaymin() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client2.newCall(request2).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - Float.parseFloat(banknifty_Close);
                        float res = val / Float.parseFloat(banknifty_Close);
                        float persent = res * 100;

                        banknifty.setText(bn);

                        Log.e(TAG, "run: onedaybefire banknifty: " + recordsList.get(0));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bankNiftyDayClac.setText(df.format(val));
                                bankniftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.green));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.red));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });

//                    Log.e(TAG, "onPostExecute: banknifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: banknifty bc " + banknifty_Close);
//                    Log.e(TAG, "onPostExecute: banknifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: banknifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: banknifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                socketData();
                new Thread(MoneBeforeTickData).start();
            }
        }

    }

    class twoBeforeTickData implements Runnable {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {

            if (!token.isEmpty()){

                //TODO: Nifty live data
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY-I&from=" + twoDaymin() + "T09:15:00&to=" + twoDaymin() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");


                        Collections.reverse(recordsList);

                        //Log.e(TAG, "TickData: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        Log.e(TAG, "run: nifty 1 day calc:" + bn );

                        float val = Float.parseFloat(bn) - Float.parseFloat(nifty_Close);
                        float res = val / Float.parseFloat(nifty_Close);
                        float persent = res * 100;

                        nifty.setText(bn);

                        Log.e(TAG, "run: 2 days: = " + bn );
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyDayCalc.setText(df.format(val));
                                niftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
                                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1000);

//                    Log.e(TAG, "onPostExecute: nifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: nifty bc " + nifty_Close);
//                    Log.e(TAG, "onPostExecute: nifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: nifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: nifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //TODO: BankNifty live data

                OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request2 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=BANKNIFTY-I&from=" + twoDaymin() + "T09:15:00&to=" + twoDaymin() + "T15:30:00&response=json&bidask=1min")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client2.newCall(request2).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - Float.parseFloat(banknifty_Close);
                        float res = val / Float.parseFloat(banknifty_Close);
                        float persent = res * 100;

                        banknifty.setText(bn);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bankNiftyDayClac.setText(df.format(val));
                                bankniftyPersent.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.green));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    BankNiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
                                    bankNiftyDayClac.setTextColor(getResources().getColor(R.color.red));
                                    bankniftyPersent.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });

//                    Log.e(TAG, "onPostExecute: banknifty bn " + bn);
//                    Log.e(TAG, "onPostExecute: banknifty bc " + banknifty_Close);
//                    Log.e(TAG, "onPostExecute: banknifty val " + df.format(val));
//                    Log.e(TAG, "onPostExecute: banknifty res " + df.format(res));
//                    Log.e(TAG, "onPostExecute: banknifty persent " + df.format(persent));
//                    Log.e(TAG, "TickData: set text called" );

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                socketData();
                new Thread(MtwoBeforeTickData).start();
            }
        }

    }

}