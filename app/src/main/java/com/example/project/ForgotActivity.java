package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class ForgotActivity extends AppCompatActivity {
    private EditText TextEmail;
    private Button btnNext;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        TextEmail = findViewById(R.id.TextEmail);
        btnNext = findViewById(R.id.btnNext);

        database = new Database(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectData();
            }
        });
    }

    private void ConnectData() {
        String emailStr = TextEmail.getText().toString();

        if (!TextUtils.isEmpty(emailStr)) {
            boolean res = database.getUserEmail(emailStr);

            if (res) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(this, OPTActivity.class);
                    intent.putExtra("email", emailStr);
                    startActivity(intent);
                    finish();
                });
            } else {
                Toast toast = Toast.makeText(this, "There is no email in this system.", Toast.LENGTH_LONG);
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                } else {
                    toast.show();
                }
            }
        } else {
            Toast toast = Toast.makeText(this, "You didn't enter your email.", Toast.LENGTH_LONG);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
            } else {
                toast.show();
            }
        }
    }
}