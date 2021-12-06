package com.propositive.tradewaale.advisory.tabs.derivative.extendView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.MySingleton;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.equity.extendView.EquityCallHistoryAdapter;
import com.propositive.tradewaale.advisory.tabs.equity.extendView.EquityCallHistoryModel;
import com.propositive.tradewaale.advisory.tabs.equity.extendView.EquityExtendViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DerivativeExtendViewActivity extends AppCompatActivity {

    TextView name, rate, stock, tcTxet, apText, recoValue, targetStart, targetEnd;
    TextView opc, term, dateDetails, targetDetails, date, time;
    TextView opcl, name2, reduceValue, tcText2, tcValue2, date2, time2;
    TextView historyTitleTop;

    ImageView status_indicator;

    View tag;

    int targetDay;

    int showMore = 0;

    RecyclerView recyclerView;
    LinearLayout show_more_layout, noCallHistory;

    List<DerivativeCallHistoryModel> datalist = new ArrayList<>();
    DerivativeCallHistoryAdapter adapter;


    private static final String TAG = "equity extend activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derivative_extend_view);

        recyclerView = findViewById(R.id.call_history);
        //show_more_layout = findViewById(R.id.show_more);

        Intent intent = getIntent();

        String rid = intent.getStringExtra("rid");
        String namei = intent.getStringExtra("name");
        String ratei = intent.getStringExtra("rateStatus");
        String stocki = intent.getStringExtra("stockStatus");
        String recopricei = intent.getStringExtra("recoValue");
        String targetopeni = intent.getStringExtra("tcValueStart");
        String targetclosei = intent.getStringExtra("tcValueEnd");
        String termi = intent.getStringExtra("term");
        String datei = intent.getStringExtra("date");
        String timei = intent.getStringExtra("time");

//        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\" >" + " Details " + "</font>"));

        recyclerView = findViewById(R.id.call_history);
        //show_more_layout = findViewById(R.id.show_more);

        //noCallHistory = findViewById(R.id.no_calls_found_image);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //noCallHistory.setVisibility(View.GONE);
        status_indicator = findViewById(R.id.status_indicator);
        historyTitleTop = findViewById(R.id.history_top_title);

        tag = findViewById(R.id.tag_status_view);

        name = findViewById(R.id.name_ltd_txt);
        rate = findViewById(R.id.buy_or_sell);
        stock = findViewById(R.id.open_or_close);
        apText = findViewById(R.id.pot_or_act_txt);
        recoValue = findViewById(R.id.reco_value);
        targetStart = findViewById(R.id.target_start_value);
        targetEnd = findViewById(R.id.target_end_value);

        opc = findViewById(R.id.op_or_cl);
        term = findViewById(R.id.term_days);
        targetDetails = findViewById(R.id.target_date);
        date2 = findViewById(R.id.dateText2);
        time2 = findViewById(R.id.timeText2);

        opcl = findViewById(R.id.op_or_cl2);
        name2 = findViewById(R.id.name_ltd_txt2);
        reduceValue = findViewById(R.id.reduce_value);
        tcText2 = findViewById(R.id.tc_text);
        tcValue2 = findViewById(R.id.reduce_value2);
        date = findViewById(R.id.dateText);
        time = findViewById(R.id.timeText);

        Log.d(TAG, "onCreate: name - " + namei);
        Log.d(TAG, "onCreate: rate - " + ratei);
        Log.d(TAG, "onCreate: stock - " + stocki);
        Log.d(TAG, "onCreate: reco - " + recopricei);
        Log.d(TAG, "onCreate: tcopen - " + targetopeni);
        Log.d(TAG, "onCreate: tcclose - " + targetclosei);
        Log.d(TAG, "onCreate: term - " + termi);
        Log.d(TAG, "onCreate: date - " + datei);
        Log.d(TAG, "onCreate: time - " + timei);

        switch (termi){
            case "LONG TERM":
                targetDay = 365;
                break;
            case "INTRADAY":
                targetDay = 1;
                break;
            case "SHORT TERM":
                targetDay = 30;
                break;
            case "MEDIUM TERM":
                targetDay = 90;
                break;
        }


        name.setText(namei);
        if (ratei.equals("buy")){
            rate.setText(ratei);
            rate.setTextColor(getResources().getColor(R.color.green));
            rate.setBackgroundResource(R.drawable.buy_stroke);

        }else{
            rate.setText(ratei);
            rate.setTextColor(getResources().getColor(R.color.red));
            rate.setBackgroundResource(R.drawable.sell_stroke);
        }
        if (stocki.equals("open")){
            stock.setText(stocki);
            opcl.setText(stocki);
            historyTitleTop.setText("Add");
            status_indicator.setImageResource(R.drawable.round_open);
            stock.setTextColor(getResources().getColor(R.color.orange));
            opcl.setTextColor(getResources().getColor(R.color.orange));
            tag.setBackgroundColor(getResources().getColor(R.color.orange));

        }else{
            stock.setText(stocki);
            opcl.setText(stocki);
            historyTitleTop.setText("Exit");
            status_indicator.setImageResource(R.drawable.round_close);
            stock.setTextColor(getResources().getColor(R.color.red));
            opcl.setTextColor(getResources().getColor(R.color.red));
            tag.setBackgroundColor(getResources().getColor(R.color.red));
        }
        recoValue.setText(String.valueOf(recopricei));
        targetStart.setText(String.valueOf(targetopeni));
        targetEnd.setText(String.valueOf(targetclosei));


        if (stocki.equals("open")){
            opc.setText(stocki);
            opc.setTextColor(getResources().getColor(R.color.orange));
        }else{
            opc.setText(stocki);
            opc.setTextColor(getResources().getColor(R.color.red));
        }

        name2.setText(namei + " @");
        reduceValue.setText(String.valueOf(recopricei));
        tcValue2.setText(String.valueOf(targetclosei));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(datei));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, targetDay);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        String output = sdf1.format(c.getTime());

        Log.d(TAG, "onCreate: calc" + output);

        targetDetails.setText("Before " + output);

        Log.d(TAG, "onCreate: date target" + targetDay);

        String new_term = termi.replace("_", " ");

        term.setText(termi);
        date.setText(dateChanger(datei));
        time.setText(timei);
        date2.setText(dateChanger(datei));
        time2.setText(timei);

        loadCallHistory(rid);

