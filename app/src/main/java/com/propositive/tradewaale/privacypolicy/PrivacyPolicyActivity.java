package com.propositive.tradewaale.privacypolicy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;
import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

   PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("privacy.pdf").load();

    }
}