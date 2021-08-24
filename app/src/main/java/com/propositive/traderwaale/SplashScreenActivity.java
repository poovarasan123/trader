package com.propositive.traderwaale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.propositive.traderwaale.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo;
    TextView name;

    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo_image);
        name = findViewById(R.id.text_name);

        top = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_logo_anim);
        bottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_text_anim);

        logo.setAnimation(top);
        name.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreenActivity.this, LoginActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, 3000);
    }
}