package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz2 extends BaseActivity {
    private String lessonID = "2";
    private int [] verifyRadio = {R.id.q1a, R.id.q2a, R.id.q3a, R.id.q4a, R.id.q5a,R.id.q6c, R.id.q7b, R.id.q8c};
    private RadioButton[] radios = new RadioButton[verifyRadio.length];
    private EditText q9ans, q10ans;
    private int score = 0;
    private String status = "";
    private Button btnSend;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_quiz2);

        for (int n = 0; n < radios.length; n++) {
            radios[n] = findViewById(verifyRadio[n]);
        }
        q9ans = findViewById(R.id.q9ans);
        q10ans = findViewById(R.id.q10ans);
        btnSend = findViewById(R.id.btnSend);

        database = new Database(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExam();
                ConnectData(userID);
            }
        });
    }

    private void ConnectData(String userID) {
        boolean res = database.getScoreUser(lessonID, userID);
        if (res) {
            boolean resUpdate = database.updateScore(userID, lessonID, score, status);
            if (resUpdate) {
                dialogScore();
            } else Toast.makeText(this, "update not success", Toast.LENGTH_SHORT).show();
        } else {
            boolean resInsert = database.insertScore(userID, lessonID, score, status);
            if (resInsert) {
                dialogScore();
            } else Toast.makeText(this, "insert not success", Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogScore() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("ทำแบบทดสอบแล้ว");
        builder.setMessage("คะแนนที่คุณได้คือ " + score + " คะแนน");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Quiz2.this, QuizActivity.class));
                finish();
            }
        });
        builder.create().show();
    }

    private void checkExam() {
        for (int n = 0; n < radios.length; n++) {
            if (radios[n].isChecked()) score++;
        }

        if (q9ans.getText().toString().equals("float price = 99.99f;")) score++;
        else if (q9ans.getText().toString().equals("float price = 99.99F;")) score++;
        if (q10ans.getText().toString().equals("var name = \"John\"")) score++;

        status = score == 10 ? "All correct" : "Not entirely correct";
    }
}