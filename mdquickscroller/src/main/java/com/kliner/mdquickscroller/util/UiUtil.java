package com.kliner.mdquickscroller.util;

import android.content.Context;

/**
 * Created by klikli on 2015/3/18.
 */
public class UiUtil {
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