//        show_more_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMore++;
//                if (showMore % 2 != 0) {
//                    recyclerView.getLayoutParams().width = RecyclerView.LayoutParams.MATCH_PARENT;
//                    recyclerView.setVisibility(View.VISIBLE);
//                    loadCallHistory(rid);
//                    if (datalist.size() == 0) {
//                        noCallHistory.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    recyclerView.setVisibility(View.GONE);
//                    noCallHistory.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    private void loadCallHistory(String rid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CALL_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: call history response: " + response);

               if (!response.equals("No response")){
                   try {
                       JSONArray call_history = new JSONArray(response);

                       for (int i=0; i<call_history.length(); i++){
                           JSONObject jsonObject = call_history.getJSONObject(i);

                           datalist.add(new DerivativeCallHistoryModel(
                                   jsonObject.getString("h_symbol"),
                                   jsonObject.getString("h_buy_value"),
                                   jsonObject.getString("h_calls_method"),
                                   jsonObject.getString("h_reco_pr"),
                                   jsonObject.getString("h_target"),
                                   jsonObject.getString("h_updated"),
                                   jsonObject.getString("h_updated_time;")
                           ));

                       }

                       adapter = new DerivativeCallHistoryAdapter(DerivativeExtendViewActivity.this, datalist);
                       recyclerView.setAdapter(adapter);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: call history error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hid", rid);

                Log.d(TAG, "getParams: response rid:" + rid);
                return params;
            }
        };
        MySingleton.getMySingleton(DerivativeExtendViewActivity.this).addToRequestQue(stringRequest);
    }

    public String dateChanger(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String timeChanger(String time) {
        String inputPattern = "HH:mm:s";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}