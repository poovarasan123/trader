package com.propositive.tradewaale.advisory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.derivative.DerivativeFragment;
import com.propositive.tradewaale.advisory.tabs.equity.EquityFragment;


public class bottomAdvisoryFragment extends Fragment {

    private static final String TAG = "advisory fragment";
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem equity, derivative;
    AdvisoryTabAdapter tabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bottom_advisory, container, false);

        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager = root.findViewById(R.id.view_pager);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        AdvisoryTabAdapter adapter = new AdvisoryTabAdapter(getChildFragmentManager());

        adapter.addFragment(new EquityFragment(),"Equity");
        adapter.addFragment(new DerivativeFragment(), "Derivative");

        viewPager.setAdapter(adapter);
    }
}