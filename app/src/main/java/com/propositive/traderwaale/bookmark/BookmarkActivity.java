package com.propositive.traderwaale.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.propositive.traderwaale.R;
import com.propositive.traderwaale.home.newsAdapter;
import com.propositive.traderwaale.home.newsModel;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    bookmarkAdapter adapter;

    ArrayList<bookmarkModel> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView = findViewById(R.id.recyclerview);

        newsList.clear();


        newsList.add(new bookmarkModel(R.drawable.g, "Part Of Our World: Mermaids Mingle At US Convention", "MAY 12, 2005", "20"));
        newsList.add(new bookmarkModel(R.drawable.h, "Brookfield India REIT Q1 operating income up 4pc to Rs 170cr, to distribute Rs 182cr to unitholders", "JUN 24, 2006", "54"));
        newsList.add(new bookmarkModel(R.drawable.i, "Swiggy launches 15-30 minute grocery deliveries, expands Instamart to 5 more cities in India", "JUY 18, 2007", "18"));
        newsList.add(new bookmarkModel(R.drawable.j, "IRB Infra reports Q1 net profit at Rs 71.91 crore", "AUG 14, 2008", "76"));
        newsList.add(new bookmarkModel(R.drawable.a, "WFI suspends Vinesh Phogat for indiscipline, notice issued to Sonam Malik for misconduct", "NOV 05, 1999", "5"));
        newsList.add(new bookmarkModel(R.drawable.b, "Number of billionaires in India dropped from 141 in FY20 to 136 in FY21: FM Nirmala Sitharaman in Rajya Sabha", "DEC 15, 2000", "8"));
        newsList.add(new bookmarkModel(R.drawable.c, "SC fines 9 political parties for failure to publish candidates' criminal background", "JAN 25, 2001", "6"));
        newsList.add(new bookmarkModel(R.drawable.d, "Confirmed! Messi to move to PSG on two-year deal", "FEB 02, 2002", "2"));
        newsList.add(new bookmarkModel(R.drawable.e, "CoinDCX Becomes India’s First Crypto Unicorn, Raises $90 Million Led By B Capital", "MAR 04, 2003", "7"));
        newsList.add(new bookmarkModel(R.drawable.f, "QR Code-Based Passes To Be Issued For Fully Vaccinated Citizens At 65 Railway Stations: Mumbai Mayor", "APR 08, 2004", "1"));

        adapter = new bookmarkAdapter(this, newsList);


        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        Toast.makeText(this, "initial size " + newsList.size(), Toast.LENGTH_SHORT).show();
    }
}