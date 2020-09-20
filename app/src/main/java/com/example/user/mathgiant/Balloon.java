package com.example.user.mathgiant;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class Balloon extends AppCompatImageView
        implements View.OnTouchListener,
        Animator.AnimatorListener,
        ValueAnimator.AnimatorUpdateListener {

    // public static final String TAG = "Balloon";

    private BalloonListener mListener;

    private boolean mPopped;
    private PlayActivity playActivity;
    private ValueAnimator mAnimator;


    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int balloonID, int rawHeight) {
        super(context);
        Paint paint = new Paint();
        paint.setTextSize(10);
        paint.setColor(Color.BLACK);
        Canvas canvas = new Canvas();
        canvas.drawText("12",this.getX(),this.getY(),paint);

        this.mListener = (BalloonListener) context;

        this.setImageResource(balloonID);
        //   this.setColorFilter(color);
        int rawWidth = rawHeight / 2;


        //Calc balloon height and width as dp
        int dpHeight = PixelHelper.pixelsToDp(rawHeight, context);
        int dpWidth = PixelHelper.pixelsToDp(rawWidth, context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        setLayoutParams(params);
        //  setOnTouchListener(this);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public  void releaseBalloon(int screenHeight, int duration) {



        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);
        mAnimator.setFloatValues(screenHeight, screenHeight - 780);//better then int value - more for moving.
        //     screenHeight = the start of the balloon.0f = the ending value.
        mAnimator.setFloatValues(screenHeight+780, screenHeight+350);//better then int value - more for moving.

        mAnimator.setInterpolator(new LinearInterpolator());//how to animate the object
        mAnimator.setTarget(this);
        mAnimator.addListener(this);//implements the listener.
        mAnimator.addUpdateListener(this);//implements the listener.
        mAnimator.start();

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //i'm getting here each time that the animate value is changing.
        // here i find what the new y position.
        setY((Float) animation.getAnimatedValue());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mPopped && event.getAction() == MotionEvent.ACTION_DOWN){
            mListener.popBalloon(this,true,mAnimator);
            mPopped = true;


        }

        // return super.onTouchEvent(event);
        return true;
    }

    public void pause() {
        mAnimator.pause();
    }

    public interface BalloonListener {
        void popBalloon(Balloon balloon, boolean touched, ValueAnimator  mAnimator);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
//      This means the balloon got to the top of the screen

        mListener.popBalloon(this,false,this.mAnimator);

    }


    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

    public Boolean setPopped(boolean popped) {
        return this.mPopped = popped;



    }
    public  boolean getPopped(){
        return mPopped;
    }

}