package org.xdq.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by  xiangdingquan  on 2018/4/16.
 * <p>
 * 点击一分二的按钮
 */
public class SeparatedButton extends View {

    /**
     * 取消按钮
     */
    public static final int TYPE_CANCLE = 0X00001;

    /**
     * 确认按钮
     */
    public static final int TYPE_CONFIRM = 0x00002;


    private int mType = 0;

    private int mSize;

    private float mCenterX;

    private float mCenterY;

    private float mRadius;

    private Paint mPaint;

    private Path mPath;

    private float mStrokeWidth;

    private float mArrow;

    private RectF mRectF;

    public SeparatedButton(Context context){
        super(context);
    }


    public SeparatedButton(Context context, int type, int size) {
        super(context);
        init(type, size);
    }

    private void init(int type, int size) {
        mType = type;
        mSize = size;
        mRadius = size / 2.0f;
        mCenterX = size / 2.0f;
        mCenterY = size / 2.0f;
        mPaint = new Paint();
        mPath = new Path();
        mStrokeWidth = size / 50.0f;
        mArrow = size / 10.0f;
        mRectF = new RectF(mCenterX, mCenterY - mArrow, mCenterX + mArrow * 2, mCenterY + mArrow);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mType) {
            case TYPE_CANCLE:
                drawCancleView(canvas);
                break;
            case TYPE_CONFIRM:
                drawConfirmView(canvas);
                break;
        }


    }

    private void drawConfirmView(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(0xFF00CC00);
        mPath.moveTo(mCenterX - mSize / 6.0f, mCenterY);
        mPath.lineTo(mCenterX - mSize / 20.0f, mCenterY + mSize / 7.0f);
        mPath.lineTo(mCenterX + mSize / 4.0f, mCenterY - mSize / 8.0f);
        mPath.lineTo(mCenterX - mSize / 20.0f, mCenterY + mSize / 8.5f);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawCancleView(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xEEDCDCDC);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);


        mPath.moveTo(mCenterX - mArrow / 2.0f, mCenterY + mArrow);
        mPath.lineTo(mCenterX + mArrow, mCenterY + mArrow);
        mPath.arcTo(mRectF, 90, -180);
        mPath.lineTo(mCenterX - mArrow, mCenterY - mArrow);
        canvas.drawPath(mPath, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();
        mPath.moveTo(mCenterX - mArrow, mCenterY - mArrow * 1.5f);
        mPath.lineTo(mCenterX - mArrow, mCenterY - mArrow / 2.5f);
        mPath.lineTo(mCenterX - mArrow * 1.5f, mCenterY - mArrow);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
