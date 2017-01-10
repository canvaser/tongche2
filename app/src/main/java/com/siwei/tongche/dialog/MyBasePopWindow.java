package com.siwei.tongche.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.siwei.tongche.R;


/**
 * Created by HanJinLiang on 2016-05-16.
 */
public class MyBasePopWindow  extends PopupWindow{
    private Window window;
    private WindowManager.LayoutParams lp;
    public MyBasePopWindow(Context context, Window window) {
        super(context);
        this.window=window;
        init();
    }

    private void init() {
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(colorDrawable);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.MyPopWindow_anim_style);

        //解决pop 被系统键盘挡住
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        lp = window.getAttributes();
        //显示时背景变暗
        changeLight2Show();
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                changeLight2close();
            }
        });
    }


    /**
     * 恢复背景
     */
    public void changeLight2close() {
        final ValueAnimator animation = ValueAnimator.ofFloat(0.7f, 1.0f);
        animation.setDuration(300);
        animation.start();

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.alpha = (float) valueAnimator.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
    }


    /**
     * 背景变暗
     */
    public void changeLight2Show() {
        final ValueAnimator  animation = ValueAnimator.ofFloat(1.0f, 0.7f);
        animation.setDuration(300);
        animation.start();

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.alpha = (float) valueAnimator.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
    }

}
