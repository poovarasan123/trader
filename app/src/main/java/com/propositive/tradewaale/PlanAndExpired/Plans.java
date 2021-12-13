package com.propositive.tradewaale.PlanAndExpired;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.equity.EquityModel;
import com.propositive.tradewaale.home.newsAdapter;
import com.propositive.tradewaale.PlanAndExpired.PlanAdapter;
import com.propositive.tradewaale.PlanAndExpired.PlanModel;
import com.propositive.tradewaale.PlanAndExpired.apiController;
import com.propositive.tradewaale.login.LoginActivity;
import com.propositive.tradewaale.test;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;

public class Plans extends AppCompatActivity  implements PaymentResultListener {

    private static final String TAG = "Plan Activity";

    Checkout checkout;

    String userid, userfname, userlname, usermail, userphone;

    Button plan2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        Checkout.preload(getApplicationContext());

        plan2 = findViewById(R.id.get_plan2);

        Log.e(TAG, "onCreate: date: " + getMonth("2021-12-08") );

        SharedPreferences shared = getSharedPreferences("user_cred", MODE_PRIVATE);
        userid = shared.getString("userId", "");
        userfname = shared.getString("userfname", "");
        userlname = shared.getString("userlname", "");
        usermail = shared.getString("usermail", "");
        userphone = shared.getString("usernumber", "");

        Log.e(TAG, "onCreate: id" + userid );
        Log.e(TAG, "onCreate: fname" + userfname );
        Log.e(TAG, "onCreate: lname" + userlname );
        Log.e(TAG, "onCreate: mail" + usermail );
        Log.e(TAG, "onCreate: phone" + userphone );

        plan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay(usermail, userphone);
            }
        });

    }

    private void pay(String usermail, String userphone) {
        checkout = new Checkout();

        float netAmout = Math.round(3599 * 100);

        checkout.setKeyID("rzp_test_7Btns3DGHiIpYd");//rzp_live_wo7p2a2lOyiTaQ, rzp_test_7Btns3DGHiIpYd
        checkout.setImage(R.drawable.propositive);


        final Activity activity = this;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "Propositive");
            jsonObject.put("description", "Selected Plan: Perfect Trader");
            //jsonObject.put("order_id", "order_android");
            jsonObject.put("theme.color", "#0093DD");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", netAmout);
            jsonObject.put("country", "+91");
            jsonObject.put("prefill.contact", userphone);
            jsonObject.put("prefill.email", usermail);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            jsonObject.put("retry", retryObj);

            Log.e(TAG, "pay: phone" + userphone );
            Log.e(TAG, "pay: mail" + usermail );

            checkout.open(activity, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "pay: error: " + e );
        }
    }

    public void go_to_payment(View view) {
        startActivity(new Intent(this, Payment.class));
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Confirm exit")
                .setMessage("Do you really want to exit the app?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateSession(usermail);
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
                params.put("email", usermail);
                return params;
            }
        };
        MySingleton.getMySingleton(Plans.this).addToRequestQue(stringRequest);
    }


    @Override
    public void onPaymentSuccess(String s) {

        String mess = "Welcome to Trade Waale \n\n You have successfully done a subscribe Perfect Trader of Rs. 3899.\n Your Payment id: " + s + " \n\n Regards \n Support Team \n Trade Waale. ";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.PAYMENT_INSERT, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server:---> " + response);

                if (response.equals("Payment successful")) {
                    Mail(usermail, "Hi, Welcome to Trade Waale", mess);
                    startActivity(new Intent(Plans.this, test.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Something went wrong
                Log.d(TAG, "onResponse: payment error from server:---> " + error.getMessage());
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userid);
                params.put("plan", "4");
                params.put("plan_name", "Perfect Trader");
                params.put("payment_id", s);
                params.put("reg_date", getDate());
                params.put("end_date", getMonth(getDate()));
                return params;
            }
        };
        MySingleton.getMySingleton(Plans.this).addToRequestQue(stringRequest);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "error: " + s, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPaymentError: error: " + i + " string: " + s);
    }

    private String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getMonth(String date){
        LocalDate mDate = LocalDate.parse(date);
        LocalDate dMonth = mDate.plusMonths(1);
        return String.valueOf(dMonth);

    }

    public void Mail(String to, String subject, String message) {

        String mail = "tradewaale@gmail.com";
        String pass = "Proluck@2797";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        });

        try {
            Message message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(mail));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message1.setSubject(subject);
            message1.setText(message);

            new SendMail().execute(message1);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private class SendMail extends AsyncTask<Message, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(Plans.this, "Please Wait", "Sending Mail...", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: mail error: " + e.getMessage());
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressDialog.cancel();

            if (s.equals("Success")) {
                //startActivity(new Intent(Plans.this, test.class));
                finish();
            } else {
                Toast.makeText(Plans.this, "Mail send failed!...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Plans.this, LoginActivity.class));
                finish();
            }

        }


    }
}