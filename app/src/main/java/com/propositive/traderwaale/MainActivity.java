package com.propositive.traderwaale;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient;
import com.propositive.traderwaale.advisory.bottomAdvisoryFragment;
import com.propositive.traderwaale.bookmark.BookmarkActivity;
import com.propositive.traderwaale.home.bottomHomeFragment;
import com.propositive.traderwaale.livefeed.bottomLiveFeedFragment;
import com.propositive.traderwaale.market.bottomMarketFragment;
import com.propositive.traderwaale.openAccount.OpenAccountActivity;
import com.propositive.traderwaale.privacypolicy.PrivacyPolicyActivity;
import com.propositive.traderwaale.termOfuse.TermofUseActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    Context context;

    private BottomSheetDialog moreMenuSheet;
    private BottomSheetDialog supportSheet;

    BottomNavigationView bottomNavView;

    UserSettings userSettings;

    CircleImageView circleImageView, setPic;

    LinearLayout theme, bookmark, openDematAccpunt, privacypolicy, termsOfuse, support, share, logout;

    MaterialAlertDialogBuilder dialogBuilder;

    private Uri profUri;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);

        FirebaseInstallations.getInstance().getId().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("TAG", "onSuccess: refreshed token:---> " + s);
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadFragment(new bottomHomeFragment());
        bottomNavView = findViewById(R.id.bottom_nav_bar);

        bottomNavView.setOnNavigationItemSelectedListener(item -> {
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
            return loadFragment(selectedFragment);
        });

    }

    private boolean loadFragment(Fragment selectedFragment) {
        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();
            return true;
        }
        return false;
    }

//    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
//                Fragment selectedFragment = null;
//
//                switch (item.getItemId()) {
//                    case R.id.bottomHomeFragment:
//                        selectedFragment = new bottomHomeFragment();
//                        break;
//                    case R.id.bottomMarketFragment:
//                        selectedFragment = new bottomMarketFragment();
//                        break;
//                    case R.id.bottomAdvisoryFragment:
//                        selectedFragment = new bottomAdvisoryFragment();
//                        break;
//
//                    case R.id.bottomLiveFeedFragment:
//                        selectedFragment = new bottomLiveFeedFragment();
//                        break;
//                }
//
//        assert selectedFragment != null;
//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
//
//        return true;
//    };

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

        circleImageView = loginSheetView.findViewById(R.id.profile_image);

        //theme = loginSheetView.findViewById(R.id.theme_menu);

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
            }
        });

        //theme switching
        /**
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Toast.makeText(getApplicationContext(), "checked!...", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("state",isChecked);
                    editor.commit();
                    moreMenuSheet.dismiss();
                    MainActivity.this.recreate();
                }else {
                    Toast.makeText(getApplicationContext(), "not checked!...", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("state",isChecked);
                    editor.commit();
                    moreMenuSheet.dismiss();
                    MainActivity.this.recreate();
                }
            }
        });
         **/

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
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profUri = result.getUri();

                // TODO: save image
                //profUri = data.getData();

//                this.grantUriPermission(this.getPackageName(), profUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                final int takeflag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//                this.getContentResolver().takePersistableUriPermission(profUri, takeflag);

//                SharedPreferences sharedImage = getSharedPreferences("image", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedImage.edit();
//                editor.putString("image", String.valueOf(profUri));

                circleImageView.setImageURI(profUri);

                Log.d("mainActivity", "onActivityResult: image : --> " + profUri);
                //circleImageView.invalidate();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

}