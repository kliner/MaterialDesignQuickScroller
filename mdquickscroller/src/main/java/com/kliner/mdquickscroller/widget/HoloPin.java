package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kliner.mdquickscroller.util.UiUtil;

public class HoloPin extends RelativeLayout implements Pin {

    private Context mContext;
    public static final int GREY_LIGHT = Color.parseColor("#f0888888");
    private TextView mTextView;

    public HoloPin(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HoloPin(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private void init() {
        this.setVisibility(View.INVISIBLE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.setLayoutParams(layoutParams);

        mTextView = new TextView(mContext);
        final LayoutParams indicatorParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextView.setId(200);
        mTextView.setLayoutParams(indicatorParams);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setBackgroundColor(GREY_LIGHT);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
        this.addView(mTextView);

        final HoloPinIndicatorView pin = new HoloPinIndicatorView(mContext);
        final LayoutParams pinParams = new LayoutParams(UiUtil.dp2px(mContext, 10), ViewGroup.LayoutParams.WRAP_CONTENT);
        pinParams.addRule(RelativeLayout.ALIGN_TOP, 200);
        pinParams.addRule(RelativeLayout.ALIGN_BOTTOM, 200);
        pinParams.addRule(RelativeLayout.RIGHT_OF, 200);
        pin.setLayoutParams(pinParams);
        this.addView(pin);
    }

    @Override
    public TextView getTextView() {
        return mTextView;
    }

    public class HoloPinIndicatorView extends View {

        private final int mPinColor = Color.argb(224, 66, 66, 66);
        private Paint mPaint;
        private Path mPath;

        public HoloPinIndicatorView(Context context) {
            super(context);
            init();
        }

        public HoloPinIndicatorView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public HoloPinIndicatorView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public void setColor(int color) {
            mPaint.setColor(color);
        }

        private void init() {
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            setColor(mPinColor);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (changed) {
                mPath.reset();
                mPath.moveTo(0, getHeight());
                mPath.lineTo(getWidth(), getHeight() / 2);
                mPath.lineTo(0, 0);
                mPath.close();
            }
            super.onLayout(changed, left, top, right, bottom);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(mPath, mPaint);
            super.onDraw(canvas);
        }
    }
}
