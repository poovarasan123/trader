package com.propositive.tradewaale.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.propositive.tradewaale.FCMnotification.MySingleton;
import com.propositive.tradewaale.FCMnotification.SharedPreference;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.SplashScreenActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "profile activity";

    TextView musername, name, mob, umail, uplan, expire_at;
    LinearLayout exp;
    CircleImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        musername = findViewById(R.id.username);
        name = findViewById(R.id.username2);
        mob = findViewById(R.id.mobile_txt);
        umail = findViewById(R.id.mail_txt);
        uplan = findViewById(R.id.plan_txt);
        expire_at = findViewById(R.id.expaire_date_txt);

        exp = findViewById(R.id.exp_layout);
        profile_pic = findViewById(R.id.profile_image);

        SharedPreferences getmail = getSharedPreferences("Log_cred", MODE_PRIVATE);
        String mail = getmail.getString("mail","");

        Log.d( TAG, "onCreate: saved mail:---> "+ mail );

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.90.211/trader/api/user_profile.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: response from server:---> "+ response);


                        try {
                            JSONArray jsonarray= new JSONArray(response);  //your response
                            for(int i=0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id       = jsonobject.getString("uid");
                                String prof_pic       = jsonobject.getString("profile_image");
                                String fname    = jsonobject.getString("first_name");
                                String lname  = jsonobject.getString("last_name");
                                String mobile  = jsonobject.getString("phone");
                                String maile = jsonobject.getString("email_id");
                                String plan = jsonobject.getString("plan");
                                String exp_date = jsonobject.getString("expire_at");

                                Log.d(TAG, "onResponse: uid: " + id);
                                Log.d(TAG, "onResponse: prof_pic: " + prof_pic);
                                Log.d(TAG, "onResponse: fname: " + fname);
                                Log.d(TAG, "onResponse: lname: " + lname);
                                Log.d(TAG, "onResponse: mobile: " + mobile);
                                Log.d(TAG, "onResponse: mail: " + maile);
                                Log.d(TAG, "onResponse: plan: " + plan);
                                Log.d(TAG, "onResponse: exp_at: " + exp_date);

                                musername.setText(fname);
                                Picasso.get().load("http://192.168.90.211/trader/imageupload/" +prof_pic).into(profile_pic);
                                name.setText(fname + " " + lname);
                                mob.setText(mobile);
                                umail.setText(maile);
                                uplan.setText(plan);
                                if (!plan.equals("Trial")) {
                                    expire_at.setText(exp_date);
                                }else{
                                    exp.setVisibility(View.GONE);
                                }
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
                params.put("email", mail);

                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }
}