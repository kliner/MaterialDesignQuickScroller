# About MaterialDesignQuickScroller

<html><img src="https://github.com/kliner/MaterialDesignQuickScroller/raw/master/screenshot/demo.gif"/></html>

Refactored from QuickScroll: [https://github.com/andraskindler/quickscroll](https://github.com/andraskindler/quickscroll)

MaterialDesignQuickScroller is a library aimed at creating similar scrolling experiences to the Android 5.0 native Contacts app's People view. Android 5.0 Lolipop using material design, and old QuickScroll lib can't support this feature. This new lib also refactored the QuickScroll code, thus you can easily change the Scroller's style via xml attributes and add new style of scroller in java code.

This library also support Holo style and popup style. 

Min support sdk version is Android 2.2.

# Usage

1. Add this lib to your project dependences.
2. Include the widget in your layout. You can set it at anywhere you like.   <pre><code>
&lt;com.kliner.mdquickscroller.widget.MaterialDesignQuickScroller
        xmlns:app="http://schemas.android.com/apk/com.kliner.mdquickscroller"
        android:id="@+id/scroller"
        android:layout_width="30dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        android:layout_alignParentEnd="true" />
</code></pre>and you can custom it easily by add more feature:<pre><code>
&lt;com.kliner.mdquickscroller.widget.MaterialDesignQuickScroller
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/scroller"
        android:layout_width="30dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        android:layout_alignParentEnd="true"
        app:scrollerStyle="material"
        app:scrollerBarVisible="visible"
        app:scrollerLineVisible="invisible"
        app:scrollBarWidth="4dp"
        app:scrollBarHeight="16dp"
        app:scrollBarColor="#111"
        app:scrollBarFocusColor="#111"
        app:scrollBarFocusTransColor="#8111"
        app:scrollLineWidth="1dp"
        app:scrollLineColor="#bbb"
        app:materialPinWidth="60dp"
        app:materialPinColor="#0f0"
        app:materialPinTextSize="18sp"
        app:materialPinTextColor="#fff"
        />
</code></pre>
3. Implement the Scrollable interface in your BaseExpandableListAdapter or BaseAdapter subclass. You must override the methods below. 
	
	The first function returns the corresponding String to display at any given position:

	<pre><code>@Override
	public String getIndicatorForPosition(int childposition, int groupposition) {
	    return null;
	}
	</code></pre>

	The second function is responsible for is for implementing scroll behaviour. This can be used to perform special tasks, e.g. if you want to snap to the first item starting with a letter in an alphabetically ordered list or jump between groups in an ExpandableListView. If you want the normal approach, simply return childposition.

	<pre><code>@Override
	public int getScrollPosition(int childposition, int groupposition) {
	return 0;
	}
	</code></pre>
4. Initialize the MaterialDesignQuickScroller widget.
	<pre><code>
	MaterialDesignQuickScroller scroller = (MaterialDesignQuickScroller) findViewById(R.id.scroller);
    scroller.init(mListView, mAdapter);
	</code></pre>
5. Customizing also can be done via scroller's methods.

#Other notices

Developed by **kliner**([kliner@live.cn](mailto:kliner@live.cn))

Reference code: [https://github.com/andraskindler/quickscroll](https://github.com/andraskindler/quickscroll)
