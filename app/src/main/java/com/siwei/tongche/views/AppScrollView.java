package com.siwei.tongche.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by SWSD on 2016-04-19.
 */
public class AppScrollView extends ScrollView{
    public AppScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppScrollView(Context context) {
        super(context);
    }

    public AppScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }
}
