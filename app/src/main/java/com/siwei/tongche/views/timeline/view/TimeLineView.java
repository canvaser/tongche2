package com.siwei.tongche.views.timeline.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.views.timeline.bean.CircleDraw;
import com.siwei.tongche.views.timeline.bean.InfoTxtDraw;
import com.siwei.tongche.views.timeline.bean.RedCricleDraw;
import com.siwei.tongche.views.timeline.bean.TimeLineDataBean;


/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class TimeLineView extends ImageView{

    Context context;


    //绘制的图案上下左右的边距
    public static int borderWidth_left = AppConstants.DIMEN_1*50;

    public static int borderWidth_top = AppConstants.DIMEN_1*40;

    public static int borderWidth_right = AppConstants.DIMEN_1*50;

    public static int borderWidth_bottom = AppConstants.DIMEN_1*40;


    //操纵本view的数据
    TimeLineDataBean data;

    //默认填充为红色的园框未知
    private int position = 4;

    //最大圆点数
    public static final int max_circle = 5;


    //默认的显示文字 依次为圆点对应的文字 最后一个为中间文字
    String[] str = new String[]{"","","","","",""};

    //圆点xy坐标
    private int[][] values ;

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs){

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();
    }

    public void init(){
        if(data==null){
            //初始化圆点坐标
            values = new int[][]{{borderWidth_left,borderWidth_top},
                    {getWidth()/2,borderWidth_top},
                    {getWidth()-borderWidth_right,borderWidth_top},
                    {getWidth()-borderWidth_right,getHeight()-borderWidth_bottom},
                    {borderWidth_left,getHeight()-borderWidth_bottom}
            };
            data = new TimeLineDataBean();
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            //添加普通圆点数据
            for(int i=0;i<max_circle;i++){
                data.getCircleXYs().add(new CircleDraw(values[i][0],values[i][1],paint));
            }
            //添加红色圆点数据
            data.setLastCircleDraw(new RedCricleDraw(values[position][0],values[position][1],paint));
            //根据圆点得到点点线数据
            data.createLineDraw();
            Paint txtpaint = new Paint();
            txtpaint.setColor(Color.WHITE);
            //添加圆点对应的文字数据
            for(int i=0;i<data.getCircleXYs().size();i++){
                data.getCircleXYs().get(i).getInfoTxtDraw().setTxt(str[i]);
                if(i<=data.getCircleXYs().size()/2){
                    data.getCircleXYs().get(i).getInfoTxtDraw().setDirect(InfoTxtDraw.direct_top);
                }else{
                    data.getCircleXYs().get(i).getInfoTxtDraw().setDirect(InfoTxtDraw.direct_bottom);
                }
            }

            //调整文字位置
            for(int i=0;i<data.getCircleXYs().size();i++){
                switch (data.getCircleXYs().get(i).getInfoTxtDraw().getDirect()){
                    case InfoTxtDraw.direct_bottom:
                        data.getCircleXYs().get(i).getInfoTxtDraw().setY(data.getCircleXYs().get(i).getInfoTxtDraw().getY()+InfoTxtDraw.default_textsize+InfoTxtDraw.default_gap);
                        break;
                    case InfoTxtDraw.direct_top:
                        data.getCircleXYs().get(i).getInfoTxtDraw().setY(data.getCircleXYs().get(i).getInfoTxtDraw().getY()-InfoTxtDraw.default_gap);
                        break;
                }
                data.getCircleXYs().get(i).getInfoTxtDraw().setX((int) (data.getCircleXYs().get(i).getInfoTxtDraw().getX()-(data.getCircleXYs().get(i).getInfoTxtDraw().getPaint().measureText(data.getCircleXYs().get(i).getInfoTxtDraw().getTxt()))/2));

            }

            //中心文字位置调整
            data.getCenterTxt().setTxt(str[str.length-1]);
            data.getCenterTxt().setX((int) (getWidth()/2-data.getCenterTxt().getPaint().measureText(data.getCenterTxt().getTxt())/2));
            data.getCenterTxt().setY(getHeight()/2+InfoTxtDraw.default_textsize/2);
            //根据设置的红点位置设置各线段是否为虚线标志
            for(int i=0;i<values.length-1;i++){
                if(i<position){
                    data.getLineDraws().get(i).setIsgap(false);
                }else{
                    data.getLineDraws().get(i).setIsgap(true);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(data==null){
            init();
        }
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        //画普通圆点
        for(int i=0;i<data.getCircleXYs().size();i++){
            data.getCircleXYs().get(i).draw(canvas);
        }

        //画线段
        for(int i=0;i<data.getLineDraws().size();i++){
            data.getLineDraws().get(i).draw(canvas);

        }
        //画红色圆点
        data.getLastCircleDraw().draw(canvas);

        //画中心文字
        data.getCenterTxt().draw(canvas);
    }

    /**
     * 设置红点位置
     * @param position
     */
    public void setNowTime(int position){
        this.position = position;
        data=null;
        requestLayout();
    }

    /**
     * 设置文字
     * @param str
     */
    public void setTxt(String[] str){
        this.str =str;
        requestLayout();
    }
}
