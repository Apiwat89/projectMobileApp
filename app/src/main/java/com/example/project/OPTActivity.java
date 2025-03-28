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

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OPTActivity extends AppCompatActivity {
    private EditText TextOTP;
    private Button btnNext;
    private String otpCode;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optactivity);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextOTP = findViewById(R.id.TextOPT);
        btnNext = findViewById(R.id.btnNext);
        executorService = Executors.newSingleThreadExecutor();

        String userEmail = getIntent().getStringExtra("email");

        Toast toast = Toast.makeText(this, "Send OTP to " + userEmail, Toast.LENGTH_LONG);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            toast.show();
        }

        otpCode = generateOTP();
        sendOTP(userEmail, otpCode);

        btnNext.setOnClickListener(view -> {
            String OTPstr = TextOTP.getText().toString();
            if (!TextUtils.isEmpty(OTPstr)) {
                if (OTPstr.equals(otpCode)) {
                    Intent intent = new Intent(OPTActivity.this, ResetPassActivity.class);
                    intent.putExtra("email", userEmail);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    showToast("Wrong OTP code");
                }
            } else {
                showToast("You did not enter the OTP code.");
            }
        });
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOTP(String email, String otpCode) {
        executorService.execute(() -> {
            try {
                JavaMailSender mailSender = new JavaMailSender();
                mailSender.sendVerificationCode(email, otpCode);

                runOnUiThread(() -> showToast("OTP sent successfully!"));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> showToast("Failed to send OTP. Please try again."));
            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
