package com.propositive.tradewaale.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.equity.EquityModel;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyListActivity extends AppCompatActivity {

    private static final String TAG = "Notification Activity";

    SwipeRefreshLayout swipeRefreshLayout;

    ImageView image;
    RecyclerView recyclerView;
    List<NotificationModel> notifyList;
    NotificationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);

        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.no_record_img);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        setData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setElevation(0);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Notifications" + "</font>"));

    }

    private void setData() {
        Call<List<NotificationModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<NotificationModel>> call, @NonNull Response<List<NotificationModel>> response) {

                notifyList = response.body();

                Log.d(TAG, "onResponse: " + notifyList.size());

                if (notifyList.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new NotificationListAdapter(notifyList);

                    image.setVisibility(View.GONE);

                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    Collections.reverse(notifyList);
                } else {
                    image.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                Log.d(TAG, "onResponse: data" + notifyList);
                Log.d(TAG, "onResponse: response" + response);
                Log.d(TAG, "onResponse: call" + call);

            }

            @Override
            public void onFailure(@NonNull Call<List<NotificationModel>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(getApplicationContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + call.toString());
            }
        });
    }
}