package com.siwei.tongche.module.message.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${viwmox} on 2017-01-03.
 */

public class MessageMenuBean implements Serializable{

    private String name;

    private String mType;

    private int index=0;

    ArrayList<MessageDetailBean> list = new ArrayList<>();

    public MessageMenuBean(String mType, String name) {
        this.mType = mType;
        this.name = name;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<MessageDetailBean> getList() {
        return list;
    }

    public void setList(ArrayList<MessageDetailBean> list) {
        this.list = list;
    }
}
