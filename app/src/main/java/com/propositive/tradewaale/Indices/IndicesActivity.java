package com.propositive.tradewaale.Indices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.propositive.tradewaale.Indices.tab.GlobalIndices.GlobalIndicesFragment;
import com.propositive.tradewaale.Indices.tab.IndianIndices.IndianIndicesFragment;
import com.propositive.tradewaale.R;

public class IndicesActivity extends AppCompatActivity {

    private static final String TAG = "Indian Indices";

    TabLayout tabLayout;
    ViewPager viewPager;

    RadioGroup toggleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indices);
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

//        toggleGroup = findViewById(R.id.radioGroup);
//
//        //TODO: buttons filter
//        toggleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId) {
//
//                    // TODO: filter intraday
//                    case R.id.indian:
//                        Toast.makeText(getApplicationContext(), "indian indices!...", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    // TODO: filter short term
//                    case R.id.global:
//                        Toast.makeText(getApplicationContext(), "global indices!...", Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
//            }
//        });

    }

    private void setUpViewPager(ViewPager viewPager) {
        IndicesTabAdapter adapter = new IndicesTabAdapter(getSupportFragmentManager());

        adapter.addFragment(new IndianIndicesFragment(),"Indian Indices");
        adapter.addFragment(new GlobalIndicesFragment(), "Global Indices");

        viewPager.setAdapter(adapter);
    }
}