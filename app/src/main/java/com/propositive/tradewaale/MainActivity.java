package com.propositive.tradewaale;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.propositive.tradewaale.advisory.bottomAdvisoryFragment;
import com.propositive.tradewaale.connection.NetworkChangeListener;
import com.propositive.tradewaale.home.bottomHomeFragment;
import com.propositive.tradewaale.livefeed.bottomLiveFeedFragment;
import com.propositive.tradewaale.login.LoginActivity;
import com.propositive.tradewaale.market.bottomMarketFragment;
import com.propositive.tradewaale.notification.NotifyListActivity;
import com.propositive.tradewaale.openAccount.OpenAccountActivity;
import com.propositive.tradewaale.privacypolicy.PrivacyPolicyActivity;
import com.propositive.tradewaale.profile.ProfileActivity;
import com.propositive.tradewaale.termOfuse.TermofUseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog moreMenuSheet;
    private BottomSheetDialog supportSheet;

    BottomNavigationView bottomNavView;

    CircleImageView circleImageView;

    LinearLayout theme, profile_menu, openDematAccpunt, privacypolicy, termsOfuse, support, share, logout, menusback;

    ImageView moremenu;

    TextView name_text, number_text;
    String id, prof_pic, fname, mobile;

    String token;
    private ProgressDialog progressDialog;
    private static final String TAG = "Main Activity";
    private static final String SHARE_CONTENT = "Check out Tradewaale app. Get access to SEBI-Registered Trading Ideas, Live Intraday Screeners, All-Day Expert Market Analysis and more! " + "\n" + "ahjrgbahbdfgashdfbg\n" + "https://marketfeed.app/ \n";

//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;

    private final int STORAGE_PERMISSION_CODE = 23;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    String UserMail;

    MenuItem menuItem;
    TextView notification_count;
    String pendingNotification = "0";

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));

        Log.e(TAG, "onCreate: device id:" + android.os.Build.ID );
        Log.e(TAG, "onCreate: device:" + Build.DEVICE );
        Log.e(TAG, "onCreate: device brand:" + android.os.Build.BRAND );
        Log.e(TAG, "onCreate: device model:" + android.os.Build.MODEL );
        Log.e(TAG, "onCreate: device serial:" + android.os.Build.SERIAL );
        Log.e(TAG, "onCreate: device user:" + android.os.Build.USER );
        Log.e(TAG, "onCreate: device host:" + android.os.Build.HOST );

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.d(TAG, "onComplete: task not success" + task);
                }
                String token = task.getResult();
                Log.d(TAG, "onComplete: main activity token " + token);

//                insertToken(token);

                SharedPreferences sharedPreferences = getSharedPreferences("user_token", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token",token);
            }
        });

//        FirebaseMessaging.getInstance().subscribeToTopic("weather").addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "msg_subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "msg_subscribe_failed";
//                        }
//                        Log.d(TAG, msg);
//                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        FirebaseMessaging.getInstance().subscribeToTopic("weather").addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "msg_subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "msg_subscribe_failed";
//                        }
//                        Log.d(TAG, msg);
//                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        FirebaseMessaging.getInstance().subscribeToTopic("weather").addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "msg_subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "msg_subscribe_failed";
//                        }
//                        Log.d(TAG, msg);
//                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        FirebaseMessaging.getInstance().subscribeToTopic("Calls");
        FirebaseMessaging.getInstance().subscribeToTopic("News");
        FirebaseMessaging.getInstance().subscribeToTopic("Notifications");

        SharedPreferences shared = getSharedPreferences("Log_cred", MODE_PRIVATE);
        UserMail = (shared.getString("mail", ""));

        moremenu = findViewById(R.id.more);


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

//        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();

        loadFragment(new bottomHomeFragment());
        bottomNavView = findViewById(R.id.bottom_nav_bar);

        bottomNavView.setSelectedItemId(R.id.bottomHomeFragment);

        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {

                case R.id.bottomHomeFragment:
                    selectedFragment = new bottomHomeFragment();
                    break;
                case R.id.bottomMarketFragment:
                    selectedFragment = new bottomMarketFragment();
                    break;
                case R.id.bottomAdvisoryFragment:
                    selectedFragment = new bottomAdvisoryFragment();
                    break;

                case R.id.bottomLiveFeedFragment:
                    selectedFragment = new bottomLiveFeedFragment();
                    break;
            }
            return loadFragment(selectedFragment);
        });

    }

