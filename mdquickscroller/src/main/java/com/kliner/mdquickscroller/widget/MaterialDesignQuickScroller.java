package com.kliner.mdquickscroller.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kliner.mdquickscroller.R;
import com.kliner.mdquickscroller.util.UiUtil;

public class MaterialDesignQuickScroller extends View {

    // type statics
    public static final int TYPE_POPUP = 0;
    public static final int TYPE_INDICATOR = 1;
    public static final int TYPE_MATERIAL = 2;

    // base variables
    protected boolean isScrolling;
    protected AlphaAnimation fadeInAnimation, fadeOutAnimation;
    protected TextView scrollIndicatorTextView;
    protected Scrollable scrollable;
    protected ListView listView;
    protected int groupPosition;
    protected int itemCount;
    protected int type;
    protected boolean isInitialized = false;
    protected ScrollLine scrollLine;
    // handlebar variables
    protected ScrollBar scrollBar;
    // indicator variables
    protected View scrollIndicator;

    private int scrollBarVisible, scrollLineVisible;
    private int scrollBarWidth, scrollBarHeight;
    private int totalWidth;
    private int scrollBarColor, scrollBarFocusColor, scrollBarFocusTransColor;
    private int scrollLineWidth;
    private int scrollLineColor;
    private int materialPinWidth, materialPinColor, materialPinTextSize, materialPinTextColor;

    // default constructors
    public MaterialDesignQuickScroller(Context context) {
        super(context);
    }

