package com.siwei.tongche.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class TimeLineView extends ImageView{

    Context context;

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){

    }

}
