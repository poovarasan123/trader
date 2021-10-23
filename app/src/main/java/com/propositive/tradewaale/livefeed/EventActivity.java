package com.propositive.tradewaale.livefeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.propositive.tradewaale.MainActivity;
import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.bottomAdvisoryActivity;
import com.propositive.tradewaale.home.NewsActivity;
import com.propositive.tradewaale.market.bottomMarketActivity;

public class EventActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        bottomNavView = findViewById(R.id.bottom_nav_bar);

        bottomNavView.setSelectedItemId(R.id.bottomLiveFeedFragment);

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