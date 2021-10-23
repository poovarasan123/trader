package com.propositive.tradewaale.openAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.connection.NetworkChangeListener;

public class OpenAccountActivity extends AppCompatActivity {

    Button btn1, btn2;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_account);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenAccountActivity.this, WebviewActivity.class);

                i.putExtra("url", Constants.ALICEBLUE_URL);

                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenAccountActivity.this, WebviewActivity.class);

                i.putExtra("url", Constants.PROFITMART_URL);

                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}