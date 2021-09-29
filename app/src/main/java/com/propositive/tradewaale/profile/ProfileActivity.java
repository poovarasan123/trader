package com.propositive.tradewaale.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.propositive.tradewaale.FCMnotification.MySingleton;
import com.propositive.tradewaale.FCMnotification.SharedPreference;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.SplashScreenActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "profile activity";

    private static final String UPLOAD_URL = "http://192.168.90.211/trader/imageupload/upload_image.php";
    private static final String PROFILE_URL ="http://192.168.90.211/trader/api/user_profile.php";

    TextView musername, name, mob, umail, uplan, expire_at;
    LinearLayout exp;
    CircleImageView profile_pic, profile;
    Uri profUri;
    String mail;

    String id, prof_pic, fname, lname, mobile, maile, plan, exp_date;

    EditText dialogFirstname, dialogLastname, dialogMobile;
    Button save;

    Bitmap bitmap;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "onCreate: date:--->" + getDate());

        musername = findViewById(R.id.username);
        name = findViewById(R.id.username2);
        mob = findViewById(R.id.mobile_txt);
        umail = findViewById(R.id.mail_txt);
        uplan = findViewById(R.id.plan_txt);
        expire_at = findViewById(R.id.expaire_date_txt);

        exp = findViewById(R.id.exp_layout);

        profile_pic = findViewById(R.id.profile_image);

        fab = findViewById(R.id.floatingActionButton);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.profile_dialog,null);
                dialogBuilder.setView(view);

                profile = view.findViewById(R.id.dialog_profile_image);

                dialogFirstname = view.findViewById(R.id.firstname_txt);
                dialogLastname = view.findViewById(R.id.lastname_txt);
                dialogMobile = view.findViewById(R.id.mob_txt);

                save = view.findViewById(R.id.save_btn);

                Picasso.get().load("http://192.168.90.211/trader/imageupload/" +prof_pic).into(profile);
                dialogFirstname.setText(fname);
                dialogLastname.setText(lname);
                dialogMobile.setText(mobile);

                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startCrop();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UploadProfile(id, fname, lname);
                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        SharedPreferences getmail = getSharedPreferences("Log_cred", MODE_PRIVATE);
        mail = getmail.getString("mail","");

        Log.d( TAG, "onCreate: saved mail:---> "+ mail );


    }

    private void UploadProfile(String id, String fname, String lname) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response:---> " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: response:---> " + error.getMessage());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

//                String imageData = imageToString(bitmap);
//                params.put("profile", imageData);
                params.put("uid", id);
                params.put("profile", profUri.toString());
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("mob", mobile);
                params.put("modified_date", getDate());

                Log.d(TAG, "getParams: id: " + id);
                Log.d(TAG, "getParams: profile: " + profUri.toString());
                Log.d(TAG, "getParams: fname: " + fname);
                Log.d(TAG, "getParams: lname: " + lname);
                Log.d(TAG, "getParams: mobile: " + mobile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest);

    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void startCrop() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                try{
                    profUri = result.getUri();

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profUri);

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                //profUri = CropImage.getPickImageResultUri(this, data);
                profile.setImageBitmap(bitmap);

                Log.d(TAG, "onActivityResult: image bitmap:--->" + bitmap);

                Log.d("mainActivity", "onActivityResult: image : --> " + profUri);
                //circleImageView.invalidate();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PROFILE_URL,
                new Response.Listener<String>() {
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
                                lname = jsonobject.getString("last_name");
                                mobile = jsonobject.getString("phone");
                                maile = jsonobject.getString("email_id");
                                plan = jsonobject.getString("plan");
                                exp_date = jsonobject.getString("expire_at");

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

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}