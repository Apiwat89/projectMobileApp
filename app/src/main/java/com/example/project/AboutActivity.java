package com.example.project;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout contectProjectManager, contectDeveloper, contectUIUX, contectResearch;
    private LinearLayout detailProjectManager, detailDeveloper, detailUIUX, detailResearch;
    private ImageView imageView_pm, imageView_dp, imageView_UIUX, imageView_RS;
    private TextView textView_pm_title, textView_dp_title, textView_UIUX_title, textView_RS_title;
    private TextView textView_pm_name, textView_dp_name, textView_UIUX_name, textView_RS_name;

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

        // ตั้งค่า Project Manager
        contectProjectManager = findViewById(R.id.contectProjectManager);
        detailProjectManager = findViewById(R.id.detailProjectManager);
        imageView_pm = findViewById(R.id.imageView_pm);
        textView_pm_title = findViewById(R.id.textView_pm_title);
        textView_pm_name = findViewById(R.id.textView_pm_name);
        contectProjectManager.setOnClickListener(this);

        // ตั้งค่า Developer
        contectDeveloper = findViewById(R.id.contectDeveloper);
        detailDeveloper = findViewById(R.id.detailDeveloper);
        imageView_dp = findViewById(R.id.imageView_dp);
        textView_dp_title = findViewById(R.id.textView_dp_title);
        textView_dp_name = findViewById(R.id.textView_dp_name);
        contectDeveloper.setOnClickListener(this);

        // ตั้งค่า UI/UX
        contectUIUX = findViewById(R.id.contectUIUX);
        detailUIUX = findViewById(R.id.detailUIUX);
        imageView_UIUX = findViewById(R.id.imageView_UIUX);
        textView_UIUX_title = findViewById(R.id.textView_UIUX_title);
        textView_UIUX_name = findViewById(R.id.textView_UIUX_name);
        contectUIUX.setOnClickListener(this);

        // ตั้งค่า Research
        contectResearch = findViewById(R.id.contectResearch);
        detailResearch = findViewById(R.id.detailResearch);
        imageView_RS = findViewById(R.id.imageView_Research);
        textView_RS_title = findViewById(R.id.textView_RS_title);
        textView_RS_name = findViewById(R.id.textView_RS_name);
        contectResearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.contectProjectManager) {
            toggleSection(detailProjectManager, imageView_pm, textView_pm_title, textView_pm_name);
        } else if (id == R.id.contectDeveloper) {
            toggleSection(detailDeveloper, imageView_dp, textView_dp_title, textView_dp_name);
        } else if (id == R.id.contectUIUX) {
            toggleSection(detailUIUX, imageView_UIUX, textView_UIUX_title, textView_UIUX_name);
        } else if (id == R.id.contectResearch) {
            toggleSection(detailResearch, imageView_RS, textView_RS_title, textView_RS_name);
        }
    }

    private void toggleSection(View detailView, ImageView imageView, TextView titleView, TextView nameView) {
        if (detailView.getVisibility() == View.GONE) {
            expand(detailView, imageView, titleView, nameView);
        } else {
            collapse(detailView, imageView, titleView, nameView);
        }
    }

    private void expand(View detailView, ImageView imageView, TextView titleView, TextView nameView) {
        detailView.setVisibility(View.VISIBLE);

        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        detailView.measure(widthSpec, heightSpec);
        int targetHeight = detailView.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = detailView.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            detailView.setLayoutParams(params);
        });
        animator.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.8f);
        scaleX.setDuration(500);
        scaleY.setDuration(500);
        scaleX.start();
        scaleY.start();

        ValueAnimator textSizeAnimator = ValueAnimator.ofFloat(24f, 29f);
        textSizeAnimator.setDuration(500);
        textSizeAnimator.addUpdateListener(animation -> titleView.setTextSize((float) animation.getAnimatedValue()));
        textSizeAnimator.start();

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(nameView, "alpha", 1f, 0f);
        fadeOut.setDuration(350);
        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                nameView.setVisibility(View.GONE);
            }
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        });
        fadeOut.start();
    }

    private void collapse(View detailView, ImageView imageView, TextView titleView, TextView nameView) {
        int initialHeight = detailView.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = detailView.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            detailView.setLayoutParams(params);
        });
        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                detailView.setVisibility(View.GONE);
            }
        });
        animator.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.8f, 1f);
        scaleX.setDuration(500);
        scaleY.setDuration(500);
        scaleX.start();
        scaleY.start();

        ValueAnimator textSizeAnimator = ValueAnimator.ofFloat(29f, 24f);
        textSizeAnimator.setDuration(500);
        textSizeAnimator.addUpdateListener(animation -> titleView.setTextSize((float) animation.getAnimatedValue()));
        textSizeAnimator.start();

        nameView.setVisibility(View.VISIBLE);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(nameView, "alpha", 0f, 1f);
        fadeIn.setDuration(1200);
        fadeIn.start();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.action_about;
    }

}