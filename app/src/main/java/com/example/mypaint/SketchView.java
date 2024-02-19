package com.example.mypaint;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class SketchView extends View {


    public static Paint paint = new Paint();

    int currentColor = Color.BLACK;
    private Path path = new Path();

    List<Path> paths = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    public SketchView(Context context) {
        super(context);
    }

    public SketchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < paths.size(); i++) {
            paint.setColor(colors.get(i));
            canvas.drawPath(paths.get(i), paint);
        }
        canvas.drawPath(path, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paths.add(new Path(path));
                colors.add(currentColor);
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                paths.add(new Path(path));
                colors.add(currentColor);
                path.lineTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_UP:
                paths.add(new Path(path));
                colors.add(currentColor);
                path.reset();
            default:
                return false;
        }
        postInvalidate();
        return true;
    }

    public void setCurrentColor(int color) {
        currentColor = color;
        paint.setColor(color);
    }

    public void undo() {
        if (!paths.isEmpty()) {
            paths.remove(paths.size() - 1);
            colors.remove(colors.size() - 1);
            invalidate();
        }
    }
}

