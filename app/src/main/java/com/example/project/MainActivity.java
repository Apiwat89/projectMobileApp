package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private View progressView;
    private TextView tvProgress;
    private int progressValue = 0;
    private Button btnLesson1, btnLesson2, btnLesson3, btnLesson4, btnLesson5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        setSelectedNavItem(R.id.action_home);

        progressView = findViewById(R.id.progressView);
        tvProgress = findViewById(R.id.tvProgress);
        updateProgress(progressValue);

        btnLesson1 = findViewById(R.id.btnLesson1);
        btnLesson2 = findViewById(R.id.btnLesson2);
        btnLesson3 = findViewById(R.id.btnLesson3);
        btnLesson4 = findViewById(R.id.btnLesson4);
        btnLesson5 = findViewById(R.id.btnLesson5);
        btnLesson1.setOnClickListener(this);
        btnLesson2.setOnClickListener(this);
        btnLesson3.setOnClickListener(this);
        btnLesson4.setOnClickListener(this);
        btnLesson5.setOnClickListener(this);
    }

    private void updateProgress(int progress) {
        int maxProgress = 5;
        float percentage = (progress == 0) ? 0.01f : (float) progress / maxProgress;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) progressView.getLayoutParams();
        params.matchConstraintPercentWidth = percentage;
        progressView.setLayoutParams(params);

        tvProgress.setText(progress + "/" + maxProgress + " บท");

        progressView.requestLayout();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLesson1) {
            startActivity(new Intent(this, Lesson1.class));
            finishAffinity();
        } else if (id == R.id.btnLesson2) {
            startActivity(new Intent(this, Lesson2.class));
            finishAffinity();
        } else if (id == R.id.btnLesson3) {
            startActivity(new Intent(this, Lesson3.class));
            finishAffinity();
        } else if (id == R.id.btnLesson4) {
            startActivity(new Intent(this, Lesson4.class));
            finishAffinity();
        } else if (id == R.id.btnLesson5) {
            startActivity(new Intent(this, Lesson5.class));
            finishAffinity();
        }
    }
}
