package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kliner.mdquickscroller.util.UiUtil;

public class PopupPin extends TextView implements Pin {

    private Context mContext;
    public static final int GREY_LIGHT = Color.parseColor("#f0888888");
    public static final int GREY_DARK = Color.parseColor("#e0585858");

    public PopupPin(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PopupPin(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        this.setVisibility(View.INVISIBLE);

        this.setTextColor(Color.WHITE);
        this.setVisibility(View.INVISIBLE);
        this.setGravity(Gravity.CENTER);
        final RelativeLayout.LayoutParams popupParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.setLayoutParams(popupParams);
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
        setPopupColor(GREY_LIGHT, GREY_DARK, 1, Color.WHITE, 1);
        int padding = UiUtil.dp2px(mContext, 4);
        this.setPadding(padding, padding, padding, padding);
    }

    @Override
    public TextView getTextView() {
        return this;
    }

    /**
     * Sets the popup colors, when QuickScroll.TYPE_POPUP is selected as type.
     * <p/>
     *
     * @param backgroundColor the background color of the TextView
     * @param borderColor     the background color of the border surrounding the TextView
     * @param textColor       the color of the text
     */
    public void setPopupColor(final int backgroundColor, final int borderColor, final int borderWidthDPI, final int textColor, float cornerRadiusDPI) {

        final GradientDrawable popupBackground = new GradientDrawable();
        popupBackground.setCornerRadius(cornerRadiusDPI * getResources().getDisplayMetrics().density);
        popupBackground.setStroke((int) (borderWidthDPI * getResources().getDisplayMetrics().density), borderColor);
        popupBackground.setColor(backgroundColor);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            this.setBackgroundDrawable(popupBackground);
        else
            this.setBackground(popupBackground);

        this.setTextColor(textColor);
    }
}
