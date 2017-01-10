package com.siwei.tongche.common;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ${viwmox} on 2016-12-27.
 */

public class BaseUIOpe{

    protected View containerView;

    protected Context context;


    public BaseUIOpe(Context context, View containerView) {
        this.context =context;
        this.containerView=containerView;
        if(containerView==null){
            return;
        }
        ButterKnife.bind(this,containerView);
    }



}
