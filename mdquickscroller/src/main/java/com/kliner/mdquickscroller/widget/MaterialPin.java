package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kliner.mdquickscroller.R;

public class MaterialPin extends RelativeLayout implements Pin {

    private Context mContext;
    private TextView mTextView;

    public MaterialPin(Context context, int size, int color, int textSize, int textColor) {
        super(context);
        mContext = context;
        init(size, color, textSize, textColor);
    }

    public MaterialPin(Context context) {
        super(context);
        mContext = context;
    }

    public MaterialPin(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init(int size, int color, int textSize, int textColor) {
        this.setVisibility(View.INVISIBLE);
        mTextView = new TextView(mContext);
//        int size = AndroidUtil.dp2px(mContext, 88);
        mTextView.setGravity(Gravity.CENTER);
        Drawable bg = getResources().getDrawable(R.drawable.pin_bg);
        bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mTextView.setBackgroundDrawable(bg);
        mTextView.setTextColor(textColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        final LayoutParams pinParams = new LayoutParams(size, size);
        pinParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mTextView.setLayoutParams(pinParams);
        this.addView(mTextView);
    }

    @Override
    public TextView getTextView() {
        return mTextView;
    }
}
