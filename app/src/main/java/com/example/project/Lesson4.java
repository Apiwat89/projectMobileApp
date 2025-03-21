package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lesson4 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz4, btnBackLesson, btnNextLesson;
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
        btnQuiz4 = findViewById(R.id.btnQuiz4);
        btnBackLesson = findViewById(R.id.btnBackLesson);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnBack.setOnClickListener(this);
        btnQuiz4.setOnClickListener(this);
        btnBackLesson.setOnClickListener(this);
        btnNextLesson.setOnClickListener(this);

        database = new Database(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.btnBackLesson) {
            startActivity(new Intent(this, Lesson3.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson5.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.btnQuiz4) {
            startActivity(new Intent(this, Quiz4.class));
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
        return R.layout.activity_lesson4;
    }

    @Override
    protected int getSelectedNavItem() {
        return 0;
    }
}