package com.propositive.tradewaale.advisory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.derivative.DerivativeFragment;
import com.propositive.tradewaale.advisory.tabs.equity.EquityFragment;
import com.propositive.tradewaale.home.NewsActivity;
import com.propositive.tradewaale.livefeed.EventActivity;
import com.propositive.tradewaale.market.bottomMarketActivity;

public class bottomAdvisoryActivity extends AppCompatActivity {

    private static final String TAG = "advisory fragment";
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem equity, derivative;
    AdvisoryTabAdapter tabAdapter;

    BottomNavigationView bottomNavView;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_advisory);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager) {
        AdvisoryTabAdapter adapter = new AdvisoryTabAdapter(getSupportFragmentManager());

        adapter.addFragment(new EquityFragment(),"Equity");
        adapter.addFragment(new DerivativeFragment(), "Derivative");

        viewPager.setAdapter(adapter);
    }
}