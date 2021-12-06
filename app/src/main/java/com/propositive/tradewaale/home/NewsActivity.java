package com.propositive.tradewaale.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.bottomAdvisoryActivity;
import com.propositive.tradewaale.livefeed.EventActivity;
import com.propositive.tradewaale.market.bottomMarketActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    newsAdapter adapter;

    public static String NEWS_URL = "http://1192.168.29.40/trader/api/get_news.php";

    SwipeRefreshLayout swipeRefreshLayout;

    //List<newsModel> newsList = new ArrayList<>();

    private static final String TAG = "News Activity";

    BottomNavigationView bottomNavView;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        newsList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void newsList() {

        Call<List<newsModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<newsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<newsModel>> call, @NonNull retrofit2.Response<List<newsModel>> response) {

                List<newsModel> newsList = response.body();
                adapter = new newsAdapter(newsList);
                recyclerView.setAdapter(adapter);

                Log.d(TAG, "onResponse: " + newsList.size());

//                if (data.size() != 0) {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    adapter = new DerivativeAdapter(data);
//
//                    imageView.setVisibility(View.GONE);
//
//                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
//                    Collections.reverse(data);
//                } else {
//                    imageView.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }

                Log.d(TAG, "onResponse: data " + newsList.toString());
                Log.d(TAG, "onResponse: response " + response);
                Log.d(TAG, "onResponse: call " + call);

            }

            @Override
            public void onFailure(@NonNull Call<List<newsModel>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure:  t " + t.toString());

                Toast.makeText(getApplicationContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: call " + call.toString());
            }

        });
    }

//    private void listNews() {
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, NEWS_URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject news = response.getJSONObject(i);
//
//                        newsModel model = new newsModel();
//                        model.setNews_image(news.getString("news_image").toString());
//                        model.setNews_title(news.getString("news_title").toString());
//                        model.setDescription(news.getString("description").toString());
//                        model.setPost_date(news.getString("post_date").toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                adapter = new newsAdapter(getContext(), newsList);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setHasFixedSize(true);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, "onErrorResponse: ");
//            }
//        });
//
//        queue.add(arrayRequest);
//    }

}