package com.propositive.tradewaale.Indices.tab.IndianIndices;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.test;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.http.GET;

public class IndianIndicesFragment extends Fragment {

    Context context;

    private static final String TAG = "Indian Indices";

    DecimalFormat df = new DecimalFormat("0.00");

    TickData tickData;
    Handler handler;

    String token;

    TextView banknifty, bankniftyCalc, bankniftyPers;
    TextView niftyAuto, niftyAutoCalc, niftyAutoPers;
    TextView niftyFinServ, niftyFinServCalc, niftyFinServPers;
    TextView niftyfmcg, niftyfmcgCalc, niftyfmcgPers;
    TextView niftyit, niftyitCalc, niftyitPers;
    TextView niftymedia, niftymediaCalc, niftymediaPers;
    TextView niftymetal, niftymetalCalc, niftymetalPers;
    TextView niftypharma, niftypharmaCalc, niftypharmaPers;
    TextView niftypsu, niftypsuCalc, niftypsuPers;
    TextView niftyrealty, niftyrealtyCalc, niftyrealtyPers;

    String BankNiftyPrevClose, NiftyAutoPrevClose, NiftyFinservPrevClose, NiftyFmcgPrevClose, NiftyItPrevClose, NiftyMediaPrevClose, NiftyMetalPrevClose, NiftyPharmaPrevClose, NiftyPsuPrevClose, NiftyRealtyPrevClose;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_indian_indices, container, false);

        tickData = new TickData();
        handler = new Handler();

        banknifty = root.findViewById(R.id.r1c2);
        bankniftyCalc = root.findViewById(R.id.r1c3);
        bankniftyPers = root.findViewById(R.id.r1c4);

        niftyAuto = root.findViewById(R.id.r2c2);
        niftyAutoCalc = root.findViewById(R.id.r2c4);
        niftyAutoPers = root.findViewById(R.id.r2c3);

        niftyFinServ = root.findViewById(R.id.r3c2);
        niftyFinServCalc = root.findViewById(R.id.r3c4);
        niftyFinServPers = root.findViewById(R.id.r3c3);

        niftyfmcg = root.findViewById(R.id.r4c2);
        niftyfmcgCalc = root.findViewById(R.id.r4c4);
        niftyfmcgPers = root.findViewById(R.id.r4c3);

        niftyit = root.findViewById(R.id.r5c2);
        niftyitCalc = root.findViewById(R.id.r5c4);
        niftyitPers = root.findViewById(R.id.r5c3);

        niftymedia = root.findViewById(R.id.r6c2);
        niftymediaCalc = root.findViewById(R.id.r6c4);
        niftymediaPers = root.findViewById(R.id.r6c3);

        niftymetal = root.findViewById(R.id.r7c2);
        niftymetalCalc = root.findViewById(R.id.r7c4);
        niftymetalPers = root.findViewById(R.id.r7c3);

        niftypharma = root.findViewById(R.id.r8c2);
        niftypharmaCalc = root.findViewById(R.id.r8c4);
        niftypharmaPers = root.findViewById(R.id.r8c3);

        niftypsu = root.findViewById(R.id.r9c2);
        niftypsuCalc = root.findViewById(R.id.r9c4);
        niftypsuPers = root.findViewById(R.id.r9c3);

        niftyrealty = root.findViewById(R.id.r0c2);
        niftyrealtyCalc = root.findViewById(R.id.r0c4);
        niftyrealtyPers = root.findViewById(R.id.r0c3);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

        socketData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(tickData).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Thread(tickData).start();
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

            try {
                JSONObject call_history = new JSONObject(res);

                token = call_history.getString("access_token");

                SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences("indian_indices_token", Context.MODE_PRIVATE).edit();
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

    class TickData implements Runnable {
        @Override
        public void run() {

            if (!token.isEmpty()) {

                //TODO: NiftyBank live data

                OkHttpClient client0 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request0 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=BANKNIFTY-I&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client0.newCall(request0).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();

                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        Log.e(TAG, "TickData NIFTY BANK: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));


                        //refresh(1000);

                        float val = Float.parseFloat(bn) - BankNiftyPervClose();
                        float res = val / BankNiftyPervClose();
                        float persent = res * 100;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                banknifty.setText(bn);
                                bankniftyPers.setText(df.format(val));
                                bankniftyCalc.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    bankniftyPers.setTextColor(getResources().getColor(R.color.green));
                                    bankniftyCalc.setBackgroundResource(R.drawable.gain_indices);
                                } else {
                                    bankniftyPers.setTextColor(getResources().getColor(R.color.red));
                                    bankniftyCalc.setBackgroundResource(R.drawable.loss_indices);
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

                //TODO: Nifty Auto live data
                OkHttpClient client1 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request1 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY AUTO&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client1.newCall(request1).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        Log.e(TAG, "TickData NIFTY AUTO: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - NiftyAutoPervClose();
                        float res = val / NiftyAutoPervClose();
                        float persent = res * 100;


                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyAuto.setText(bn);

                                niftyAutoCalc.setText(df.format(val));
                                niftyAutoPers.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    niftyAutoPers.setBackgroundResource(R.drawable.gain_indices);
                                    niftyAutoCalc.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    niftyAutoPers.setBackgroundResource(R.drawable.loss_indices);
                                    niftyAutoCalc.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1);

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

                //TODO: Nifty FINSERV live data
                OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request2 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY FIN SERVICE&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
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

                        Log.e(TAG, "TickData NIFTY FINSERV: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - NiftyFinServPervClose();
                        float res = val / NiftyFinServPervClose();
                        float persent = res * 100;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyFinServ.setText(bn);

                                niftyFinServCalc.setText(df.format(val));
                                niftyFinServPers.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    niftyFinServPers.setBackgroundResource(R.drawable.gain_indices);
                                    niftyFinServCalc.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    niftyFinServPers.setBackgroundResource(R.drawable.loss_indices);
                                    niftyFinServCalc.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1);

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

                //TODO: Nifty FMCG live data
                OkHttpClient client3 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request3 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY FMCG&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client3.newCall(request3).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        Log.e(TAG, "TickData NIFTY FMCG: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - NiftyFmcgPervClose();
                        float res = val / NiftyFmcgPervClose();
                        float persent = res * 100;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyfmcg.setText(bn);

                                niftyfmcgCalc.setText(df.format(val));
                                niftyfmcgPers.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    niftyfmcgPers.setBackgroundResource(R.drawable.gain_indices);
                                    niftyfmcgCalc.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    niftyfmcgPers.setBackgroundResource(R.drawable.loss_indices);
                                    niftyfmcgCalc.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1);

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

                //TODO: Nifty IT live data
                OkHttpClient client4 = new OkHttpClient().newBuilder().build();
                okhttp3.Request request4 = new okhttp3.Request.Builder()
                        .url("https://history.truedata.in/getticks?symbol=NIFTY FMCG&from=" + getToday() + "T09:15:00&to=" + getToday() + "T15:30:00&response=json&bidask=1")
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                try {
                    okhttp3.Response response = client4.newCall(request4).execute();

                    String s = response.body().string();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Map map = mapper.readValue(s, Map.class);
                        List<List> recordsList = (List<List>) map.get("Records");
                        Collections.reverse(recordsList);

                        Log.e(TAG, "TickData NIFTY FMCG: " + recordsList.get(0).get(1));
                        String bn = String.valueOf(recordsList.get(0).get(1));

                        float val = Float.parseFloat(bn) - NiftyItPervClose();
                        float res = val / NiftyItPervClose();
                        float persent = res * 100;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                niftyit.setText(bn);

                                niftyitCalc.setText(df.format(val));
                                niftyitPers.setText("( " + df.format(persent) + "% )");

                                if (val > 0) {
                                    niftyitPers.setBackgroundResource(R.drawable.gain_indices);
                                    niftyitCalc.setTextColor(getResources().getColor(R.color.green));
                                } else {
                                    niftyitPers.setBackgroundResource(R.drawable.loss_indices);
                                    niftyitCalc.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        //refresh(1);

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
            } else {
                socketData();
                new Thread(tickData).start();
            }
        }
    }

    public float BankNiftyPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+bank+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvLg6-WbQh-GnvJcSPtpWUAaHwAQKA%3A1638781130095&ei=ytCtYYj7BPOVseMPxp2NqAY&ved=0ahUKEwiIiP645870AhXzSmwGHcZOA2UQ4dUDCA8&uact=5&oq=nifty+bank+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECcyBggAEAgQHjIGCAAQCBAeMgYIABAIEB4yBggAEAgQHjoHCCMQsAMQJzoHCAAQRxCwAzoKCCMQsAIQJxCdAjoECAAQDToICAAQCBAHEB46CAgAEA0QBRAeOggIABAIEA0QHjoHCCMQsAIQJ0oFCDwSATJKBAhBGABKBAhGGABQuQpY-hlgvRxoAnACeAGAAc4CiAH8BZIBBzIuMi4wLjGYAQCgAQHIAQnAAQE&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            BankNiftyPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: banknifty 50 prev close data:" + BankNiftyPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(BankNiftyPrevClose);
    }

    public float NiftyAutoPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+auto+live+data&rlz=1C1CHBF_enIN981IN981&oq=nifty+auto+live+data&aqs=chrome..69i57.6713j0j1&sourceid=chrome&ie=UTF-8";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyAutoPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty auto prev close data:" + NiftyAutoPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyAutoPrevClose);
    }

    public float NiftyFinServPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+fin+service+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvLjsoXdLC81Zk2-sMxJ_vIuxeUCjg%3A1638782323036&ei=c9WtYfbfAaeQseMP1uWxeA&ved=0ahUKEwi25enx6870AhUnSGwGHdZyDA8Q4dUDCA8&uact=5&oq=nifty+fin+service+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECc6BwgjELADECc6BwgAEEcQsAM6DAgjECcQnQIQRhD6AToGCAAQBxAeOgUIABCABDoHCCMQsAIQJ0oFCDwSATFKBAhBGABKBAhGGABQ9wZY7SJg7SRoAXACeACAAZcDiAG7FJIBCTAuOS4zLjAuMZgBAKABAcgBCcABAQ&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyFinservPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty fin serv prev close data:" + NiftyFinservPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyFinservPrevClose);
    }

    public float NiftyFmcgPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+fmcg+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvKgEOu_XgF2LZuj3OYOG7EpbWtlgg%3A1638784094655&ei=XtytYau9J6LDmAXkzpA4&ved=0ahUKEwir5My-8s70AhWiIaYKHWQnBAcQ4dUDCA8&uact=5&oq=nifty+fmcg+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEM0COgcIIxCwAxAnOgcIABBHELADSgUIPBIBMUoECEEYAEoECEYYAFCZDFiiEGDVJGgBcAJ4AIABowGIAf4EkgEDMC40mAEAoAEByAEJwAEB&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyFmcgPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty fmcg prev close data:" + NiftyFmcgPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyFmcgPrevClose);
    }

    public float NiftyItPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+it+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvLQUPd_NeNaaEmeI3nnk2APWRX1_g%3A1638784216835&ei=2NytYdCuMpCxmAXB6p6IAQ&ved=0ahUKEwjQ-u348s70AhWQGKYKHUG1BxEQ4dUDCA8&uact=5&oq=nifty+it+live+data&gs_lcp=Cgdnd3Mtd2l6EAM6BwgAEEcQsANKBQg8EgEySgQIQRgASgQIRhgAUNISWN0XYP4caAJwAngBgAHFBYgBkwiSAQcwLjIuNi0xmAEAoAEByAEIwAEB&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyItPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty it serv prev close data:" + NiftyItPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyItPrevClose);
    }

    public float NiftyMediaPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+media+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvLcrrvv6QWHYrJXLwnQqzoDVdfUJw%3A1638784255444&ei=_9ytYdHPGrC2mAXX8rPgAQ&ved=0ahUKEwiRy6KL8870AhUwG6YKHVf5DBwQ4dUDCA8&uact=5&oq=nifty+media+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEM0COgcIABBHELADSgUIPBIBMUoECEEYAEoECEYYAFDFB1jgDWD8E2gBcAJ4AYABngSIAcYKkgEJMC4yLjIuNS0xmAEAoAEByAEIwAEB&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyMediaPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty media prev close data:" + NiftyMediaPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyMediaPrevClose);
    }

    public float NiftyMetalPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+metal+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvIldnGqyF5L-ftkZHNM6R-lgwJIwQ%3A1638784515535&ei=A96tYf2NIOWRseMP2d2JyAk&ved=0ahUKEwi9m6WH9M70AhXlSGwGHdluApkQ4dUDCA8&uact=5&oq=nifty+metal+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEM0COgcIABBHELADSgUIPBIBM0oECEEYAEoECEYYAFCYE1jEFmDAL2gDcAJ4AIABqwGIAZUFkgEDMC40mAEAoAEByAEIwAEB&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyMetalPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty metal prev close data:" + NiftyMetalPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyMetalPrevClose);
    }

    public float NiftyPharmaPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+pharma+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvJCK4dIDYh7ajJdjDtkwR4GS_FbPA%3A1638784600505&ei=WN6tYa6cHtedseMPgMud2AQ&ved=0ahUKEwiuqOev9M70AhXXTmwGHYBlB0sQ4dUDCA8&uact=5&oq=nifty+pharma+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEM0CMgUIABDNAjIFCAAQzQIyBQgAEM0CMgUIABDNAjoHCAAQRxCwAzoKCCMQsAIQJxCdAjoECAAQDToICAAQDRAFEB46CAgAEAgQDRAeSgUIPBIBMkoECEEYAEoECEYYAFDIDFjtGmCjHWgCcAJ4AYABgwWIAYMNkgEHMC42LjUtMZgBAKABAcgBCMABAQ&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyPharmaPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty pharma prev close data:" + NiftyPharmaPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyPharmaPrevClose);
    }

    public float NiftyPsuPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+psu+bank+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvJXdFP8raiHuy6pXixyZFJNXT0pCw%3A1638784654080&ei=jt6tYayhBJyaseMP8e-byAM&ved=0ahUKEwisoK3J9M70AhUcTWwGHfH3BjkQ4dUDCA8&uact=5&oq=nifty+psu+bank+live+data&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEM0CMgUIABDNAjoHCAAQRxCwAzoGCAAQBxAeSgUIPBIBMUoECEEYAEoECEYYAFC6CliIIGDHImgBcAJ4AIAB3AGIAcMLkgEFMC42LjKYAQCgAQHIAQjAAQE&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyPsuPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty psu prev close data:" + NiftyPsuPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyPsuPrevClose);
    }

    public float NiftyRealtyPervClose() {
        try {
            String url = "https://www.google.com/search?q=nifty+realty+live+data&rlz=1C1CHBF_enIN981IN981&sxsrf=AOaemvLOZs2dSDaRfoQk_OScw3OrK80Vrw%3A1638784871519&ei=Z9-tYeOJH6Wn2roPu7W5iAY&ved=0ahUKEwij2YSx9c70AhWlk1YBHbtaDmEQ4dUDCA8&uact=5&oq=nifty+realty+live+data&gs_lcp=Cgdnd3Mtd2l6EAM6BwgAEEcQsAM6BQgAEM0CSgUIPBIBMUoECEEYAEoECEYYAFCkGFiAJGDdLmgBcAJ4AYAB8ASIAY0MkgEJMC40LjEuNS0xmAEAoAEByAEIwAEB&sclient=gws-wiz";
            Document doc = Jsoup.connect(url).get();

            Elements webData = doc.select("tr.PZPZlf");

            String PrevClose = webData.select("tr.PZPZlf")
                    .select("td")
                    .text().substring(26, 34);

            NiftyRealtyPrevClose = PrevClose.replace(",", "");

            Log.e(TAG, "doInBackground: nifty realty prev close data:" + NiftyRealtyPrevClose);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(NiftyRealtyPrevClose);
    }

    private void refresh(int i) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tickData.run();
            }
        };
        handler.postDelayed(runnable, i);
    }

    /**
     * public class NiftyAutoData extends AsyncTask<Void, Void, Void> {
     *
     * @Override protected void onPreExecute() {
     * super.onPreExecute();
     * }
     * @Override protected void onPostExecute(Void unused) {
     * super.onPostExecute(unused);
     * <p>
     * if (NiftyAuto != null) {
     * String new_val = NiftyAuto.replace(",", "");
     * String npc = NiftyPrevClose.replace(",", "");
     * <p>
     * float val = Float.parseFloat(new_val) - Float.parseFloat(npc);
     * <p>
     * float res = val / Float.parseFloat(npc);
     * <p>
     * float persent = res * 100;
     * <p>
     * Log.e(TAG, "onPostExecute: nifty val " + val);
     * Log.e(TAG, "onPostExecute: nifty res " + res);
     * Log.e(TAG, "onPostExecute: nifty present " + persent);
     * <p>
     * niftyAuto.setText(NiftyAuto);
     * //StoreNiftyData(niftydata);
     * niftyAutoCalc.setText(df.format(val));
     * niftyAutoPers.setText("( " + df.format(persent) + "% )");
     * <p>
     * if (val > 0) {
     * niftyAutoPers.setBackgroundResource(R.drawable.gain_indices);
     * niftyAutoCalc.setTextColor(getResources().getColor(R.color.green));
     * niftyAutoPers.setTextColor(getResources().getColor(R.color.white));
     * } else {
     * niftyAutoPers.setBackgroundResource(R.drawable.gain_indices);
     * niftyAutoCalc.setTextColor(getResources().getColor(R.color.red));
     * niftyAutoPers.setTextColor(getResources().getColor(R.color.white));
     * }
     * <p>
     * refresh(1);
     * <p>
     * } else {
     * Toast.makeText(getContext(), "else executed!...", Toast.LENGTH_SHORT).show();
     * Log.e(TAG, "onPostExecute: link expaired!..." );
     * //                SharedPreferences sharedPreferences = getSharedPreferences("nifty_data", MODE_PRIVATE);
     * //
     * //                String share_nifty_data = sharedPreferences.getString("close_nifty_data", "");
     * //
     * //                String new_val = share_nifty_data.replace(",", "");
     * //
     * //                float val = Float.parseFloat(new_val) - Float.parseFloat(share_nifty_data);
     * //
     * //                float res = val / Float.parseFloat(nifty_Close);
     * //
     * //                float persent = res * 100;
     * //
     * //                Log.e(TAG, "onPostExecute: nifty val " + val);
     * //                Log.e(TAG, "onPostExecute: banknifty res " + res);
     * //                Log.e(TAG, "onPostExecute: banknifty present " + persent);
     * //
     * //                nifty.setText(share_nifty_data);
     * //                niftyDayCalc.setText(df.format(val));
     * //                niftyPersent.setText("( " + df.format(persent) + "% )");
     * //
     * //                if (val > 0) {
     * //                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_up);
     * //                    niftyDayCalc.setTextColor(getResources().getColor(R.color.green));
     * //                    niftyPersent.setTextColor(getResources().getColor(R.color.green));
     * //                } else {
     * //                    NiftyTrend.setImageResource(R.drawable.ic_arrow_drop_down);
     * //                    niftyDayCalc.setTextColor(getResources().getColor(R.color.red));
     * //                    niftyPersent.setTextColor(getResources().getColor(R.color.red));
     * //                }
     * }
     * }
     * @Override protected void onCancelled(Void unused) {
     * super.onCancelled(unused);
     * }
     * @Override protected Void doInBackground(Void... voids) {
     * try {
     * String url = "https://www.google.com/search?q=nifty+auto+live+data&rlz=1C1CHBF_enIN981IN981&oq=nifty+auto+live+data&aqs=chrome..69i57.6713j0j1&sourceid=chrome&ie=UTF-8";
     * Document doc = Jsoup.connect(url).get();
     * <p>
     * Elements webData1 = doc.select("div.PZPZlf");
     * Elements webData2 = doc.select("tr.PZPZlf");
     * <p>
     * NiftyAuto = webData1.select("div.PZPZlf")
     * .select("span")
     * .text().substring(0, 9);
     * <p>
     * NiftyPrevClose = webData2.select("tr.PZPZlf")
     * .select("td")
     * .text().substring(26, 34);
     * <p>
     * Log.e(TAG, "doInBackground: nifty 50 data:" + NiftyAuto);
     * Log.e(TAG, "doInBackground: nifty 50 prev close data:" + NiftyPrevClose);
     * <p>
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * return null;
     * }
     * <p>
     * }
     **/


    private String getToday() {
        return new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
    }

}