    public MaterialDesignQuickScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.md_quick_scroller);
        type = ta.getInt(R.styleable.md_quick_scroller_scrollerStyle, TYPE_MATERIAL);
        scrollBarVisible = ta.getInt(R.styleable.md_quick_scroller_scrollerBarVisible, View.VISIBLE);
        scrollLineVisible = ta.getInt(R.styleable.md_quick_scroller_scrollerLineVisible, View.VISIBLE);
        scrollBarWidth = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_scrollBarWidth, UiUtil.dp2px(context, 8));
        scrollBarHeight = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_scrollBarHeight, UiUtil.dp2px(context, 36));
        totalWidth = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_totalWidth, UiUtil.dp2px(context, 30));
        switch (type) {
            case TYPE_MATERIAL:
                scrollBarColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarColor, Color.rgb(58, 62, 74));
                scrollBarFocusColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarFocusColor, Color.rgb(58, 62, 74));
                scrollBarFocusTransColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarFocusTransColor, Color.argb(0x80, 58, 62, 74));
                break;
            default:
                scrollBarColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarColor, Color.rgb(0x33, 0xB5, 0xE5));
                scrollBarFocusColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarFocusColor, Color.rgb(0x33, 0xB5, 0xE5));
                scrollBarFocusTransColor = ta.getColor(R.styleable.md_quick_scroller_scrollBarFocusTransColor, Color.argb(0x80, 0x33, 0xB5, 0xE5));
                break;
        }
        scrollLineWidth = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_scrollLineWidth, 2);
        scrollLineColor = ta.getColor(R.styleable.md_quick_scroller_scrollLineColor, Color.argb(0x64, 0x40, 0x40, 0x40));
        materialPinWidth = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_materialPinWidth, UiUtil.dp2px(context, 88));
        materialPinColor = ta.getColor(R.styleable.md_quick_scroller_materialPinColor, Color.rgb(0xf4, 0x43, 0x36));
        materialPinTextSize = ta.getDimensionPixelSize(R.styleable.md_quick_scroller_materialPinTextSize, UiUtil.dp2px(context, 45));
        materialPinTextColor = ta.getColor(R.styleable.md_quick_scroller_materialPinTextColor, Color.WHITE);
        ta.recycle();
    }

    public MaterialDesignQuickScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollLineVisible(int visible) {
        scrollLine.setVisibility(visible);
    }

    public void setScrollBarVisible(int visible) {
        scrollBar.setVisibility(visible);
    }

    /**
     * Initializing the QuickScroll, this function must be called.
     * <p/>
     *
     * @param list         the ListView
     * @param scrollable   the adapter, must implement Scrollable interface
     */
    public void init(final ListView list, final Scrollable scrollable) {
        if (isInitialized) return;

//        this.type = scrollerType;
        listView = list;
        this.scrollable = scrollable;
        groupPosition = -1;
        fadeInAnimation = new AlphaAnimation(.0f, 1.0f);
        fadeInAnimation.setFillAfter(true);
        fadeOutAnimation = new AlphaAnimation(1.0f, .0f);
        fadeOutAnimation.setFillAfter(true);
        fadeOutAnimation.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                isScrolling = false;
            }
        });
        isScrolling = false;

        listView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (isScrolling && (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN)) {
                    return true;
                }
                return false;
            }
        });

        final RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        final RelativeLayout container = new RelativeLayout(getContext());
        container.setBackgroundColor(Color.TRANSPARENT);
        containerParams.addRule(RelativeLayout.ALIGN_TOP, getId());
        containerParams.addRule(RelativeLayout.ALIGN_BOTTOM, getId());
        containerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        container.setLayoutParams(containerParams);

        if (this.type == TYPE_POPUP) {
            scrollIndicator = new PopupPin(getContext());
        } else if (this.type == TYPE_MATERIAL) {
            scrollIndicator = new MaterialPin(getContext(), materialPinWidth, materialPinColor, materialPinTextSize, materialPinTextColor);
            setFadeDuration(150);
        } else {
            scrollIndicator = new HoloPin(getContext());
        }

        scrollIndicatorTextView = ((Pin) scrollIndicator).getTextView();
        container.addView(scrollIndicator);

        // setting scrollbar total width
        getLayoutParams().width = totalWidth;

        // scrollbar setup
        scrollLine = new ScrollLine(getContext());
        scrollLine.init(scrollLineWidth, totalWidth);
        scrollLine.setVisibility(scrollLineVisible);
        scrollLine.setColor(scrollLineColor);
        container.addView(scrollLine);

        scrollBar = new ScrollBar(getContext());
        scrollBar.init(scrollBarWidth, scrollBarHeight, totalWidth);
        scrollBar.setVisibility(scrollBarVisible);
        scrollBar.setHandlebarColor(scrollBarColor, scrollBarFocusColor, scrollBarFocusTransColor);
        container.addView(scrollBar);

        listView.setOnScrollListener(new OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isScrolling && scrollBar.getVisibility() == View.VISIBLE && totalItemCount - visibleItemCount > 0) {
                    moveHandlebar(getHeight() * firstVisibleItem / (totalItemCount - visibleItemCount));
                }
            }
        });

        isInitialized = true;

        ViewGroup.class.cast(listView.getParent()).addView(container);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Adapter adapter = listView.getAdapter();

        if (adapter == null)
            return false;

        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }

        itemCount = adapter.getCount();
        if (itemCount == 0)
            return false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (type == TYPE_INDICATOR || type == TYPE_MATERIAL) {
                    scrollIndicator.startAnimation(fadeInAnimation);
                    scrollIndicator.setPadding(0, 0, getWidth(), 0);
                } else {
                    scrollIndicator.startAnimation(fadeInAnimation);
                }
                scroll(event.getY());
                isScrolling = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                scroll(event.getY());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (scrollBar.getVisibility() == View.VISIBLE) {
                    scrollBar.setSelected(false);
                }
                scrollIndicator.startAnimation(fadeOutAnimation);
                return true;
            default:
                return false;
        }
    }

    protected void scroll(final float height) {
        if (type == TYPE_MATERIAL) {
            float move = height - (scrollIndicator.getHeight()) - (scrollBar.getHeight() / 2);

            if (move < 0)
                move = 0;
            else if (height > getHeight() - scrollBar.getHeight() / 2)
                move = getHeight() - scrollBar.getHeight() - scrollIndicator.getHeight();

            ViewHelper.setTranslationY(scrollIndicator, move);
        } else if (type == TYPE_INDICATOR) {
            float move = height - (scrollIndicator.getHeight() / 2);

            if (move < 0)
                move = 0;
            else if (move > getHeight() - scrollIndicator.getHeight())
                move = getHeight() - scrollIndicator.getHeight();

            ViewHelper.setTranslationY(scrollIndicator, move);
        }

        if (scrollBar.getVisibility() == View.VISIBLE) {
            scrollBar.setSelected(true);
            moveHandlebar(height - (scrollBar.getHeight() / 2));
        }

        int position = (int) ((height / getHeight()) * itemCount);
        if (listView instanceof ExpandableListView) {
            final int groupPosition = ExpandableListView.getPackedPositionGroup(((ExpandableListView) listView).getExpandableListPosition(position));
            if (groupPosition != -1)
                this.groupPosition = groupPosition;
        }

        if (position < 0)
            position = 0;
        else if (position >= itemCount)
            position = itemCount - 1;
        scrollIndicatorTextView.setText(scrollable.getIndicatorForPosition(position, groupPosition));
        listView.setSelection(scrollable.getScrollPosition(position, groupPosition));
    }

    protected void moveHandlebar(final float where) {
        float move = where;
        if (move < ScrollLine.SCROLLBAR_MARGIN)
            move = ScrollLine.SCROLLBAR_MARGIN;
        else if (move > getHeight() - scrollBar.getHeight() - ScrollLine.SCROLLBAR_MARGIN)
            move = getHeight() - scrollBar.getHeight() - ScrollLine.SCROLLBAR_MARGIN;

        ViewHelper.setTranslationY(scrollBar, move);
    }

    /**
     * Sets the fade in and fade out duration of the indicator; default is 150 ms.
     * <p/>
     *
     * @param millis the fade duration in milliseconds
     */
    public void setFadeDuration(long millis) {
        fadeInAnimation.setDuration(millis);
        fadeOutAnimation.setDuration(millis);
    }

