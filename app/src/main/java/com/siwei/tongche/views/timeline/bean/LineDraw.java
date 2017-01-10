package com.siwei.tongche.views.timeline.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.siwei.tongche.common.AppConstants;


/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class LineDraw {


    //左边的圆点
    CircleDraw leftCircle;
    //右边的圆点
    CircleDraw rightCircle;

    Paint paint;
    //线宽
    int thick = 1;
    //是否虚线
    boolean isgap=false;
    //左右起点方向
    private int direct = 0;
    //水平
    public static int direct_h= 0;
    //竖直
    public static int direct_v = 1;

    //默认虚线间距
    public static int default_gap = 3* AppConstants.DIMEN_1;

    //默认厚度
    public static int default_thick = 3* AppConstants.DIMEN_1;

    public LineDraw(CircleDraw leftCircle, CircleDraw rightCircle, int thick, Paint paint) {
        this.leftCircle = leftCircle;
        this.rightCircle = rightCircle;
        this.thick = thick;
        this.paint = paint;
    }

    public LineDraw(CircleDraw leftCircle, CircleDraw rightCircle, Paint paint) {
        this.thick = default_thick;
        this.leftCircle = leftCircle;
        this.rightCircle = rightCircle;
        this.paint = paint;
    }

    public LineDraw(CircleDraw leftCircle, CircleDraw rightCircle) {
        this.thick = default_thick;
        this.leftCircle = leftCircle;
        this.rightCircle = rightCircle;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    public static int getDefault_thick() {
        return default_thick;
    }

    public static void setDefault_thick(int default_thick) {
        LineDraw.default_thick = default_thick;
    }

    public CircleDraw getLeftCircle() {
        return leftCircle;
    }

    public void setLeftCircle(CircleDraw leftCircle) {
        this.leftCircle = leftCircle;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public CircleDraw getRightCircle() {
        return rightCircle;
    }

    public void setRightCircle(CircleDraw rightCircle) {
        this.rightCircle = rightCircle;
    }

    public int getThick() {
        return thick;
    }

    public void setThick(int thick) {
        this.thick = thick;
    }

    public boolean isgap() {
        return isgap;
    }

    public void setIsgap(boolean isgap) {
        this.isgap = isgap;
    }

    //绘制
    public void draw(Canvas canvas){
        int d = (getRightCircle().getX()- getLeftCircle().getX())/default_gap;
        if(getDirect()== direct_v){
            d = (getRightCircle().getY()- getLeftCircle().getY())/default_gap;
        }
        if(isgap()){
            for(int i=0;i<d;i++){
                if(i%2==0){
                    if(getDirect()== direct_v){
                        canvas.drawRect(getLeftCircle().getX(),getLeftCircle().getY()+i*default_gap,getRightCircle().getX(),getLeftCircle().getY()+(i+1)*default_gap,getPaint());
                    }else{
                        canvas.drawRect(getLeftCircle().getX()+i*default_gap,getLeftCircle().getY(),getLeftCircle().getX()+(i+1)*default_gap,getRightCircle().getY(),getPaint());
                    }
                }
            }
        }else{
            canvas.drawRect(getLeftCircle().getX(),getLeftCircle().getY(),getRightCircle().getX(),getRightCircle().getY(),getPaint());
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
