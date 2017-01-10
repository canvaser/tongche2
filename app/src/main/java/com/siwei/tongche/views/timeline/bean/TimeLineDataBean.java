package com.siwei.tongche.views.timeline.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class TimeLineDataBean implements Serializable{


    //圆点们数据
    ArrayList<CircleDraw> circleXYs;
    //线条们数据
    ArrayList<LineDraw> lineDraws;
    //红点数据
    RedCricleDraw lastCircleDraw;
    //中心文字数据
    InfoTxtDraw centerTxt;



    public TimeLineDataBean() {

    }

    public ArrayList<CircleDraw> getCircleXYs() {
        if(circleXYs==null){
            circleXYs=new ArrayList<>();
        }
        return circleXYs;
    }

    public void createLineDraw(){
        //添加线条
        for(int i=0;i<circleXYs.size()-1;i++){
            CircleDraw left = new CircleDraw(circleXYs.get(i).getX(),circleXYs.get(i).getY(),circleXYs.get(i).getCirclePaint());
            CircleDraw right = new CircleDraw(circleXYs.get(i+1).getX(),circleXYs.get(i+1).getY(),circleXYs.get(i+1).getCirclePaint());
            getLineDraws().add(new LineDraw(left,right));
        }


        for(int i=0;i<getLineDraws().size();i++){

            //从左到右方向
            if(getLineDraws().get(i).getLeftCircle().getX()<getLineDraws().get(i).getRightCircle().getX()){
                getLineDraws().get(i).getLeftCircle().setY(getLineDraws().get(i).getLeftCircle().getY()-getLineDraws().get(i).getThick());
                getLineDraws().get(i).getRightCircle().setY(getLineDraws().get(i).getRightCircle().getY()+getLineDraws().get(i).getThick());
                getLineDraws().get(i).setDirect(LineDraw.direct_h);
            }else if(getLineDraws().get(i).getLeftCircle().getX()==getLineDraws().get(i).getRightCircle().getX()){
                //从上到下
                getLineDraws().get(i).getLeftCircle().setX(getLineDraws().get(i).getLeftCircle().getX()-getLineDraws().get(i).getThick());
                getLineDraws().get(i).getRightCircle().setX(getLineDraws().get(i).getRightCircle().getX()+getLineDraws().get(i).getThick());
                getLineDraws().get(i).setDirect(LineDraw.direct_v);
            }else{
                //从右到左
                CircleDraw circleDraw = getLineDraws().get(i).getLeftCircle();
                getLineDraws().get(i).setLeftCircle(getLineDraws().get(i).getRightCircle());
                getLineDraws().get(i).setRightCircle(circleDraw);
                getLineDraws().get(i).getLeftCircle().setY(getLineDraws().get(i).getLeftCircle().getY()-getLineDraws().get(i).getThick());
                getLineDraws().get(i).getRightCircle().setY(getLineDraws().get(i).getRightCircle().getY()+getLineDraws().get(i).getThick());
                getLineDraws().get(i).setDirect(LineDraw.direct_h);
            }

        }


    }


    public void setCircleXYs(ArrayList<CircleDraw> circleXYs) {
        this.circleXYs = circleXYs;
    }

    public ArrayList<LineDraw> getLineDraws() {
        if(lineDraws==null){
            lineDraws = new ArrayList<>();
        }
        return lineDraws;
    }

    public void setLineDraws(ArrayList<LineDraw> lineDraws) {
        this.lineDraws = lineDraws;
    }

    public RedCricleDraw getLastCircleDraw() {
        return lastCircleDraw;
    }

    public void setLastCircleDraw(RedCricleDraw lastCircleDraw) {
        this.lastCircleDraw = lastCircleDraw;
    }

    public InfoTxtDraw getCenterTxt() {
        if(centerTxt==null){
            centerTxt = new InfoTxtDraw(0,0);
        }
        return centerTxt;
    }

    public void setCenterTxt(InfoTxtDraw centerTxt) {
        this.centerTxt = centerTxt;
    }

}
