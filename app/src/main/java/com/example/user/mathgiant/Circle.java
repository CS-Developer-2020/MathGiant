package com.example.user.mathgiant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class Circle extends View {

    private static final int START_ANGLE_POINT = 0;

    private final Paint paint;
    private final RectF rect;


    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 90;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);



        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 200 + strokeWidth, 200 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setColor(int color1) {

        int color = ContextCompat.getColor(getContext(), R.color.empty);
       int color2 = ContextCompat.getColor(getContext(), R.color.darkOrange);


        if(color1 == 1){
        // paint.setColor(getResources().getColor(R.color.empty));
            paint.setColor(color);
        }
        else{
            paint.setColor(color2);
           // paint.setColor(getResources().getColor(R.color.red));
        }
    }
}