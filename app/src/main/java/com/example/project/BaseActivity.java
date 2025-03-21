package com.example.project;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getString("USER_ID", null);
        if (userID == null) {
            Toast.makeText(this, "Session expired, please log in again.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        FrameLayout container = findViewById(R.id.container);
        getLayoutInflater().inflate(getLayoutResource(), container, true);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavItemSelected);

        bottomNavigationView.setSelectedItemId(getSelectedNavItem());

        updateMenuText(getSelectedNavItem());
    }

    protected abstract @LayoutRes int getLayoutResource();

    protected abstract int getSelectedNavItem();

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Class<?> targetActivity = null;

        if (item.getItemId() == R.id.action_home && !(this instanceof MainActivity)) {
            targetActivity = MainActivity.class;
        } else if (item.getItemId() == R.id.action_quiz && !(this instanceof QuizActivity)) {
            targetActivity = QuizActivity.class;
        } else if (item.getItemId() == R.id.action_program && !(this instanceof ProgramActivity)) {
            targetActivity = ProgramActivity.class;
        } else if (item.getItemId() == R.id.action_about && !(this instanceof AboutActivity)) {
            targetActivity = AboutActivity.class;
        }

        if (targetActivity != null) {
            startActivity(new Intent(this, targetActivity));
            overridePendingTransition(0, 0);
            finish();
        }

        updateMenuText(itemId);

        return true;
    }

    private void updateMenuText(int selectedItemId) {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem item = bottomNavigationView.getMenu().getItem(i);
            boolean isSelected = item.getItemId() == selectedItemId;

            View view = bottomNavigationView.findViewById(item.getItemId());

            if (view != null) {
                float scale = isSelected ? 1.1f : 1.0f;
                float translationY = isSelected ? 0f : 20f;

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale);
                ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", translationY);

                scaleX.setDuration(300);
                scaleY.setDuration(300);
                translateY.setDuration(300);

                scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
                scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
                translateY.setInterpolator(new AccelerateDecelerateInterpolator());

                scaleX.start();
                scaleY.start();
                translateY.start();
            }

            item.setTitle(isSelected ? getMenuTitle(item.getItemId()) : "");
        }
    }

    private String getMenuTitle(int itemId) {
        if (itemId == R.id.action_home) return "Home";
        if (itemId == R.id.action_quiz) return "Quiz";
        if (itemId == R.id.action_program) return "Program";
        if (itemId == R.id.action_about) return "About";
        return "";
    }

}