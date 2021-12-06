package com.propositive.tradewaale.livefeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.propositive.tradewaale.MainActivity;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.bottomAdvisoryActivity;
import com.propositive.tradewaale.home.NewsActivity;
import com.propositive.tradewaale.market.bottomMarketActivity;

public class EventActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


    }
}