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

    String URL = "https://propositive.in/charts/";

    WebView webView;

    BottomNavigationView bottomNavView;
    MenuItem menuItem;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_market);

        webView =findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);

//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());

        bottomNavView = findViewById(R.id.bottom_nav_bar);

        bottomNavView.setSelectedItemId(R.id.bottomMarketFragment);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (menuItem.getItemId()){
                    case R.id.bottomHomeFragment:
                        Toast.makeText(getApplicationContext(), "main activity", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottomMarketFragment:
                        Toast.makeText(getApplicationContext(), "market activity", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), bottomMarketActivity.class));
//                    overridePendingTransition(0,0);
                        return true;
                    case R.id.bottomAdvisoryFragment:
                        Toast.makeText(getApplicationContext(), "advisory activity", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), bottomAdvisoryActivity.class));
//                    overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomLiveFeedFragment:
                        Toast.makeText(getApplicationContext(), "event activity", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), EventActivity.class));
//                    overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });


    }
}