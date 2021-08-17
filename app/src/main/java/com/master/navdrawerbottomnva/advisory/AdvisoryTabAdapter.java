package com.master.navdrawerbottomnva.advisory;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.master.navdrawerbottomnva.advisory.tabs.derivative.DerivativeFragment;
import com.master.navdrawerbottomnva.advisory.tabs.equity.EquityFragment;

public class AdvisoryTabAdapter extends FragmentPagerAdapter {

    int tabs;

    Context context;

    private static final String TAG = "adivisory tab adapter";

    public AdvisoryTabAdapter(FragmentManager fragmentManager, int tabs){
        super(fragmentManager);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        try {
            switch (position) {
                case 0: return new EquityFragment();

                case 1: return new DerivativeFragment();
            }
        }
        catch (Exception e){
            Log.d(TAG, "getItem: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
