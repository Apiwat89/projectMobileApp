package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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

        userID = getIntent().getStringExtra("USER_ID");
        if (userID == null) {
            Toast.makeText(this, "Session expired, please log in again.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class<?> targetActivity = null;

                if (item.getItemId() == R.id.action_home && !(BaseActivity.this instanceof MainActivity)) {
                    targetActivity = MainActivity.class;
                } else if (item.getItemId() == R.id.action_quiz && !(BaseActivity.this instanceof QuizActivity)) {
                    targetActivity = QuizActivity.class;
                } else if (item.getItemId() == R.id.action_program && !(BaseActivity.this instanceof ProgramActivity)) {
                    targetActivity = ProgramActivity.class;
                }

                if (targetActivity != null) {
                    Intent intent = new Intent(BaseActivity.this, targetActivity);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                    finish();
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