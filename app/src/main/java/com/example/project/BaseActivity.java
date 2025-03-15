package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // ดึง userID จาก Intent ที่ส่งมาจาก LoginActivity
//        userID = getIntent().getStringExtra("USER_ID");
        userID = "1";

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
                    if (!(BaseActivity.this instanceof MainActivity)) {
                        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.action_quiz) {
                    if (!(BaseActivity.this instanceof QuizActivity)) {
                        Intent intent = new Intent(BaseActivity.this, QuizActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.action_program) {
                    if (!(BaseActivity.this instanceof ProgramActivity)) {
                        Intent intent = new Intent(BaseActivity.this, ProgramActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                }
                return true;
            }
        });
    }

    protected void setLayout(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, findViewById(R.id.container), true);
    }

    protected void setSelectedNavItem(int itemId) {
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.setSelectedItemId(itemId);
    }
}