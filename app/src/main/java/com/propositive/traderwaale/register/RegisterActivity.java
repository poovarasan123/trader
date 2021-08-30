package com.propositive.traderwaale.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.propositive.traderwaale.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.159.211/trader/api/register.php";

    TextInputEditText name, mail, mobile, birth, pass, cpass;
    TextInputLayout Lname, Lmail, Lmob, Lbirth, Lpass, Lcpass;

    Button submit;

    MaterialDatePicker.Builder materialDateBuilder;
    MaterialDatePicker materialDatePicker;

    private static final String TAG = "registerActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.username);
        mail = findViewById(R.id.mail_ID);
        mobile = findViewById(R.id.mobile);
        birth = findViewById(R.id.dob);
        pass = findViewById(R.id.password);
        cpass = findViewById(R.id.cpassword);

        Lname = findViewById(R.id.username_layout);
        Lmail = findViewById(R.id.mail_layout);
        Lmob = findViewById(R.id.mobile_layout);
        Lbirth  =findViewById(R.id.dob_layout);
        Lpass = findViewById(R.id.pass_layout);
        Lcpass = findViewById(R.id.cpass_layout);

        submit = findViewById(R.id.submitbtn);


        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setSelection(today);
        materialDatePicker = materialDateBuilder.build();

        birth.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                birth.setText(materialDatePicker.getHeaderText());
            });
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator();
            }
        });

    }

    private void validator() {

        String Name = name.getText().toString();
        String Mail = mail.getText().toString();
        String Mobile = mobile.getText().toString();
        String BirthDay = birth.getText().toString();
        String Pass = pass.getText().toString();
        String Cpass = cpass.getText().toString();

        if (Name.isEmpty()){
            name.setError("Name required!...");
            name.requestFocus();
        }else if (Mail.isEmpty() ){
            mail.setError("Mail ID required!...");
            mail.requestFocus();
        }else if (Mobile.length() != 10){
            mobile.setError("Mobile No required!...");
            mobile.requestFocus();
        }else if (BirthDay.isEmpty()){
            birth.setError("DOB is required!...");
            birth.requestFocus();
        }else if (Pass.isEmpty()){
            pass.setError("Password is required!...");
            pass.requestFocus();
        }else if (Cpass.isEmpty()){
            cpass.setError("Enter password again!...");
            cpass.requestFocus();
        }else if (Pass.equals(Cpass)){

            // TODO: conect db here
            //Toast.makeText(RegisterActivity.this, "all fileds ready...", Toast.LENGTH_SHORT).show();

            addUser(Name,Mail,Mobile,BirthDay,Pass);

        }else{

            // TODO: error message here
            Toast.makeText(RegisterActivity.this, "feilds missing!...", Toast.LENGTH_SHORT).show();

        }
    }

    private void addUser(String uname, String umail, String umobile, String ubirthDay, String upass) {

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                name.setText("");
                mail.setText("");
                mobile.setText("");
                birth.setText("");
                pass.setText("");
                cpass.setText("");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                name.setText("");
                mail.setText("");
                mobile.setText("");
                birth.setText("");
                pass.setText("");
                cpass.setText("");
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error", "onErrorResponse: " + error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> map = new HashMap<String, String>();
                    map.put("username",uname);
                    map.put("mail_id",umail);
                    map.put("mobile_no",umobile);
                    map.put("dob",ubirthDay);
                    map.put("pass",upass);
                    return map;
                }
            };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}