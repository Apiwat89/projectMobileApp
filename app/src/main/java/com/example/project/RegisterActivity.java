package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private EditText TextUser, TextEmail, TextPassword, TextPasswordRe;
    private Button btnSignup;
    private TextView btnLogin;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextUser = findViewById(R.id.TextUsername);
        TextEmail = findViewById(R.id.TextEmail);
        TextPassword = findViewById(R.id.TextPassword);
        TextPasswordRe = findViewById(R.id.TextPasswordRe);
        btnSignup = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        database = new Database(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataofUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void checkDataofUser() {
        String userStr = TextUser.getText().toString();
        String emailStr = TextEmail.getText().toString();
        String passStr = TextPassword.getText().toString();
        String passReStr = TextPasswordRe.getText().toString();

        if (!TextUtils.isEmpty(userStr) && !TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(passStr) && !TextUtils.isEmpty(passReStr)) {
            if (passStr.equals(passReStr)) {
                ConnectData(userStr, passStr, emailStr);
            } else {
                Toast toast = Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG);
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    toast.show();
                }
            }
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

    private void ConnectData(String userStr, String passStr, String emailStr) {
        boolean resEmail = database.getUserEmail(emailStr);
        boolean resUsername = database.getUserUsername(userStr);

        if (resEmail) {
            Toast toast = Toast.makeText(this, "This email has already been used.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        } else if (resUsername) {
            Toast toast = Toast.makeText(this, "This username already exists.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        } else {
            boolean resInsertUser = database.insertUser(userStr, passStr, emailStr);

            if (resInsertUser) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                Toast toast = Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG);
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    toast.show();
                }
            }
        }
    }
}