package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lesson4 extends BaseActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnQuiz4, btnBackLesson, btnNextLesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_lesson4);

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
            finish();
        } else if (id == R.id.btnBackLesson) {
            startActivity(new Intent(this, Lesson3.class));
            finish();
        } else if (id == R.id.btnNextLesson) {
            startActivity(new Intent(this, Lesson5.class));
            finish();
        } else if (id == R.id.btnQuiz4) {
            startActivity(new Intent(this, Quiz4.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}