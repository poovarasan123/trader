package com.master.navdrawerbottomnva.advisory.tabs.equity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.master.navdrawerbottomnva.R;

import java.util.ArrayList;
import java.util.Collections;

public class EquityFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<EquityModel> list = new ArrayList<>();

    ImageView filterbtn;
    HorizontalScrollView scrollbar;

    EquityAdapter adapter;

    int value=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_equity, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        filterbtn = root.findViewById(R.id.filter);
        scrollbar = root.findViewById(R.id.hscroll);

        list.add(new EquityModel("interglobe aviation ltd", "buy", "close", "", "", 2005.0f,1550.78f,33.0f));
        list.add(new EquityModel("Indian Railway Ctrng nd Trsm Corp Ltd", "sell", "open", "", "", 1220.50f,1550.78f,75.0f));
        list.add(new EquityModel("interglobe aviation ltd", "buy", "close", "", "", 1580.06f,1550.78f,33.0f));
        list.add(new EquityModel("Indian Railway Ctrng nd Trsm Corp Ltd", "sell", "close", "", "", 1220.50f,1550.78f,33.0f));
        list.add(new EquityModel("interglobe aviation ltd", "buy", "open", "", "", 1420.50f,1550.78f,33.0f));
        list.add(new EquityModel("Indian Railway Ctrng nd Trsm Corp Ltd", "sell", "close", "", "", 1220.50f,1550.78f,156.0f));
        list.add(new EquityModel("interglobe aviation ltd", "buy", "open", "", "", 1520.59f,1550.78f,33.0f));
        list.add(new EquityModel("Indian Railway Ctrng nd Trsm Corp Ltd", "sell", "close", "", "", 1220.50f,1550.78f,33.0f));
        list.add(new EquityModel("interglobe aviation ltd", "sell", "open", "", "", 1720.50f,1550.78f,33.0f));
        list.add(new EquityModel("interglobe aviation ltd", "sell", "open", "", "", 1720.50f,1550.78f,33.0f));
        list.add(new EquityModel("Indian Railway Ctrng nd Trsm Corp Ltd", "sell", "close", "", "", 1220.50f,1550.78f,33.0f));

        adapter = new EquityAdapter(getContext(),list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        Collections.reverse(list);

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

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getContext(), "equity", Toast.LENGTH_SHORT).show();
    }

}