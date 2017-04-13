package io.github.hugoangeles0810.desafiomobile_reto3.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.github.hugoangeles0810.desafiomobile_reto3.R;

/**
 * Created by hugo on 12/04/17.
 */

public class BubbleGroup extends ViewGroup {

    private static final String TAG = BubbleGroup.class.getSimpleName();

    public static final int DEFAULT_RADIO_DP = 100;
    public static final int INNER_PADDING_DP = 8;

    private Button mMainButton;

    private float mMainRadio;

    private int mMaxChildWidth;
    private int mMaxChildHeight;

    private int mCurrentChildIndex;

    private OnBubbleClickListener mBubbleClickListener;

    private Paint mLinePaint;

    public interface OnBubbleClickListener {
        void onBubbleClick(int index, BubbleView bubble);
    }

    public BubbleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(2f);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleGroup);

        mMainRadio = typedArray.getDimension(R.styleable.BubbleGroup_bubble_control_radio,
                Utils.dpToPxFloat(DEFAULT_RADIO_DP));

        buildMainButton(typedArray);

        typedArray.recycle();
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setupChildMeasures();

        int minW = getPaddingLeft() + getPaddingRight() +
                (int)mMainRadio*2 + mMaxChildWidth +
                Utils.dpToPx(INNER_PADDING_DP);

        int w = resolveSizeAndState(minW, widthMeasureSpec, 1);

        int minH = getPaddingBottom() + getPaddingTop() +
                (int)mMainRadio*2 + mMaxChildHeight +
                Utils.dpToPx(INNER_PADDING_DP);
        int h = resolveSizeAndState(minH, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Boundaries
        int parentLeft = getPaddingLeft();
        int parentTop = getPaddingTop();
        int parentRight = getMeasuredWidth() - getPaddingRight();
        int parentBottom = getMeasuredHeight() - getPaddingBottom();
        int parentWidth = parentRight - parentLeft;
        int parentHeight = parentBottom - parentTop;

        int centerWidth = parentWidth/2;
        int centerHeight = parentHeight/2;

        positionMainButton(parentWidth, parentHeight);

        double angle = Math.toRadians(180);
        double fraction = -Math.toRadians(360d/(getChildCount()-1));

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView == mMainButton) continue;

            if (!(childView instanceof BubbleView)) {
                throw new RuntimeException("BubbleGroup only must have BubbleView childs");
            }

            setupBubbleListener(childView, i-1);

            BubbleView bubbleView = (BubbleView) childView;

            int childWidth = bubbleView.getMeasuredWidth();
            int childHeight = bubbleView.getMeasuredHeight();

            int toX = (int) (centerWidth + mMainRadio * Math.cos(angle) - childWidth / 2);
            int toY = (int) (centerHeight - mMainRadio * Math.sin(angle) - childHeight / 2);

            int fromX = (centerWidth - childWidth / 2);
            int fromY = (centerHeight - childHeight / 2);

            bubbleView.setPositions(toX, toY, fromX, fromY);
            childView.layout(fromX, fromY, fromX + childWidth, fromY + childHeight);


            angle += fraction;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 1; i < getChildCount(); i++) {
            BubbleView bubbleView = (BubbleView) getChildAt(i);
            if (bubbleView.canDrawLine()) {
                canvas.drawLine(
                        bubbleView.getCenterToX(), bubbleView.getCenterToY(),
                        bubbleView.getCenterFromX(), bubbleView.getCenterFromY(),
                        mLinePaint);
            }
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        setupBubbleListener(child, getChildCount()-1);
    }


    public void setOnBubbleClickListener(OnBubbleClickListener bubbleClickListener) {
        mBubbleClickListener = bubbleClickListener;
    }

    private void setupBubbleListener(View child, final int index) {
        if (child instanceof BubbleView && mBubbleClickListener != null) {
            final BubbleView bubbleView = (BubbleView) child;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBubbleClickListener.onBubbleClick(index, bubbleView);
                }
            });
        }
    }

    private void buildMainButton(TypedArray typedArray) {
        mMainButton = new Button(getContext());

        float size = typedArray.getDimension(R.styleable.BubbleGroup_bubble_control_size, Utils.dpToPx(DEFAULT_RADIO_DP));
        String btnTitle = typedArray.getString(R.styleable.BubbleGroup_bubble_control_text);
        int backgroundColor = typedArray.getColor(R.styleable.BubbleGroup_bubble_control_color, Color.CYAN);
        ShapeDrawable drawable = Utils.getRoundedDrawable(size, backgroundColor);

        mMainButton.setHeight((int) size);
        mMainButton.setWidth((int) size);
        mMainButton.setText(btnTitle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMainButton.setBackground(drawable);
        } else {
            mMainButton.setBackgroundDrawable(drawable);
        }

        mMainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentChildIndex >= 1 && mCurrentChildIndex < getChildCount()) {
                    BubbleView bubbleView = (BubbleView) getChildAt(mCurrentChildIndex);
                    bubbleView.animatedShow();
                    mCurrentChildIndex++;
                    return;
                }

                if (mCurrentChildIndex >= getChildCount()) {
                    int delay = 0;
                    int delayAcum = 100;
                    for (int i = getChildCount()-1; i >= 1; i--) {
                        BubbleView bubbleView = (BubbleView) getChildAt(i);
                        bubbleView.animatedHide(delay);
                        delay+=delayAcum;
                    }
                    mCurrentChildIndex = getInitialChildIndex();
                }
            }
        });

        addView(mMainButton);
    }

    private void positionMainButton(int parentWidth, int parentHeight) {

        mMainButton.measure(
                MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.AT_MOST));

        int btnWidth = mMainButton.getMeasuredWidth();
        int btnHeight = mMainButton.getMeasuredHeight();

        int left = parentWidth/2 - btnWidth/2;
        int top = parentHeight/2 - btnHeight/2;

        mMainButton.layout(left, top, left + btnWidth, top + btnHeight);
    }

    private void setupChildMeasures() {
        int maxWidth = -1;
        int maxHeight = -1;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            if (mMainButton == childView) continue;

            childView.measure(
                    MeasureSpec.makeMeasureSpec(childView.getLayoutParams().width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childView.getLayoutParams().height, MeasureSpec.EXACTLY));

            if (maxWidth < childView.getMeasuredWidth()) {
                maxWidth = childView.getMeasuredWidth();
            }

            if (maxHeight < childView.getMeasuredHeight()) {
                maxHeight = childView.getMeasuredHeight();
            }
        }

        mMaxChildWidth = maxWidth;
        mMaxChildHeight = maxHeight;
        mCurrentChildIndex = getInitialChildIndex();
    }

    private int getInitialChildIndex() {
        return getChildCount() > 1 ? 1 : -1;
    }
}
