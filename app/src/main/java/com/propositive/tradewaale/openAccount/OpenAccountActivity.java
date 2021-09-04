package com.propositive.tradewaale.openAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.propositive.tradewaale.R;

public class OpenAccountActivity extends AppCompatActivity {

    Button btn1, btn2;

    String url1 = "https://www.accountopeningaliceblue.com/CBT84";

    String url2 = "http://kyc.profitmart.info:8004/onboard.aspx?branch=3351&RM=6CvuwvGP1KjtWnvPULiPyg%3D%3D%0A";

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

                i.putExtra("url", url1);

                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenAccountActivity.this, WebviewActivity.class);

                i.putExtra("url", url2);

                startActivity(i);
            }
        });


    }
}