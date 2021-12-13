package com.propositive.tradewaale.PlanAndExpired;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.SendMail.Mail;
import com.propositive.tradewaale.test;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
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

public class Payment extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "payment activity";
    LinearLayout plan1;
    LinearLayout plan2;

    RadioButton st1;
    RadioButton st2;

    TextView proc, price;

    Checkout checkout;

    int planPrice = 0;
    String planName;

    String userid, userfname, userlname, usermail, userphone;

    LocalDate dMonth;

    Mail sendMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = Payment.this.getResources().getDrawable(R.drawable.payment_gradient);
        getWindow().setBackgroundDrawable(background);

        setContentView(R.layout.activity_payment);

        Checkout.preload(getApplicationContext());

        plan1 = findViewById(R.id.smart_trader1);
        plan2 = findViewById(R.id.smart_trader2);

        st1 = findViewById(R.id.radioButton1);
        st2 = findViewById(R.id.radioButton2);

        proc = findViewById(R.id.proceed_btn);
        price = findViewById(R.id.plan_price);

        SharedPreferences shared = getSharedPreferences("user_cred", MODE_PRIVATE);
        userid = shared.getString("userId", "");
        userfname = shared.getString("userfname", "");
        userlname = shared.getString("userlname", "");
        usermail = shared.getString("usermail", "");
        userphone = shared.getString("usernumber", "");

        plan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st1.setChecked(true);
                st2.setChecked(false);
                proc.setBackgroundResource(R.drawable.payment_gradient);
                proc.setTextColor(getResources().getColor(R.color.white));
                planPrice = 999;
                planName = "Smart Trader - I";
            }
        });

        plan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st2.setChecked(true);
                st1.setChecked(false);
                proc.setBackgroundResource(R.drawable.payment_gradient);
                proc.setTextColor(getResources().getColor(R.color.white));
                planPrice = 2899;
                planName = "Smart Trader - II";


            }
        });

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (planPrice != 0) {
                    try {
                        pay();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please select plan!...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void go_back(View view) {
        finish();
    }


    private void pay() throws UnsupportedEncodingException {

        checkout = new Checkout();

        float netAmout = Math.round(planPrice * 100);

        checkout.setKeyID("rzp_test_7Btns3DGHiIpYd");//rzp_live_wo7p2a2lOyiTaQ, rzp_test_7Btns3DGHiIpYd
        checkout.setImage(R.drawable.propositive);

        JSONObject jsonObject = new JSONObject();
        final Activity activity = this;

        try {
            jsonObject.put("name", "Propositive");
            jsonObject.put("description", "Selected Plan: " + planName);
            //jsonObject.put("order_id", "order_android");
            jsonObject.put("theme.color", "#0093DD");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", netAmout);
            jsonObject.put("prefill.contact", userphone);
            jsonObject.put("prefill.email", usermail);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            jsonObject.put("retry", retryObj);

            checkout.open(activity, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "pay: error: " + e);
        }

    }


    @Override
    public void onPaymentSuccess(String s) {

        String mess = "Welcome to Trade Waale \n\n You have successfully done a subscribe " + planName + " of Rs." + planPrice + ".\n Your Payment id: " + s + " \n\n Regards \n Support Team \n Trade Waale. ";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.propositive.tradewaale.Constants.PAYMENT_INSERT, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response from server:---> " + response);

                if (response.equals("Payment successful")) {
                    Mail(usermail, "Hi, Welcome to Trade Waale", mess);
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
                if (planName.equals("Smart Trader - I")) {
                    params.put("plan", "2");
                } else {
                    params.put("plan", "3");
                }
                params.put("plan_name", planName);
                params.put("payment_id", s);
                params.put("reg_date", getDate());
                params.put("end_date", getMonth(getDate()));
                return params;
            }
        };
        MySingleton.getMySingleton(Payment.this).addToRequestQue(stringRequest);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "error: " + s, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPaymentError: error: " + i + " string: " + s);
    }

    private String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getMonth(String date) {
        LocalDate mDate = LocalDate.parse(date);
        if (planName.equals("Smart Trader - I")) {
            dMonth = mDate.plusMonths(1);
        } else {
            dMonth = mDate.plusMonths(3);
        }
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
            progressDialog = ProgressDialog.show(Payment.this, "Please Wait", "Sending Mail...", true, false);
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
            progressDialog.dismiss();

            if (s.equals("Success")) {
                startActivity(new Intent(Payment.this, test.class));
                finish();
            } else {
                Toast.makeText(Payment.this, "mail send failed!...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Payment.this, test.class));
                finish();
            }

        }


    }

}