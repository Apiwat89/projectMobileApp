package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Lesson1 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz1, btnNextLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_lesson1);

        btnBack = findViewById(R.id.btnBack);
        btnQuiz1 = findViewById(R.id.btnQuiz1);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnBack.setOnClickListener(this);
        btnQuiz1.setOnClickListener(this);
        btnNextLesson.setOnClickListener(this);

        database = new Database(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson2.class));
            finish();
        } else if (id == R.id.btnQuiz1) {
            startActivity(new Intent(this, Quiz1.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}