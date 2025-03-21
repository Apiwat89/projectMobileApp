package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lesson2 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz2, btnBackLesson, btnNextLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        btnQuiz2 = findViewById(R.id.btnQuiz2);
        btnBackLesson = findViewById(R.id.btnBackLesson);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnBack.setOnClickListener(this);
        btnQuiz2.setOnClickListener(this);
        btnBackLesson.setOnClickListener(this);
        btnNextLesson.setOnClickListener(this);

        database = new Database(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.btnBackLesson) {
            startActivity(new Intent(this, Lesson1.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson3.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.btnQuiz2) {
            startActivity(new Intent(this, Quiz2.class));
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_lesson2;
    }

    @Override
    protected int getSelectedNavItem() {
        return 0;
    }
}