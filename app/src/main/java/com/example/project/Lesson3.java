package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;

public class Lesson3 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz3, btnBackLesson, btnNextLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_lesson3);

        btnBack = findViewById(R.id.btnBack);
        btnQuiz3 = findViewById(R.id.btnQuiz3);
        btnBackLesson = findViewById(R.id.btnBackLesson);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnBack.setOnClickListener(this);
        btnQuiz3.setOnClickListener(this);
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
            startActivity(new Intent(this, Lesson2.class));
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson4.class));
            finish();
        } else if (id == R.id.btnQuiz3) {
            startActivity(new Intent(this, Quiz3.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}