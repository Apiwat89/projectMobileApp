package com.example.yourapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DashedCurveView extends View {
    private Paint paint;
    private Path path;
    private View[] buttons;

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

    public void setButtons(View... btns) {
        if (btns.length < 2) return; // ต้องมีปุ่มอย่างน้อย 2 ปุ่ม
        this.buttons = btns;
        postDelayed(this::updatePath, 100); // รอให้ Layout เสร็จก่อน
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updatePath(); // อัปเดตเส้นเมื่อ Layout เปลี่ยนแปลง
    }

    private float[] getButtonCenter(View button) {
        int[] buttonLoc = new int[2];
        int[] parentLoc = new int[2];

        button.getLocationOnScreen(buttonLoc);
        this.getLocationOnScreen(parentLoc); // หาตำแหน่งของ DashedCurveView

        float x = buttonLoc[0] - parentLoc[0] + (button.getWidth() / 2f);
        float y = buttonLoc[1] - parentLoc[1] + (button.getHeight() / 2f);

        return new float[]{x, y};
    }


    private void updatePath() {
        if (buttons == null || buttons.length < 2) return;

        path.reset();
        debugButtonPositions();

        float[][] points = new float[buttons.length][2];
        for (int i = 0; i < buttons.length; i++) {
            points[i] = getButtonCenter(buttons[i]);
        }

        path.moveTo(points[0][0], points[0][1]);

        for (int i = 1; i < points.length; i++) {
            float midX = (points[i - 1][0] + points[i][0]) / 2;
            float midY = (points[i - 1][1] + points[i][1]) / 2 - 300; // เพิ่มค่าความโค้ง

            path.quadTo(midX, midY, points[i][0], points[i][1]);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (path.isEmpty()) return;

        canvas.drawPath(path, paint);
    }

    private void debugButtonPositions() {
        if (buttons == null) return;
        for (int i = 0; i < buttons.length; i++) {
            float[] pos = getButtonCenter(buttons[i]);
            Log.d("ButtonPositions", String.format("Btn%d: (%.1f, %.1f)", i + 1, pos[0], pos[1]));
        }
    }
}
