package com.example.project;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class test extends BaseActivity {
    private Database database;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_test);

        textView = findViewById(R.id.textView);

        database = new Database(this);

        Cursor res = database.getAllScore();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("ScoreID : " + res.getString(0)+"\n");
                stringBuffer.append("UserID : " + res.getString(1)+"\n");
                stringBuffer.append("LessonID : " + res.getString(2)+"\n");
                stringBuffer.append("Score : " + res.getString(3)+"\n");
                stringBuffer.append("Status : " + res.getString(4)+"\n");
            }
            textView.setText(stringBuffer.toString());
            Toast.makeText(this, "ดึงข้อมูลได้", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
        }

    }
}