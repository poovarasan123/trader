package com.master.navdrawerbottomnva.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.master.navdrawerbottomnva.MainActivity;
import com.master.navdrawerbottomnva.R;
import com.master.navdrawerbottomnva.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mail,pass;
    Button login;

    TextView register, forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.mail_ID);
        pass = findViewById(R.id.pass);

        login = findViewById(R.id.loginbtn);

        register = findViewById(R.id.registertxt);
        forgot = findViewById(R.id.forgottxt);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "under construction!...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}