//    /**
//     * Sets the indicator colors, when QuickScroll.TYPE_INDICATOR is selected as type.
//     * <p/>
//     *
//     * @param background the background color of the square
//     * @param tip        the background color of the tip triangle
//     * @param text       the color of the text
//     */
//    public void setIndicatorColor(final int background, final int tip, final int text) {
//        if (type == TYPE_INDICATOR || type == TYPE_INDICATOR_WITH_HANDLE) {
//            ((HoloPin) scrollIndicator.findViewById(ID_PIN)).setColor(tip);
//            scrollIndicatorTextView.setTextColor(text);
//            scrollIndicatorTextView.setBackgroundColor(background);
//        }
//    }


    /**
     * Sets the width and height of the TextView containing the indicatortext. Default is WRAP_CONTENT, WRAP_CONTENT.
     * <p/>
     *
     * @param widthDP  width in DP
     * @param heightDP height in DP
     */
    public void setSize(final int widthDP, final int heightDP) {
        final float density = getResources().getDisplayMetrics().density;
        scrollIndicatorTextView.getLayoutParams().width = (int) (widthDP * density);
        scrollIndicatorTextView.getLayoutParams().height = (int) (heightDP * density);
    }

    /**
     * Sets the padding of the TextView containing the indicatortext. Default is 4 dp.
     * <p/>
     *
     * @param paddingLeftDP   left padding in DP
     * @param paddingTopDP    top param in DP
     * @param paddingBottomDP bottom param in DP
     * @param paddingRightDP  right param in DP
     */
    public void setTextPadding(final int paddingLeftDP, final int paddingTopDP, final int paddingBottomDP, final int paddingRightDP) {
        final float density = getResources().getDisplayMetrics().density;
        scrollIndicatorTextView.setPadding((int) (paddingLeftDP * density), (int) (paddingTopDP * density), (int) (paddingRightDP * density), (int) (paddingBottomDP * density));

    }

    /**
     * Turns on fixed size for the TextView containing the indicatortext. Do not use with setSize()! This mode looks good if the indicatortext length is fixed, e.g. it's always two characters long.
     * <p/>
     *
     * @param sizeEMS number of characters in the indicatortext
     */
    public void setFixedSize(final int sizeEMS) {
        scrollIndicatorTextView.setEms(sizeEMS);
    }

    /**
     * Set the textsize of the TextView containing the indicatortext.
     *
     * @param unit - use TypedValue statics
     * @param size - the size according to the selected unit
     */
    public void setTextSize(final int unit, final float size) {
        scrollIndicatorTextView.setTextSize(unit, size);
    }


}
