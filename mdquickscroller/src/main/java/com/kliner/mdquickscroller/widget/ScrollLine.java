package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by klikli on 2015/3/17.
 */
public class ScrollLine extends View {

    protected static final int SCROLLBAR_MARGIN = 10;

    public ScrollLine(Context context) {
        super(context);
    }

    public ScrollLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int color) {
        this.setBackgroundColor(color);
    }

    public void init(int width, int totalWidth) {
        final RelativeLayout.LayoutParams scrollBarParams = new RelativeLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        scrollBarParams.topMargin = SCROLLBAR_MARGIN;
        scrollBarParams.rightMargin = (totalWidth - width) / 2;
        scrollBarParams.bottomMargin = SCROLLBAR_MARGIN;
        this.setLayoutParams(scrollBarParams);

    }
}
