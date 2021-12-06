package com.propositive.tradewaale.termOfuse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;
import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.R;

public class TermofUseActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termof_use);

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("privacy.pdf").load();
    }
}