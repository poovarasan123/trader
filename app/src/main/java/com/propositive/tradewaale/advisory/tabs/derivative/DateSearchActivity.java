package com.propositive.tradewaale.advisory.tabs.derivative;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.propositive.tradewaale.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateSearchActivity extends AppCompatActivity {

    TextView mDate;
    Button getdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_search);

        mDate = findViewById(R.id.date);
        getdate = findViewById(R.id.button);

        getdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .build();

                datePicker.show(getSupportFragmentManager(), "date_range_picker");

                datePicker.addOnPositiveButtonClickListener(datePicked ->{

                    String from = datePicked.toString();

//                    String[] new_date = from.split(" ");
//
//                    String fone = new_date[0];
//                    String ftwo = new_date[1];

                    Log.e("datesearch", "onClick: from date = " + from);
//                    Log.e("datesearch", "onClick: to date = " + ftwo);

                    mDate.setText("from: " + from );
                });

            }
        });

    }

    private String longToDate(long input) {
        Date d = new Date(input);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String date = sdf.format(d);
        return date;
    }
}