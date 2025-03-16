package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText TextUser, TextPassword;
    private Button btnLogin;
    private TextView btnForgot, btnSignup;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextUser = findViewById(R.id.TextUser);
        TextPassword = findViewById(R.id.TextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgot = findViewById(R.id.btnForgot);
        btnSignup = findViewById(R.id.btnSignup);

        database = new Database(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserPass();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkUserPass() {
        String userStr = TextUser.getText().toString();
        String passStr = TextPassword.getText().toString();

        if (!TextUtils.isEmpty(userStr) && !TextUtils.isEmpty(passStr)) {
            ConnectData(userStr, passStr);
        } else {
            Toast toast = Toast.makeText(this, "You didn't enter everything.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        }
    }

    private void ConnectData(String userStr, String passStr) {
        Cursor res = database.getUser(userStr, passStr);

        if (res != null) {
            res.moveToFirst();
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USER_ID", res.getString(0));
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Username and password are incorrect.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        }
    }

}