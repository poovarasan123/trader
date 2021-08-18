package com.master.navdrawerbottomnva.advisory.tabs.equity.extendView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.master.navdrawerbottomnva.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EquityExtendViewActivity extends AppCompatActivity {

    TextView name, rate, stock, tcTxet, apText, recoValue, tcValue, apValue;
    TextView opc, term, dateDetails, targetDetails, date, time;
    TextView opcl, name2, reduceValue, tcText2, tcValue2, date2, time2;

    View tag;

    int targetDay;

    private static final String TAG = "equity extend activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity_extend_view);

        tag = findViewById(R.id.tag_status_view);

        name = findViewById(R.id.name_ltd_txt);
        rate = findViewById(R.id.buy_or_sell);
        stock = findViewById(R.id.open_or_close);
        tcTxet = findViewById(R.id.target_or_close_txt);
        apText = findViewById(R.id.pot_or_act_txt);
        recoValue = findViewById(R.id.reco_value);
        tcValue = findViewById(R.id.target_or_close_value);
        apValue = findViewById(R.id.pot_or_act_value);

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

        Intent intent = getIntent();

        String namei = intent.getStringExtra("name");
        String ratei = intent.getStringExtra("rateStatus");
        String stocki = intent.getStringExtra("stockStatus");
        float recopricei = intent.getFloatExtra("recoValue",0);
        float targetclosei = intent.getFloatExtra("tcValue", 0);
        float apvaluei = intent.getFloatExtra("apValue", 0);
        String termi = intent.getStringExtra("term");
        String datei = intent.getStringExtra("date");
        String timei = intent.getStringExtra("time");

        Log.d(TAG, "onCreate: name - " + namei);
        Log.d(TAG, "onCreate: rate - " + ratei);
        Log.d(TAG, "onCreate: stock - " + stocki);
        Log.d(TAG, "onCreate: reco - " + recopricei);
        Log.d(TAG, "onCreate: tc - " + targetclosei);
        Log.d(TAG, "onCreate: ap - " + apvaluei);
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
            stock.setTextColor(getResources().getColor(R.color.orange));
            opcl.setTextColor(getResources().getColor(R.color.orange));
            tag.setBackgroundColor(getResources().getColor(R.color.orange));
            tcTxet.setText("TARGET PRICE");
            apText.setText("POTENTIAL RETURN");
        }else{
            stock.setText(stocki);
            opcl.setText(stocki);
            stock.setTextColor(getResources().getColor(R.color.red));
            opcl.setTextColor(getResources().getColor(R.color.red));
            tag.setBackgroundColor(getResources().getColor(R.color.red));
            tcTxet.setText("CLOSED PRICE");
            apText.setText("ACTUAL RETURN");
        }
        recoValue.setText(String.valueOf(recopricei));
        tcValue.setText(String.valueOf(targetclosei));
        apValue.setText(String.valueOf(apvaluei));

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

        term.setText(termi);
        date.setText(dateChanger(datei));
        time.setText(timeChanger(timei));
        date2.setText(dateChanger(datei));
        time2.setText(timeChanger(timei));

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