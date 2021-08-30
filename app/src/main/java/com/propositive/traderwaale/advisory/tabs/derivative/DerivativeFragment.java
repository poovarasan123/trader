package com.propositive.traderwaale.advisory.tabs.derivative;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.propositive.traderwaale.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DerivativeFragment extends Fragment {

    private static final String TAG = "derivative fregment";
    RecyclerView recyclerView;
    ImageView filterbtn;
    HorizontalScrollView scrollbar;
    SwipeRefreshLayout swipeRefreshLayout;
    List<DerivativeModel> data;
    ImageView imageView;
    EditText search_filter;
    DerivativeAdapter adapter;
    MaterialButtonToggleGroup toggleGroup;
    int value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_derivative, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        filterbtn = root.findViewById(R.id.filter);
        scrollbar = root.findViewById(R.id.hscroll);

        imageView = root.findViewById(R.id.no_record_img);

        search_filter = root.findViewById(R.id.search_bar);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

        toggleGroup = root.findViewById(R.id.btnstoggle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        setdata();

        //TODO: swipe to refresh

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // TODO: show filter buttons

        filterbtn.setOnClickListener(v -> {
            value++;
            if (value % 2 != 0) {
                scrollbar.setVisibility(View.VISIBLE);
            } else {
                scrollbar.setVisibility(View.GONE);
                toggleGroup.clearChecked();
                setdata();
            }

        });

        // TODO: search from input

        search_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        //TODO: buttons filter by term wise

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {

                    switch (checkedId) {

                        case R.id.btnL:
                            String longTerm = "LONG TERM";
                            // TODO: filter long term
                            fliterTerm(longTerm);
                            break;

                        case R.id.btnI:
                            String intradayterm = "INTRADAY";
                            // TODO: filter long term
                            fliterTerm(intradayterm);
                            break;

                        case R.id.btnS:
                            String shortterm = "SHORT TERM";
                            // TODO: filter long term
                            fliterTerm(shortterm);
                            break;

                        case R.id.btnM:
                            String monthterm = "MEDIUM TERM";
                            // TODO: filter long term
                            fliterTerm(monthterm);
                            break;

                        case R.id.btnO:
                            String openterm = "open";
                            // TODO: filter long term
                            fliterstock(openterm);
                            break;

                        case R.id.btnC:
                            String closeterm = "close";
                            // TODO: filter long term
                            fliterstock(closeterm);
                            break;

                        case R.id.btnSell:
                            String sellterm = "sell";
                            // TODO: filter long term
                            fliterrate(sellterm);
                            break;

                        case R.id.btnBuy:
                            String buyterm = "buy";
                            // TODO: filter long term
                            fliterrate(buyterm);
                            break;
                    }

                } else
                    adapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    //TODO: rate filter
    private void fliterrate(String buyterm) {
        List<DerivativeModel> rateList = new ArrayList<>();
        for (DerivativeModel term : data) {
            if (term.getRate_status().contains(buyterm)) {
                rateList.add(term);
            }
        }
        adapter.rateList(rateList);
    }

    //TODO: stock filter
    private void fliterstock(String openterm) {
        List<DerivativeModel> stockList = new ArrayList<>();
        for (DerivativeModel term : data) {
            if (term.getStock_status().contains(openterm)) {
                stockList.add(term);
            }
        }
        adapter.stockList(stockList);
    }

    // TODO: term filter
    private void fliterTerm(String longTerm) {
        List<DerivativeModel> termList = new ArrayList<>();
        for (DerivativeModel term : data) {
            if (term.getTerms().contains(longTerm)) {
                termList.add(term);
            }
        }
        adapter.TermList(termList);
    }


    // TODO: search input filter
    private void filter(String filter) {
        List<DerivativeModel> flist = new ArrayList<>();
        for (DerivativeModel ad : data) {
            if (ad.getName().contains(filter)) {
                flist.add(ad);
            }
        }
        adapter.updateList(flist);
    }

    private void setdata() {

        Call<List<DerivativeModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<DerivativeModel>>() {
            @Override
            public void onResponse(Call<List<DerivativeModel>> call, Response<List<DerivativeModel>> response) {

                data = response.body();

                Log.d(TAG, "onResponse: " + data.size());

                if (data.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new DerivativeAdapter(data);

                    imageView.setVisibility(View.GONE);

                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    Collections.reverse(data);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                Log.d(TAG, "onResponse: data" + data);
                Log.d(TAG, "onResponse: response" + response);
                Log.d(TAG, "onResponse: call" + call);

            }

            @Override
            public void onFailure(Call<List<DerivativeModel>> call, Throwable t) {
                Toast.makeText(getContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(getContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + call.toString());
            }

        });
    }
}