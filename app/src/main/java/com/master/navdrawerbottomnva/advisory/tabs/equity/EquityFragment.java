package com.master.navdrawerbottomnva.advisory.tabs.equity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.master.navdrawerbottomnva.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquityFragment extends Fragment {

    RecyclerView recyclerView;

    ImageView filterbtn;
    HorizontalScrollView scrollbar;

    SwipeRefreshLayout swipeRefreshLayout;

    List<EquityModel> data;
    List<EquityModel> datasearch;

    ImageView imageView;

    EditText search_filter;

    EquityAdapter adapter;

    int value=0;


    private static final String TAG = "equity fregment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_equity, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        filterbtn = root.findViewById(R.id.filter);
        scrollbar = root.findViewById(R.id.hscroll);

        imageView = root.findViewById(R.id.no_record_img);

        search_filter = root.findViewById(R.id.search_bar);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setdata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        filterbtn.setOnClickListener(v -> {
            value++;
            if (value % 2 != 0){
                scrollbar.setVisibility(View.VISIBLE);
            }else{
                scrollbar.setVisibility(View.GONE);
            }

        });

        return root;
    }

    private void setdata() {

        Call<List<EquityModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<EquityModel>>() {
            @Override
            public void onResponse(Call<List<EquityModel>> call, Response<List<EquityModel>> response) {

                data = response.body();

                Log.d(TAG, "onResponse: " + data.size());

                if (data.size() != 0 ){
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new EquityAdapter(data);

                    imageView.setVisibility(View.GONE);

                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    Collections.reverse(data);
                }else {
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                Log.d(TAG, "onResponse: data" + data);
                Log.d(TAG, "onResponse: response" + response);
                Log.d(TAG, "onResponse: call" + call);

            }

            @Override
            public void onFailure(Call<List<EquityModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getContext(), "equity", Toast.LENGTH_SHORT).show();
    }

}