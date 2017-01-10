package com.siwei.tongche.views.timeline.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.siwei.tongche.common.AppConstants;


/**
 * Created by ${viwmox} on 2016-12-30.
 */
//文字类
public class InfoTxtDraw extends Position {


    Paint paint;

    //文字
    private String txt;

    //文字离线段的默认距离
    public static int default_gap = 10* AppConstants.DIMEN_1;
    //文字大小
    public static int default_textsize = 13* AppConstants.DIMEN_1;

    //与线段位置
    public int direct =0;
    //在线段上面
    public static final int direct_top = 0;
    //在线段下面
    public static final int direct_bottom = 1;

    public InfoTxtDraw(int x, int y) {
        super(x, y);
    }

    public Paint getPaint() {
        if(paint ==null){
            paint  = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(default_textsize);
        }
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    //绘制
    public void draw(Canvas canvas){
        canvas.drawText(getTxt(),getX(),getY(),getPaint());
    }


}
