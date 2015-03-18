package com.kliner.mdquickscrollerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kliner.mdquickscroller.widget.MaterialDesignQuickScroller;
import com.kliner.mdquickscroller.widget.Scrollable;

public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private MaterialDesignQuickScroller scroller;
    private DemoListAdapter mAdapter;
    private String[] mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = getResources().getStringArray(R.array.list);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new DemoListAdapter(this, R.layout.list_item);
        for (int i = 0; i < mData.length; i++) {
            mAdapter.add(mData[i]);
        }
        mListView.setAdapter(mAdapter);
        scroller = (MaterialDesignQuickScroller) findViewById(R.id.scroller);
        scroller.init(mListView, mAdapter);
    }

    public class DemoListAdapter extends ArrayAdapter<String> implements Scrollable {

        public DemoListAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public String getIndicatorForPosition(int childPosition, int groupPosition) {
            return mData[childPosition].substring(0, 1);
        }

        @Override
        public int getScrollPosition(int childPosition, int groupPosition) {
            return childPosition;
        }
    }
}
