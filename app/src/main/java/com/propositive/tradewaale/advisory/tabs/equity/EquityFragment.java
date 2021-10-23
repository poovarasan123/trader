package com.propositive.tradewaale.advisory.tabs.equity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.propositive.tradewaale.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquityFragment extends Fragment {

    private static final String TAG = "equity fregment";
    RecyclerView recyclerView;
    ImageView filterbtn;
    HorizontalScrollView scrollbar;
    SwipeRefreshLayout swipeRefreshLayout;
    List<EquityModel> data;
    ImageView imageView;
    EditText search_filter;
    EquityAdapter adapter;
    //MaterialButtonToggleGroup toggleGroup;
    RadioGroup toggleGroup;

    //MaterialButton intraday, short_term, medium_term, long_term, open, close, buy, sell;


    Context context;
    int value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_equity, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        filterbtn = root.findViewById(R.id.filter);
        scrollbar = root.findViewById(R.id.hscroll);

        imageView = root.findViewById(R.id.no_record_img);

        search_filter = root.findViewById(R.id.search_bar);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

        toggleGroup = root.findViewById(R.id.radioGroup);

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
                //toggleGroup.clearChecked();
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


        //TODO: buttons filter
        toggleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){

                    // TODO: filter intraday
                    case R.id.intraday:
                        String intradayterm = "intraday";
                        fliterTerm(intradayterm);
                        break;

                    // TODO: filter short term
                    case R.id.short_term:
                        String shortterm = "short_term";
                        fliterTerm(shortterm);
                        break;

                    // TODO: filter medium term
                    case R.id.medium_term:
                        String monthterm = "medium_term";
                        fliterTerm(monthterm);
                        break;

                    // TODO: filter long term
                    case R.id.long_term:
                        String longTerm = "long_term";
                        fliterTerm(longTerm);
                        break;

                    // TODO: filter open
                    case R.id.open:
                        String openterm = "open";
                        fliterstock(openterm);
                        break;

                    // TODO: filter close
                    case R.id.close:
                        String closeterm = "close";
                        fliterstock(closeterm);
                        break;

                    // TODO: filter sell
                    case R.id.sell:
                        String sellterm = "sell";
                        fliterrate(sellterm);
                        break;

                    // TODO: filter buy
                    case R.id.buy:
                        String buyterm = "buy";
                        fliterrate(buyterm);
                        break;
                }
            }
        });

        return root;
    }

    //TODO: rate filter
    private void fliterrate(String buyterm) {
        List<EquityModel> rateList = new ArrayList<>();
        for (EquityModel term : data) {
            if (term.getBuy_value().contains(buyterm)) {
                rateList.add(term);
            }
        }
        adapter.rateList(rateList);
    }

    //TODO: stock filter
    private void fliterstock(String openterm) {
        List<EquityModel> stockList = new ArrayList<>();
        for (EquityModel term : data) {
            if (term.getCalls_method().contains(openterm)) {
                stockList.add(term);
            }
        }
        adapter.stockList(stockList);
    }

    // TODO: term filter
    private void fliterTerm(String longTerm) {
        List<EquityModel> termList = new ArrayList<>();
        for (EquityModel term : data) {
            if (term.getExp_term().contains(longTerm)) {
                termList.add(term);
            }
        }
        adapter.TermList(termList);
    }


    // TODO: search input filter
    private void filter(String filter) {
        List<EquityModel> flist = new ArrayList<>();
        for (EquityModel ad : data) {
            if (ad.getSymbol().contains(filter)) {
                flist.add(ad);
            }
        }
        adapter.updateList(flist);
    }

    private void setdata() {

        Call<List<EquityModel>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<EquityModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<EquityModel>> call, @NonNull Response<List<EquityModel>> response) {

                data = response.body();

                Log.d(TAG, "onResponse: " + data.size());

                if (data.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new EquityAdapter(data);

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
            public void onFailure(@NonNull Call<List<EquityModel>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext().getApplicationContext(), "error" + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(requireContext().getApplicationContext(), "error" + call, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: " + call.toString());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}