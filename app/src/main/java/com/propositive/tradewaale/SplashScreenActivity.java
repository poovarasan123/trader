package com.propositive.tradewaale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.propositive.tradewaale.connection.NetworkChangeListener;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "splash screen";
    TextView forgot;
    ImageView logo;
    TextView name;
    LinearLayout linearLayout;
    Button login, register, gotoMain;
    Animation top, bottom;
    BottomSheetDialog loginSheet;
    ProgressBar progressBar;
    EditText uMail, uPassword;


    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo_image);
        name = findViewById(R.id.text_name);

        linearLayout = findViewById(R.id.entry_sheet);

        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);

        SharedPreferences sharedPreferences  = getSharedPreferences("user_token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        //Log.e(TAG, "onCreate: token " + token);

        top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo_anim);
        bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text_anim);

        logo.setAnimation(top);
        name.setAnimation(bottom);
        linearLayout.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loginSheet = new BottomSheetDialog(SplashScreenActivity.this, R.style.BottomSheetDialogTheme);
                        View loginSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(R.layout.login_sheet, findViewById(R.id.loginBottomSheet));

                        progressBar = loginSheetView.findViewById(R.id.progressBar);
                        //progressBar.setVisibility(View.INVISIBLE);

                        forgot = loginSheetView.findViewById(R.id.forgottxt);

                        gotoMain = loginSheetView.findViewById(R.id.loginbtn);
                        uMail = loginSheetView.findViewById(R.id.username);
                        uPassword = loginSheetView.findViewById(R.id.pass);

                        forgot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SplashScreenActivity.this);
                                View view = getLayoutInflater().inflate(R.layout.forgot_password_layout, null);
                                dialogBuilder.setView(view);

                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                            }
                        });

                        gotoMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String mail = uMail.getText().toString();
                                String password = uPassword.getText().toString();

                                //FunLogin(mail, password);
                                loginSheet.dismiss();

                            }
                        });
                        loginSheet.setContentView(loginSheetView);
                        loginSheet.show();
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.REGISTER_URL));
                        startActivity(browserIntent);
                    }
                });

            }
        }, 2000);
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