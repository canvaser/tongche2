package com.siwei.tongche.module.refresh_loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.siwei.tongche.R;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreUIHandler;

/**
 * Created by vaezq1229 on 16/1/7.
 */
public class MyLoadMoreFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    public MyLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public MyLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.loadmore_footerview, this);
        mTextView = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setAlpha(1);
        mTextView.setText("加载中...");
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            setVisibility(VISIBLE);
            if (empty) {
                mTextView.setText("");
            } else {
                mTextView.setText("您没有更多内容了");
                mTextView.animate().alpha(0).setDuration(1000);
            }
        } else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setAlpha(1);
        mTextView.setText("加载中...");
    }
}

