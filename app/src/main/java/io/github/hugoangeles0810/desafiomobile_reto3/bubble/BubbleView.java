package io.github.hugoangeles0810.desafiomobile_reto3.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.AttributeSet;

import io.github.hugoangeles0810.desafiomobile_reto3.R;


public class BubbleView extends android.support.v7.widget.AppCompatButton {

    private static final String TAG = BubbleView.class.getSimpleName();
    private static final int BG_DEFAULT = Color.CYAN;

    private int mToX, mToY;
    private int mFromX, mFromY;

    private Boolean mDrawLine;

    private AnimatorListenerAdapter mHideAnimationAdapter;
    private AnimatorListenerAdapter mShowAnimationAdapter;

    public BubbleView(Context context) {
        super(context);
        mShowAnimationAdapter = getShowAnimationAdapter();
        mHideAnimationAdapter = getHideAnimationAdapter();
        setVisibility(GONE);

        mDrawLine = false;
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShowAnimationAdapter = getShowAnimationAdapter();
        mHideAnimationAdapter = getHideAnimationAdapter();

        TypedArray data = context.obtainStyledAttributes(attrs, R.styleable.BubbleView);
        int background = data.getColor(R.styleable.BubbleView_bubble_background, BG_DEFAULT);
        ShapeDrawable drawable = Utils.getRoundedDrawable(
                Math.min(getMeasuredWidth(), getMeasuredHeight()), background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }

        mDrawLine = false;
        setVisibility(INVISIBLE);
        data.recycle();
    }

    public void setPositions(int toX, int toY,
                             int fromX, int fromY) {
        mToX = toX;
        mToY = toY;
        mFromX = fromX;
        mFromY = fromY;
    }

    public int getCenterToX() {
        return mToX + getMeasuredWidth()/2;
    }

    public int getCenterToY() {
        return mToY + getMeasuredHeight()/2;
    }

    public int getCenterFromX() {
        return mFromX + getMeasuredWidth()/2;
    }

    public int getCenterFromY() {
        return mFromY + getMeasuredHeight()/2;
    }

    public boolean canDrawLine() {
        return mDrawLine;
    }

    public void animatedShow() {
        setVisibility(VISIBLE);
        clearAnimation();
        animate()
                .x(mToX)
                .y(mToY)
                .setListener(mShowAnimationAdapter)
                .setStartDelay(0)
                .start();
    }

    public void animatedHide(int delay) {
        clearAnimation();
        animate()
                .x(mFromX)
                .y(mFromY)
                .setListener(mHideAnimationAdapter)
                .setStartDelay(delay)
                .start();
    }

    public AnimatorListenerAdapter getHideAnimationAdapter() {
        return new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mDrawLine = false;
                invalidateParent();
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(INVISIBLE);
                super.onAnimationEnd(animation);
            }
        };
    }

    public AnimatorListenerAdapter getShowAnimationAdapter() {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDrawLine = true;
                invalidateParent();
                super.onAnimationEnd(animation);
            }
        };
    }

    private void invalidateParent() {
        if (getParent() != null && getParent() instanceof BubbleGroup) {
            ((BubbleGroup) getParent()).invalidate();
        }
    }
}
