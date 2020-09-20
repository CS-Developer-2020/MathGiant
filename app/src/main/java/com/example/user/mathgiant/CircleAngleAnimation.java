package com.example.user.mathgiant;

import android.view.animation.Animation;
import android.view.animation.Transformation;



public class CircleAngleAnimation extends Animation {

    private Circle circle;
private int color;
    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle, int newAngle,int color) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;
        this.color = color;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();

        circle.setColor(color);
    }
}