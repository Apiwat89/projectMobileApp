package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResetPassActivity extends AppCompatActivity {
    private EditText TextNewPassword;
    private Button btnNext;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextNewPassword = findViewById(R.id.TextNewPassword);
        btnNext = findViewById(R.id.btnNext);

        String userEmail = getIntent().getStringExtra("email");

        database = new Database(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectData(userEmail);
            }
        });
    }

    private void ConnectData(String userEmail) {
        String newPassStr = TextNewPassword.getText().toString();

        if (!TextUtils.isEmpty(newPassStr)) {
            boolean res = database.updateUserPassword(userEmail, newPassStr);

            if (res) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                Toast toast = Toast.makeText(this, "Unable to update password", Toast.LENGTH_LONG);
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    toast.show();
                }
            }
        } else {
            Toast toast = Toast.makeText(this, "You did not enter your password.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        }
    }
}