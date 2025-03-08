package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends BaseActivity {
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
        btnLesson1.setOnClickListener(view -> startActivity(new Intent(this, Lesson1.class)));
        btnLesson2.setOnClickListener(view -> startActivity(new Intent(this, Lesson2.class)));
        btnLesson3.setOnClickListener(view -> startActivity(new Intent(this, Lesson3.class)));
        btnLesson4.setOnClickListener(view -> startActivity(new Intent(this, Lesson4.class)));
        btnLesson5.setOnClickListener(view -> startActivity(new Intent(this, Lesson5.class)));
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
}
