package com.master.navdrawerbottomnva;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.master.navdrawerbottomnva.advisory.bottomAdvisoryFragment;
import com.master.navdrawerbottomnva.bookmark.BookmarkActivity;
import com.master.navdrawerbottomnva.home.bottomHomeFragment;
import com.master.navdrawerbottomnva.livefeed.bottomLiveFeedFragment;
import com.master.navdrawerbottomnva.market.bottomMarketFragment;
import com.master.navdrawerbottomnva.openAccount.OpenAccountActivity;
import com.master.navdrawerbottomnva.privacypolicy.PrivacyPolicyActivity;
import com.master.navdrawerbottomnva.termOfuse.TermofUseActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog moreMenuSheet;
    private BottomSheetDialog supportSheet;

    private Switch themeSwitch;

    BottomNavigationView bottomNavView;

    UserSettings userSettings;

    CircleImageView circleImageView, setPic;

    LinearLayout bookmark, openDematAccpunt, privacypolicy, termsOfuse, support, share, logout;

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

        circleImageView = loginSheetView.findViewById(R.id.profile_image);

        bookmark = loginSheetView.findViewById(R.id.bookmark_menu);
        openDematAccpunt = loginSheetView.findViewById(R.id.open_account_menu);
        privacypolicy = loginSheetView.findViewById(R.id.privacy_menu);
        termsOfuse = loginSheetView.findViewById(R.id.term_menu);
        support = loginSheetView.findViewById(R.id.support_menu);
        share = loginSheetView.findViewById(R.id.share_menu);
        logout = loginSheetView.findViewById(R.id.logout_menu);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCrop();
//                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                View view = inflater.inflate(R.layout.set_profile_layout,null);
//                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                dialog.setView(view);
//                dialog.setCancelable(false);
//
//
//                AlertDialog alert = dialog.create();
//                alert.show();
//
//                Button cancel = view.findViewById(R.id.cancel);
//                setPic = view.findViewById(R.id.profile_image);
//                setPic.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                    }
//                });
//
//                cancel.setOnClickListener(v1 -> {
//                    setPic.setImageBitmap(null);
//                    alert.dismiss();
//                });
            }
        });


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        openDematAccpunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OpenAccountActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        termsOfuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TermofUseActivity.class));
                moreMenuSheet.dismiss();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportSheet();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Share for testing!....");
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreMenuSheet.setContentView(loginSheetView);
        moreMenuSheet.show();
    }

    private void SupportSheet() {
        supportSheet = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        View loginSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.support_layout, findViewById(R.id.support_sheet));

        supportSheet.setContentView(loginSheetView);
        supportSheet.show();
    }



    private void startCrop() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                circleImageView.setImageURI(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

}