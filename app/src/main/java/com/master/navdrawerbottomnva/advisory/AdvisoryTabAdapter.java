package com.master.navdrawerbottomnva.advisory;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.master.navdrawerbottomnva.advisory.tabs.derivative.DerivativeFragment;
import com.master.navdrawerbottomnva.advisory.tabs.equity.EquityFragment;

import java.util.ArrayList;
import java.util.List;

public class AdvisoryTabAdapter extends FragmentPagerAdapter {

    private static final String TAG = "adivisory tab adapter";
    int tabs;

    private List<Fragment> listFragment = new ArrayList<>();
    private List<String> listFragmentName = new ArrayList<>();

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
