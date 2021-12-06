package com.propositive.tradewaale.market;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.propositive.tradewaale.R;

public class bottomMarketFragment extends Fragment {

    //String URL = "https://propositive.in/chart/";

    String FUTURE_OI_ANALYSER = "http://www.screener.tradewaale.com/futures-oi-analyser";
    String DAILY_OI_ANALYSER = "http://www.screener.tradewaale.com/futures-daily-oi-analyser";

    Button future_oi_analyser1, future_daily_oi_analyser2;

    WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_market, container, false);

        future_oi_analyser1 = view.findViewById(R.id.future_oi);
        future_daily_oi_analyser2 = view.findViewById(R.id.future_daliy_oi);

        webView = view.findViewById(R.id.webview);

        future_oi_analyser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.loadUrl(FUTURE_OI_ANALYSER);
            }
        });

        future_daily_oi_analyser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.loadUrl(DAILY_OI_ANALYSER);
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(FUTURE_OI_ANALYSER);



//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());

        return view;
    }


}