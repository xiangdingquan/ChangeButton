package org.xdq.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.xdq.library.listener.CenterClickListener;
import org.xdq.library.listener.SeparateClickListener;
import org.xdq.library.widget.CenterButton;
import org.xdq.library.widget.SeparatedButton;

/**
 * Created by  xiangdingquan  on 2018/4/16.
 */
public class ChanggeButtonLayout extends FrameLayout {


    private Context mContext;


    private int mWidth, mHeight, mSize;

    private CenterButton mCenter;

    private SeparatedButton mCancle, mConfirm;


    private SeparateClickListener mSeparateClickListener;

    private CenterClickListener mCenterClickListener;


    public ChanggeButtonLayout(Context context) {
        this(context, null);
    }

    public ChanggeButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChanggeButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(outMetrics);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mWidth = outMetrics.widthPixels;
            } else {
                mWidth = outMetrics.widthPixels / 2;
            }
        }
        mSize = (int) (mWidth / 4.5f);
        mHeight = mSize + mSize / 2;
        initViews();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    public void startAnimator() {
        mCenter.setVisibility(GONE);
        mCancle.setVisibility(VISIBLE);
        mConfirm.setVisibility(VISIBLE);
        mCancle.setClickable(false);
        mConfirm.setClickable(false);
        ObjectAnimator animator_cancel = ObjectAnimator.ofFloat(mCancle, "translationX", mWidth / 4, 0);
        ObjectAnimator animator_confirm = ObjectAnimator.ofFloat(mConfirm, "translationX", -mWidth / 4, 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_cancel, animator_confirm);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCancle.setClickable(true);
                mConfirm.setClickable(true);
            }
        });
        set.setDuration(200);
        set.start();
    }


    private void initViews() {
        setWillNotDraw(false);
        mCenter = new CenterButton(mContext, mSize);
        LayoutParams centerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerParams.gravity = Gravity.CENTER;
        mCenter.setLayoutParams(centerParams);
        mCenter.setCallback(new CenterButton.CenterCallback() {
            @Override
            public void callBack() {
                startAnimator();
                if (mCenterClickListener != null)
                    mCenterClickListener.click();
            }
        });

        //取消按钮
        mCancle = new SeparatedButton(getContext(), SeparatedButton.TYPE_CANCLE, mSize);
        LayoutParams cancelParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        cancelParam.gravity = Gravity.CENTER_VERTICAL;
        cancelParam.setMargins((mWidth / 4) - mSize / 2, 0, 0, 0);
        mCancle.setLayoutParams(cancelParam);
        mCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLayout();
                if (mSeparateClickListener != null)
                    mSeparateClickListener.onCancle();
            }
        });

        //确认按钮
        mConfirm = new SeparatedButton(getContext(), SeparatedButton.TYPE_CONFIRM, mSize);
        LayoutParams cofirmParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        cofirmParam.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        cofirmParam.setMargins(0, 0, (mWidth / 4) - mSize / 2, 0);
        mConfirm.setLayoutParams(cofirmParam);
        mConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLayout();
                if (mSeparateClickListener != null)
                    mSeparateClickListener.onConfirm();
            }
        });


        this.addView(mCenter);
        this.addView(mConfirm);
        this.addView(mCancle);

        mCancle.setVisibility(GONE);
        mConfirm.setVisibility(GONE);
    }


    public void resetLayout() {
        mCancle.setVisibility(GONE);
        mConfirm.setVisibility(GONE);
        mCenter.setVisibility(VISIBLE);
    }

    public void setSeparateClickListener(SeparateClickListener separateClickListener) {
        mSeparateClickListener = separateClickListener;
    }

    public void setCenterClickListener(CenterClickListener centerClickListener) {
        mCenterClickListener = centerClickListener;
    }


}
