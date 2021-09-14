package com.propositive.tradewaale.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.derivative.DerivativeAdapter;
import com.propositive.tradewaale.advisory.tabs.derivative.DerivativeModel;
import com.propositive.tradewaale.home.apiController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class bottomHomeFragment extends Fragment {

    RecyclerView recyclerView;
    newsAdapter adapter;

    public static String NEWS_URL = "http://192.168.54.211/trader/api/get_news.php";

    SwipeRefreshLayout swipeRefreshLayout;

    List<newsModel> newsList = new ArrayList<>();

    private static final String TAG = "News Activity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_bottom_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

        newsList.clear();

//        newsList.add(new newsModel(R.drawable.a, "WFI suspends Vinesh Phogat for indiscipline, notice issued to Sonam Malik for misconduct", "NOV 05, 1999"));
//        newsList.add(new newsModel(R.drawable.b, "Number of billionaires in India dropped from 141 in FY20 to 136 in FY21: FM Nirmala Sitharaman in Rajya Sabha", "DEC 15, 2000"));
//        newsList.add(new newsModel(R.drawable.c, "SC fines 9 political parties for failure to publish candidates' criminal background", "JAN 25, 2001"));
//        newsList.add(new newsModel(R.drawable.d, "Confirmed! Messi to move to PSG on two-year deal", "FEB 02, 2002"));
//        newsList.add(new newsModel(R.drawable.e, "CoinDCX Becomes India’s First Crypto Unicorn, Raises $90 Million Led By B Capital", "MAR 04, 2003"));
//        newsList.add(new newsModel(R.drawable.f, "QR Code-Based Passes To Be Issued For Fully Vaccinated Citizens At 65 Railway Stations: Mumbai Mayor", "APR 08, 2004"));
//        newsList.add(new newsModel(R.drawable.g, "Part Of Our World: Mermaids Mingle At US Convention", "MAY 12, 2005"));
//        newsList.add(new newsModel(R.drawable.h, "Brookfield India REIT Q1 operating income up 4pc to Rs 170cr, to distribute Rs 182cr to unitholders", "JUN 24, 2006"));
//        newsList.add(new newsModel(R.drawable.i, "Swiggy launches 15-30 minute grocery deliveries, expands Instamart to 5 more cities in India", "JUY 18, 2007"));
//        newsList.add(new newsModel(R.drawable.j, "IRB Infra reports Q1 net profit at Rs 71.91 crore", "AUG 14, 2008"));


        newsList();
        //listNews();


        Toast.makeText(getContext(), "initial size " + newsList.size(), Toast.LENGTH_SHORT).show();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                newsList.add(new newsModel(R.drawable.a, "WFI suspends Vinesh Phogat for indiscipline, notice issued to Sonam Malik for misconduct", "NOV 05, 1999"));
//                newsList.add(new newsModel(R.drawable.b, "Number of billionaires in India dropped from 141 in FY20 to 136 in FY21: FM Nirmala Sitharaman in Rajya Sabha", "DEC 15, 2000"));
//                newsList.add(new newsModel(R.drawable.c, "SC fines 9 political parties for failure to publish candidates' criminal background", "JAN 25, 2001"));
//                newsList.add(new newsModel(R.drawable.d, "Confirmed! Messi to move to PSG on two-year deal", "FEB 02, 2002"));
//                newsList.add(new newsModel(R.drawable.e, "CoinDCX Becomes India’s First Crypto Unicorn, Raises $90 Million Led By B Capital", "MAR 04, 2003"));
//                newsList.add(new newsModel(R.drawable.f, "QR Code-Based Passes To Be Issued For Fully Vaccinated Citizens At 65 Railway Stations: Mumbai Mayor", "APR 08, 2004"));
//                newsList.add(new newsModel(R.drawable.g, "Part Of Our World: Mermaids Mingle At US Convention", "MAY 12, 2005"));
//                newsList.add(new newsModel(R.drawable.h, "Brookfield India REIT Q1 operating income up 4pc to Rs 170cr, to distribute Rs 182cr to unitholders", "JUN 24, 2006"));
//                newsList.add(new newsModel(R.drawable.i, "Swiggy launches 15-30 minute grocery deliveries, expands Instamart to 5 more cities in India", "JUY 18, 2007"));
//                newsList.add(new newsModel(R.drawable.j, "IRB Infra reports Q1 net profit at Rs 71.91 crore", "AUG 14, 2008"));

                newsList();
                //listNews();

                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(getContext(), "updated size " + newsList.size(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    public void newsList() {

        Call<List<newsModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<newsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<newsModel>> call, @NonNull retrofit2.Response<List<newsModel>> response) {

                newsList = response.body();

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
                Toast.makeText(getContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(getContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + call.toString());
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