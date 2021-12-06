package com.propositive.tradewaale.Indices;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.propositive.tradewaale.Indices.tab.IndianIndices.IndianIndicesFragment;
import com.propositive.tradewaale.advisory.tabs.equity.EquityFragment;

import java.util.ArrayList;
import java.util.List;

public class IndicesTabAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listFragmentName = new ArrayList<>();

    public IndicesTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentName.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listFragmentName.add(title);
    }

}
