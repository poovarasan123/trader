package com.propositive.tradewaale.livefeed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.propositive.tradewaale.R;

public class bottomLiveFeedFragment extends Fragment {

    MaterialButtonToggleGroup toggleGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bottom_live_feed, container, false);


//        toggleGroup = root.findViewById(R.id.theme_group);

        return root;
    }
}