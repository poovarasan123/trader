package com.propositive.tradewaale.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.propositive.tradewaale.R;

public class RegisterActivity extends AppCompatActivity {

    String URL = "http://propositive.in/register";

    WebView webView;

    Activity activity;
    private ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        activity = this;

        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        webView = findViewById(R.id.registerWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//                progDailog.dismiss();
//                final String URL = url;
//                if (URL.contains("propositive.in"))
//                    finish();
//                //super.onPageStarted(view, url, favicon);
//            }
//        });

        webView.loadUrl(URL);

//
    }
}