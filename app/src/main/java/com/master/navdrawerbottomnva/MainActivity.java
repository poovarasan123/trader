package com.master.navdrawerbottomnva;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.master.navdrawerbottomnva.advisory.bottomAdvisoryFragment;
import com.master.navdrawerbottomnva.home.bottomHomeFragment;
import com.master.navdrawerbottomnva.livefeed.bottomLiveFeedFragment;
import com.master.navdrawerbottomnva.market.bottomMarketFragment;


public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog moreMenuSheet;
    //private Switch themeSwitch;

    BottomNavigationView bottomNavView;

    UserSettings userSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavView = findViewById(R.id.bottom_nav_bar);

        bottomNavView.setOnNavigationItemSelectedListener(navListener);

        userSettings = new UserSettings();

        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERANCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOME_THEME, UserSettings.LIGHT_THEME);
        userSettings.setCustomeTheme(theme);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new bottomHomeFragment()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.bottomHomeFragment:
                        selectedFragment = new bottomHomeFragment();
                        break;
                    case R.id.bottomMarketFragment:
                        selectedFragment = new bottomMarketFragment();
                        break;
                    case R.id.bottomAdvisoryFragment:
                        selectedFragment = new bottomAdvisoryFragment();
                        break;

                    case R.id.bottomLiveFeedFragment:
                        selectedFragment = new bottomLiveFeedFragment();
                        break;
                }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        selectedFragment).commit();

                return true;
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.more) {
            openBottomSheet();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBottomSheet() {
        moreMenuSheet = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        View loginSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, findViewById(R.id.more_menu_sheet));

        //themeSwitch = loginSheetView.findViewById(R.id.theme_switch);


//        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//
//                    Toast.makeText(getApplicationContext(), "checked!..", Toast.LENGTH_SHORT).show();
//                    themeSwitch.setText("Dark");
//
//                    userSettings.setCustomeTheme(UserSettings.DARKTHEME);
//
//                 else
//                    Toast.makeText(getApplicationContext(), "unchecked!...", Toast.LENGTH_SHORT).show();
//                    themeSwitch.setText("Light");
//
//                    userSettings.setCustomeTheme(UserSettings.LIGHT_THEME);
//
//                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERANCES, MODE_PRIVATE).edit();
//                editor.putString(UserSettings.CUSTOME_THEME, userSettings.getCustomeTheme());
//                editor.apply();
//
//                changeTheme();
//            }
//        });

        //AppCompatDelegate.MODE_NIGHT_NO -- light
        //AppCompatDelegate.MODE_NIGHT_YES -- dark

        moreMenuSheet.setContentView(loginSheetView);
        moreMenuSheet.show();
    }

    private void changeTheme() {
        if (userSettings.getCustomeTheme().equals(UserSettings.DARKTHEME)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //themeSwitch.setChecked(true);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //themeSwitch.setChecked(false);
        }
    }
}