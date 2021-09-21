package com.propositive.tradewaale.advisory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdvisoryTabAdapter extends FragmentPagerAdapter {

    private static final String TAG = "adivisory tab adapter";
    int tabs;

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listFragmentName = new ArrayList<>();

    public AdvisoryTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

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
