package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizActivity extends BaseActivity implements View.OnClickListener {
    private Button btnQuiz1, btnQuiz2, btnQuiz3, btnQuiz4, btnQuiz5;
    private int [] lessonSuccess;
    private LinearLayout videoContainer;
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

        database = new Database(this);

        ConnectData(userID);
        updateButton(lessonSuccess);

        String video = getIntent().hasExtra("Video") ? getIntent().getStringExtra("Video") : null;
        videoContainer = findViewById(R.id.videoContainer);
        createVideoView(video);
    }

    private void createVideoView(String start) {
        if (start != null) {
            if (videoContainer == null) return;

            videoContainer.removeAllViews();

            VideoView videoView = new VideoView(this);
            LinearLayout.LayoutParams videoParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 800
            );
            videoParams.setMargins(0, 2300, 0, 0);
            videoView.setLayoutParams(videoParams);

            try {
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.celebration);
                videoView.setVideoURI(videoUri);

                videoContainer.addView(videoView);
                videoContainer.setVisibility(View.VISIBLE);

                videoView.setOnPreparedListener(mp -> {
                    mp.setLooping(true);
                    videoView.start();
                });

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "ไม่สามารถโหลดวิดีโอได้", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ConnectData(String UserID) {
        ArrayList<HashMap<String, Integer>> Score = database.getLessonsLearned(UserID);
        if (!Score.isEmpty()) {
            lessonSuccess = new int[Score.size()];
            for (int i = 0; i < Score.size(); i++) {
                lessonSuccess[i] = Score.get(i).get("lessonID");
            }
        } else {
            lessonSuccess = new int[0];
        }
    }

    private void updateButton(int[] lesson) {
        for (int n = 0; n < lesson.length; n++) {
            if (lesson[n] == 1) btnQuiz1.setBackgroundTintList(getResources().getColorStateList(R.color.purple));
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
                    startActivity(new Intent(QuizActivity.this, quizClass));
                    overridePendingTransition(0, 0);
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

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_quiz;
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.action_quiz;
    }
}