package com.propositive.tradewaale.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
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
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "profile activity";

    private static final String UPLOAD_URL = "http://192.168.90.211/trader/imageupload/upload_profile.php";
    private static final String PROFILE_URL ="http://192.168.90.211/trader/api/user_profile.php";

    TextView musername, name, mob, umail, uplan, expire_at;
    LinearLayout exp;
    CircleImageView profile_pic, profile;
    Uri profUri;
    String mail;

    String id, prof_pic, fname, lname, mobile, maile, plan, exp_date;

    EditText dialogFirstname, dialogLastname, dialogMobile;

    String dfname, dlname, dmobile;
    Button save;

    FloatingActionButton fab;

    private String[] permission;

    private ProgressDialog progressDialog;

    AlertDialog.Builder dialogBuilder;
    View view;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        permission = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Profile Updating!....");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

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
                dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                view = getLayoutInflater().inflate(R.layout.profile_dialog,null);
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

                        if (!hasPermissions(ProfileActivity.this, permission)){
                            ActivityCompat.requestPermissions(ProfileActivity.this, permission, 1);

                        }
                        startCrop();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dfname = dialogFirstname.getText().toString();
                        dlname = dialogLastname.getText().toString();
                        dmobile = dialogMobile.getText().toString();

                        Log.d(TAG, "onClick: save fname after edit:---> " + dfname);
                        Log.d(TAG, "onClick: save lname after edit:---> " + dlname);
                        Log.d(TAG, "onClick: save mob after edit:---> " + dmobile);

                        if (!dfname.isEmpty() && !dlname.isEmpty() && !dmobile.isEmpty()){
                            if (profUri != null){
                                File file = new File(profUri.getPath());
                                progressDialog.show();
                                AndroidNetworking.upload(UPLOAD_URL)
                                        .addMultipartFile("profile", file)
                                        .addMultipartParameter("id", id)
                                        .addMultipartParameter("fname", dfname)
                                        .addMultipartParameter("lname", dlname)
                                        .addMultipartParameter("mob", String.valueOf(dmobile))
                                        .addMultipartParameter("modified_date", getDate())
                                        .setPriority(Priority.HIGH)
                                        .build()
                                        .setUploadProgressListener(new UploadProgressListener() {
                                            @Override
                                            public void onProgress(long bytesUploaded, long totalBytes) {
                                                float progress = bytesUploaded / totalBytes * 100;
                                                progressDialog.setProgress((int)progress);

                                            }
                                        }).getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d(TAG, "onResponse: response from server: " + response);
                                            progressDialog.dismiss();
                                            JSONObject jsonObject = new JSONObject(response);
                                            int status = jsonObject.getInt("status");
                                            String message = jsonObject.getString("message");
                                            if (status == 0 ){
                                                Toast.makeText(getApplicationContext(), "Unable to upload image:" + message, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                finish();

                                            }
                                        } catch (JSONException e) {
                                            progressDialog.dismiss();
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "Pasring error!...", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "onResponse: error from response " + e.getMessage());
                                        }

                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        progressDialog.dismiss();
                                        anError.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error Uploading Image!...", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onError: error from image:---> " + anError);
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Profile modification not found!...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Profile details missing!...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog = dialogBuilder.create();
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

    private void startCrop() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                profUri = result.getUri();

                profile.setImageURI(profUri);

                Log.d("mainActivity", "onActivityResult: image : --> " + profUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Read storage permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Read storage permisions denied!...", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Write storage permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Write storage permisions denied!...", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(getApplicationContext(), "Camera permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Camera permisions denied!...", Toast.LENGTH_SHORT).show();
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

    private boolean hasPermissions(Context context, String... permission){
        if (context != null && permission != null){
            for (String PERMISSION: permission){
                if (ActivityCompat.checkSelfPermission(context, PERMISSION) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }

        return true;
    }
}