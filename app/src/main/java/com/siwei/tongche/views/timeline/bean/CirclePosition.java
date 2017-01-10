package com.siwei.tongche.views.timeline.bean;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class CirclePosition extends Position {

    //半径
    int radious = 1;


    public CirclePosition(int x, int y, int radious) {
        super(x, y);
        this.radious = radious;
    }

    public int getRadious() {
        return radious;
    }

    public void setRadious(int radious) {
        this.radious = radious;
    }
}
