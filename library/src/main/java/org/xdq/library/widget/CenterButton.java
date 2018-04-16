package org.xdq.library.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by  xiangdingquan  on 2018/4/16.
 */
public class CenterButton extends View {


    private Paint mPaint;
    private float mRadius;              //外圆半径
    private float mInRadius;            //内圆半径

    private final static int INSIDE_COLOR = 0xEEDCDCDC;

    private final static int  OUTSIDE_COLOR= 0xFFFFFFFF;

    private int mWidth, mHeight;

    private Rect mRect;

    private CenterCallback mCallback;

    public CenterButton(Context context) {
        super(context);
    }


    public CenterButton(Context context, int size) {
        super(context);
        mRadius = size / 2.0f;
        mInRadius = mRadius * 0.75f;
        mWidth = size ;
        mHeight = size ;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRect = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(OUTSIDE_COLOR);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        mPaint.setColor(INSIDE_COLOR);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mInRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                handlerPress();
                performClick();
                break;
        }
        return true;
    }



    private void handlerPress() {
        mInRadius = mRadius * 0.75f;
        ValueAnimator animator = ValueAnimator.ofFloat(mInRadius, mInRadius * 0.75f, mInRadius);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mInRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mCallback != null)
                    mCallback.callBack();
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setCallback(CenterCallback callback) {
        mCallback = callback;
    }

    public interface CenterCallback {
        void callBack();
    }

}
