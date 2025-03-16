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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OPTActivity extends AppCompatActivity {
    private EditText TextOTP;
    private Button btnNext;
    String otpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optactivity);

        TextOTP = findViewById(R.id.TextOPT);
        btnNext = findViewById(R.id.btnNext);

        String userEmail = getIntent().getStringExtra("email");

        Toast toast = Toast.makeText(this, "Send OTP to " + userEmail, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();

        otpCode = generateOTP();
        sendOTP(userEmail, otpCode);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTPstr = TextOTP.getText().toString();
                if (!TextUtils.isEmpty(OTPstr)) {
                    if (OTPstr.equals(otpCode)) {
                        Intent intent = new Intent(OPTActivity.this, ResetPassActivity.class);
                        intent.putExtra("email", userEmail);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(OPTActivity.this, "Wrong OTP code", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(OPTActivity.this, "You did not enter the OTP code.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
                    toast.show();
                }
            }
        });
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOTP(String email, String otpCode) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            JavaMailSender mailSender = new JavaMailSender();
            mailSender.sendVerificationCode(email, otpCode);
        });
    }
}