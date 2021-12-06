package com.propositive.tradewaale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.GetChars;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.propositive.tradewaale.advisory.tabs.derivative.extendView.DerivativeCallHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class niftydata extends AppCompatActivity {

    private static final String TAG = "demo calss";

    public TextView nifty;

    String token, tickdata;

    String ndata, nifty_Close;

    Date d = new Date();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niftydata);

        nifty = findViewById(R.id.textView9);

        LocalDate date = LocalDate.parse(getToday());
        LocalDate date1 = date.minusDays(2);

        String date2 = String.valueOf(date1);

        String date3 = date2.substring(2, 10);

        String date4 = date3.replace("-", "");

        Log.e(TAG, "onCreate: date1 " + date1 );
        Log.e(TAG, "onCreate: date2 " + date2 );
        Log.e(TAG, "onCreate: date3 " + date3 );
        Log.e(TAG, "onCreate: date4 " + date4 );

        Log.e(TAG, "onCreate: date check: " + getToday() + " " + date4);

        //Toast.makeText(getApplicationContext(), "date: " + getToday() + " " + date1, Toast.LENGTH_LONG).show();

        //socketData();

//        Handler mHandler = new Handler();
//        Timer mTimer = new Timer();
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        nifty.setText(tickdata);
//                    }
//                });
//            }
//        }, 10, 10);

    }
    /**
    public String socketData() {
        Log.e(TAG, "socketData: request called");
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "username=tdws141&password=propositive@141&grant_type=password");
        Log.e(TAG, "socketData: request created");

        Request request = new Request.Builder()
                .url("https://auth.truedata.in/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Response response = client.newCall(request).execute();

            String res = response.body().string();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    private String TickData() {

        Log.e(TAG, "TickData: token: " + token);

        if (!token.isEmpty()){

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://history.truedata.in/getticks?symbol=BANKNIFTY-I&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            try {
                Response response = client.newCall(request).execute();

                String s = response.body().string();

                ObjectMapper mapper = new ObjectMapper();
                try {
                    Map map = mapper.readValue(s, Map.class);
                    List<List> recordsList = (List<List>) map.get("Records");
                    Collections.reverse(recordsList);

                    Log.e(TAG, "TickData: " + recordsList.get(0).get(1));
                    String bn = String.valueOf(recordsList.get(0).get(1));

                    nifty.setText(bn);
                    refresh(1);

                    Log.e(TAG, "TickData: set text called" );

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            socketData();
            TickData();
        }


        return tickdata;
    }

    private void refresh(int i) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TickData();
            }
        };
        handler.postDelayed(runnable, i);
    }
     **/

    @Override
    protected void onStart() {
        super.onStart();

//       NiftyAutoData niftyAutoData = new NiftyAutoData();
//       niftyAutoData.execute();

        //TickData();

//        Handler mHandler = new Handler();
//        Timer mTimer = new Timer();
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        }, 10, 10);
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

//    public class NiftyAutoData extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//
//            if (ndata != null) {
//                String new_val = ndata.replace(",", "");
//
////                float val = Float.parseFloat(new_val) - Float.parseFloat(nifty_Close);
////
////                float res = val / Float.parseFloat(nifty_Close);
////
////                float persent = res * 100;
//
////                Log.e(TAG, "onPostExecute: nifty val " + val);
////                Log.e(TAG, "onPostExecute: nifty res " + res);
////                Log.e(TAG, "onPostExecute: nifty present " + persent);
//
//                nifty.setText(ndata);
////                StoreNiftyData(niftydata);
////                niftyDayCalc.setText(df.format(val));
////                niftyPersent.setText("( " + df.format(persent) + "% )");
////
////                if (val > 0) {
////                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
////                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
////                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
////                } else {
////                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
////                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
////                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
////                }
//            } else {
//                Toast.makeText(getApplicationContext(), "else executed!...", Toast.LENGTH_SHORT).show();
//
////                SharedPreferences sharedPreferences = getSharedPreferences("nifty_data", MODE_PRIVATE);
////
////                String share_nifty_data = sharedPreferences.getString("close_nifty_data", "");
////
////                String new_val = share_nifty_data.replace(",", "");
////
////                float val = Float.parseFloat(new_val) - Float.parseFloat(share_nifty_data);
////
////                float res = val / Float.parseFloat(nifty_Close);
////
////                float persent = res * 100;
////
////                Log.e(TAG, "onPostExecute: nifty val " + val);
////                Log.e(TAG, "onPostExecute: banknifty res " + res);
////                Log.e(TAG, "onPostExecute: banknifty present " + persent);
////
////                nifty.setText(share_nifty_data);
////                niftyDayCalc.setText(df.format(val));
////                niftyPersent.setText("( " + df.format(persent) + "% )");
////
////                if (val > 0) {
////                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
////                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
////                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
////                } else {
////                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
////                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
////                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
////                }
//            }
//        }
//
//        @Override
//        protected void onCancelled(Void unused) {
//            super.onCancelled(unused);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                String url = "https://www.google.com/search?q=nifty+auto+live+data&rlz=1C1CHBF_enIN981IN981&oq=nifty+auto+live+data&aqs=chrome..69i57.6713j0j1&sourceid=chrome&ie=UTF-8";
//                Document doc = Jsoup.connect(url).get();
//
//                Elements webData1 = doc.select("div.PZPZlf");
//                Elements webData2 = doc.select("tr.PZPZlf");
//
//                ndata = webData1.select("div.PZPZlf")
//                        .select("span")
//                        .text().substring(0, 9);
//
//                nifty_Close = webData2.select("tr.PZPZlf")
//                        .select("td")
//                        .text().substring(26, 34);
//
//                Log.e(TAG, "doInBackground: nifty 50 data:" + ndata);
//                Log.e(TAG, "doInBackground: nifty 50 prev close data:" + nifty_Close);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//    }
}