//    private void insertToken(String token) {
//        SharedPreferences pref_Token = getSharedPreferences("user_token", MODE_PRIVATE);
//        String tok = pref_Token.getString("token", "");
//
//        Log.e(TAG, "insertToken: tok+++++" + token );
//        Intent i = getIntent();
//
//        String mail = i.getStringExtra("mail");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.REG_TOKEN_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.e(TAG, "onResponse: response:--->" + response );
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "onResponse: error response:--->" + error.getMessage() );
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("mail", mail);
//                params.put("token", token);
//                return params;
//            }
//        };
//        MySingleton.getMySingleton(MainActivity.this).addToRequestQue(stringRequest);
//
//    }

    private boolean loadFragment(Fragment selectedFragment) {
        if(selectedFragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (bottomNavView.getSelectedItemId() == R.id.bottomHomeFragment){
            super.onBackPressed();
            UpdateSession(UserMail);
            finish();
        }else{
            bottomNavView.setSelectedItemId(R.id.bottomHomeFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu);

        menuItem = menu.findItem(R.id.notification_bell);


        if (pendingNotification.equals("0")){
            menuItem.setActionView(null);
        }else {
            menuItem.setActionView(R.layout.notification_counter);
            view = menuItem.getActionView();
            notification_count = view.findViewById(R.id.notification_count);
            notification_count.setText(String.valueOf(pendingNotification));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNotification();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.notification_bell:
                openNotification();
                return true;

            case R.id.more:
                openBottomSheet();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openNotification() {
        startActivity(new Intent(this, NotifyListActivity.class));
    }

    private void openBottomSheet() {
        moreMenuSheet = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        View loginSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, findViewById(R.id.more_menu_sheet));

//        circleImageView = loginSheetView.findViewById(R.id.profile_image);
//        //name_text = loginSheetView.findViewById(R.id.username_txt);
//        number_text = loginSheetView.findViewById(R.id.mobile_txt);

        //theme = loginSheetView.findViewById(R.id.theme_menu);

        profile_menu = loginSheetView.findViewById(R.id.profile_menu);
        openDematAccpunt = loginSheetView.findViewById(R.id.open_account_menu);
        privacypolicy = loginSheetView.findViewById(R.id.privacy_menu);
        termsOfuse = loginSheetView.findViewById(R.id.term_menu);
        support = loginSheetView.findViewById(R.id.support_menu);
        share = loginSheetView.findViewById(R.id.share_menu);
        logout = loginSheetView.findViewById(R.id.logout_menu);

        Log.e(TAG, "openBottomSheet: test constants: " + com.propositive.tradewaale.Constants.PROFILE_PATH + prof_pic );

        Picasso.get().load(com.propositive.tradewaale.Constants.PROFILE_PATH + prof_pic).into(circleImageView);
        //name_text.setText(fname);
        number_text.setText(mobile);

        //theme switching
        /**
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Toast.makeText(getApplicationContext(), "checked!...", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("state",isChecked);
                    editor.commit();
                    moreMenuSheet.dismiss();
                    MainActivity.this.recreate();
                }else {
                    Toast.makeText(getApplicationContext(), "not checked!...", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("state",isChecked);
                    editor.commit();
                    moreMenuSheet.dismiss();
                    MainActivity.this.recreate();
                }
            }
        });
         **/

        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        openDematAccpunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OpenAccountActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        termsOfuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TermofUseActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportSheet();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, SHARE_CONTENT);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreMenuSheet.dismiss();
                SharedPreferences sharedpreferences = getSharedPreferences("Log_cred", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                UpdateSession(UserMail);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        moreMenuSheet.setContentView(loginSheetView);
        moreMenuSheet.show();
    }

    private void SupportSheet() {
        supportSheet = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        View supportSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.support_layout, findViewById(R.id.support_sheet));

        LinearLayout closeBox = supportSheetView.findViewById(R.id.close_img);

        LinearLayout mail = supportSheetView.findViewById(R.id.mail_us);
        LinearLayout whatsapp = supportSheetView.findViewById(R.id.talk_to_us);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+91 95665 65278 &text= Hi, I am " + fname + "\n" +"I am using Tradewaale android app and I have a query. \n"));
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

    /**
    public void registerToken(String token) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        //final String token = SharedPreference.getInstance(getApplicationContext()).getDeviceToken();
        //final String token = getToken();
        final String email = "mail.getText().toString();";

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
     **/

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

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        loadProfile();
        super.onStart();
    }

    private void loadProfile() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server:---> "+ response);

                try {
                    JSONArray jsonarray= new JSONArray(response);
                    for(int i=0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        id = jsonobject.getString("uid");
                        prof_pic = jsonobject.getString("profile_image");
                        fname = jsonobject.getString("first_name");
                        mobile = jsonobject.getString("phone");

                        Log.d(TAG, "onResponse: id: " + id);
                        Log.d(TAG, "onResponse: prof_pic: " + prof_pic);
                        Log.d(TAG, "onResponse: fname: " + fname);
                        Log.d(TAG, "onResponse: mobile: " + mobile);

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Something went wrong
                Log.d(TAG, "onResponse: error from server:---> "+ error.getMessage());
            }
        }){
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

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    //TODO: Session Management
    private void UpdateSession(String userMail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.LOG_CLEAR , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "clear successfull "+ response, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: clear response" + response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "clear faild "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: clear response error" + error.getMessage() );
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", userMail);
                return params;
            }
        };
        MySingleton.getMySingleton(MainActivity.this).addToRequestQue(stringRequest);
    }

//    public void openNotification(MenuItem item) {
//        Toast.makeText(getApplicationContext(), "bell clicked!...", Toast.LENGTH_SHORT).show();
//    }
//
//    public void openBottomSheet(MenuItem item) {
//        openBottomSheet();
//    }
}