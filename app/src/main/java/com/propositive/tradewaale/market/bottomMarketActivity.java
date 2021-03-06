package com.propositive.tradewaale.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.propositive.tradewaale.MainActivity;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.bottomAdvisoryActivity;
import com.propositive.tradewaale.home.NewsActivity;
import com.propositive.tradewaale.livefeed.EventActivity;

public class bottomMarketActivity extends AppCompatActivity {

    String URL = "https://propositive.in/chart/";

    WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_market);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);

//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);



    }
}