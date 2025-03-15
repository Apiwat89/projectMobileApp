package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout btnQuiz1, btnQuiz2, btnQuiz3, btnQuiz4, btnQuiz5;
    private TextView score1, score2, score3, score4, score5;
    private int [] lessonSuccess;
    private int [] scorelesson;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_program);
        setSelectedNavItem(R.id.action_program);

        btnQuiz1 = findViewById(R.id.btnQuiz1);
        btnQuiz2 = findViewById(R.id.btnQuiz2);
        btnQuiz3 = findViewById(R.id.btnQuiz3);
        btnQuiz4 = findViewById(R.id.btnQuiz4);
        btnQuiz5 = findViewById(R.id.btnQuiz5);
        btnQuiz1.setOnClickListener(this);
        btnQuiz2.setOnClickListener(this);
        btnQuiz3.setOnClickListener(this);
        btnQuiz4.setOnClickListener(this);
        btnQuiz5.setOnClickListener(this);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);
        score4 = findViewById(R.id.score4);
        score5 = findViewById(R.id.score5);

        database = new Database(this);

        ConnectData(userID);
        updateButton(lessonSuccess);
    }

    private void ConnectData(String userID) {
        ArrayList<HashMap<String, Integer>> Score = database.getLessonsLearned(userID);
        if (!Score.isEmpty()) {
            lessonSuccess = new int[Score.size()];
            scorelesson = new int[Score.size()];
            for (int i = 0; i < Score.size(); i++) {
                lessonSuccess[i] = Score.get(i).get("lessonID");
                scorelesson[i] = Score.get(i).get("score");
            }
        } else {
            lessonSuccess = new int[0];
            scorelesson = new int[0];
        }
    }

    private void updateButton(int[] lesson) {
        for (int n = 0; n < lesson.length; n++) {
            if (lesson[n] == 1) {
                btnQuiz1.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
                score1.setText(String.valueOf(scorelesson[n]) + "/10");
                score1.setBackgroundColor(Color.WHITE);
            }
            else if (lesson[n] == 2) btnQuiz2.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
            else if (lesson[n] == 3) btnQuiz3.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
            else if (lesson[n] == 4) btnQuiz4.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
            else if (lesson[n] == 5) btnQuiz5.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnQuiz1) DialogQuiz(1);
        if (id == R.id.btnQuiz2) DialogQuiz(2);
        if (id == R.id.btnQuiz3) DialogQuiz(3);
        if (id == R.id.btnQuiz4) DialogQuiz(4);
        if (id == R.id.btnQuiz5) DialogQuiz(5);
    }

    private void DialogQuiz(int log) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("Quiz " + log);
        builder.setMessage("คุณต้องการไปทำแบบทดสอบที่ " + log + " หรือไม่");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Class<?> quizClass = Class.forName("com.example.project.Quiz" + log);
                    startActivity(new Intent(ProgramActivity.this, quizClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}