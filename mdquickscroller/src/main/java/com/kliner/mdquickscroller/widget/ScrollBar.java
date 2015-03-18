package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by klikli on 2015/3/17.
 */
public class ScrollBar extends View {

    private int width = 0;

    public ScrollBar(Context context) {
        super(context);
    }

    public ScrollBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(int width, int height, int totalWidth) {
        final RelativeLayout.LayoutParams handleParams = new RelativeLayout.LayoutParams(width, height);
        handleParams.rightMargin = (totalWidth - width) / 2;
        handleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.setLayoutParams(handleParams);
        this.width = width;
    }

    /**
     * Set the colors of the handlebar.
     *
     * @param inactive     - color of the inactive handlebar
     * @param activeBase   - base color of the active handlebar
     * @param activeStroke - stroke of the active handlebar
     */
    public void setHandlebarColor(final int inactive, final int activeBase, final int activeStroke) {
        final float density = getResources().getDisplayMetrics().density;
        final GradientDrawable bg_inactive = new GradientDrawable();
        bg_inactive.setCornerRadius(density);
        bg_inactive.setColor(inactive);
        bg_inactive.setStroke(width / 2, Color.TRANSPARENT);
        final GradientDrawable bg_active = new GradientDrawable();
        bg_active.setCornerRadius(density);
        bg_active.setColor(activeBase);
        bg_active.setStroke(width / 2, activeStroke);
        final StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected}, bg_active);
        states.addState(new int[]{android.R.attr.state_enabled}, bg_inactive);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            this.setBackgroundDrawable(states);
        else
            this.setBackground(states);
    }
}
