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

public class Lesson5 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz5, btnBackLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_lesson5);

        btnBack = findViewById(R.id.btnBack);
        btnQuiz5 = findViewById(R.id.btnQuiz5);
        btnBackLesson = findViewById(R.id.btnBackLesson);
        btnBack.setOnClickListener(this);
        btnQuiz5.setOnClickListener(this);
        btnBackLesson.setOnClickListener(this);

        database = new Database(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.btnBackLesson) {
            startActivity(new Intent(this, Lesson4.class));
            finish();
        } else if (id == R.id.btnQuiz5) {
            startActivity(new Intent(this, Quiz5.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}