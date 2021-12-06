package com.propositive.tradewaale.PlanAndExpired;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.equity.EquityModel;
import com.propositive.tradewaale.home.newsAdapter;
import com.propositive.tradewaale.PlanAndExpired.PlanAdapter;
import com.propositive.tradewaale.PlanAndExpired.PlanModel;
import com.propositive.tradewaale.PlanAndExpired.apiController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Plans extends AppCompatActivity {

    private static final String TAG = "Plan Activity";

    ConstraintLayout show1, show2, show3, show4;

    LinearLayout detailsLayout1, detailsLayout2, detailsLayout3, detailsLayout4;

    int plan1 = 0;
    int plan2 = 0;
    int plan3 = 0;
    int plan4 = 0;

    RecyclerView recyclerView;
    PlanAdapter adapter;

    List<PlanModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        recyclerView = findViewById(R.id.plans_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        setData();

        recyclerView.setAdapter(adapter);

//        show1 = findViewById(R.id.show_details1);
//        show2 = findViewById(R.id.show_details2);
//        show3 = findViewById(R.id.show_details3);
//        show4 = findViewById(R.id.show_details4);
//
//        detailsLayout1 = findViewById(R.id.details1);
//        detailsLayout2 = findViewById(R.id.details2);
//        detailsLayout3 = findViewById(R.id.details3);
//        detailsLayout4 = findViewById(R.id.details4);
//
//        show1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plan1++;
//
//                if (plan1 % 2 == 1){
//                    detailsLayout1.setVisibility(View.VISIBLE);
//                } else {
//                    detailsLayout1.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        show2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plan2++;
//
//                if (plan2 % 2 == 1){
//                    detailsLayout2.setVisibility(View.VISIBLE);
//                } else {
//                    detailsLayout2.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        show3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plan3++;
//
//                if (plan3 % 2 == 1){
//                    detailsLayout3.setVisibility(View.VISIBLE);
//                } else {
//                    detailsLayout3.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        show4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plan4++;
//
//                if (plan4 % 2 == 1){
//                    detailsLayout4.setVisibility(View.VISIBLE);
//                } else {
//                    detailsLayout4.setVisibility(View.GONE);
//                }
//            }
//        });
//
//    }
//
//    public void get_trail(View view) {
//        Toast.makeText(getApplicationContext(), "get trail plan under construction!...", Toast.LENGTH_SHORT).show();
//    }
//
//    public void get_smart_trader1(View view) {
//        Toast.makeText(getApplicationContext(), "get smart trader plan 1 under construction!...", Toast.LENGTH_SHORT).show();
//    }
//
//
//    public void get_smart_trader2(View view) {
//        Toast.makeText(getApplicationContext(), "get smart trader plan 2 under construction!...", Toast.LENGTH_SHORT).show();
//    }
//
//    public void get_smart_trader3(View view) {
//        Toast.makeText(getApplicationContext(), "get perfect trader plan 3 under construction!...", Toast.LENGTH_SHORT).show();

    }


    private void setData() {
        Call<List<PlanModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<PlanModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PlanModel>> call, @NonNull Response<List<PlanModel>> response) {

                data = response.body();

                Log.d(TAG, "onResponse: " + data.size());

                    adapter = new PlanAdapter(data);

                    recyclerView.setAdapter(adapter);
                    //Collections.reverse(data);

                Log.d(TAG, "onResponse: data" + data);
                Log.d(TAG, "onResponse: response" + response);
                Log.d(TAG, "onResponse: call" + call);

            }

            @Override
            public void onFailure(@NonNull Call<List<PlanModel>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(getApplicationContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + call.toString());
            }
        });
    }


}