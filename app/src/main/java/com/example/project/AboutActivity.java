package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout contectProjectManager, contectDeveloper, contectUIUX, contectReserach;

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

        contectProjectManager = findViewById(R.id.contectProjectManager);
        contectDeveloper = findViewById(R.id.contectDeveloper);
        contectUIUX = findViewById(R.id.contectUIUX);
        contectReserach = findViewById(R.id.contectResearch);

        contectProjectManager.setOnClickListener(this);
        contectDeveloper.setOnClickListener(this);
        contectUIUX.setOnClickListener(this);
        contectReserach.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.contectProjectManager) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:s6506021620211@email.kmutnb.ac.th"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email for JavaQuest");

            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.contectDeveloper) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:s6506021620016@email.kmutnb.ac.th"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email for JavaQuest");

            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.contectUIUX) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:s6506021620172@email.kmutnb.ac.th"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email for JavaQuest");

            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.contectResearch) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:s6506021620202@email.kmutnb.ac.th"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email for JavaQuest");

            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
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