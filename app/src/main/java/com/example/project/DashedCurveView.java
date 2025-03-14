package com.example.yourapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.view.View;

public class DashedCurveView extends View {
    private Paint paint;
    private Path path;

    public DashedCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF000000); // สีดำ
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        // ทำเส้นประ
        PathEffect effects = new DashPathEffect(new float[]{20, 15}, 0);
        paint.setPathEffect(effects);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

        // เชื่อมปุ่ม 1 → 2
        path.moveTo(600, 200);
        path.quadTo(400, 200, 280, 400);

        // เชื่อมปุ่ม 2 → 3
        path.moveTo(280, 400);
        path.quadTo(300, 600, 600, 600);

        // เชื่อมปุ่ม 3 → 4
        path.moveTo(400, 800);
        path.quadTo(500, 900, 700, 700);

        // เชื่อมปุ่ม 4 → 5
        path.moveTo(280, 800);
        path.quadTo(320, 1100, 700, 1100);

        canvas.drawPath(path, paint);
    }
}
