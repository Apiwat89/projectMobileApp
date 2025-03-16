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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
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
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "You didn't enter everything.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
            toast.show();
        }
    }

    private void ConnectData(String userStr, String passStr, String emailStr) {
        boolean resEmail = database.getUserEmail(emailStr);
        boolean resUsername = database.getUserUsername(userStr);

        if (resEmail) {
            Toast toast = Toast.makeText(this, "This email has already been used.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
            toast.show();
        } else if (resUsername) {
            Toast toast = Toast.makeText(this, "This username already exists.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
            toast.show();
        } else {
            boolean resInsertUser = database.insertUser(userStr, passStr, emailStr);

            if (resInsertUser) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast toast = Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
                toast.show();
            }
        }
    }
}