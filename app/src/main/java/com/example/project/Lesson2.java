package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Lesson2 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz2, btnBackLesson, btnNextLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_lesson2);

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
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson3.class));
            finish();
        } else if (id == R.id.btnQuiz2) {
            startActivity(new Intent(this, Quiz2.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}