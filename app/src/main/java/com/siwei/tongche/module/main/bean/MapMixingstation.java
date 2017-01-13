package com.siwei.tongche.module.main.bean;

import com.siwei.tongche.common.BaseBean;

/**
 * Created by HanJinLiang on 2017-01-09.
 * 地图里面搅拌站
 */

public class MapMixingstation extends BaseBean {

    /**
     * MSID : 61fb01292e7641408c04a6f000a88ccc
     * MSName : 上海市水利局
     * MSAddress : 上海市耀明路402
     * MSLinkMan : 赵云
     * MSTel : 15212358974
     * MSGpsx : 121.513007
     * MSGpsy : 31.334564
     * MSCreateTime : 2017-01-03T20:36:04
     * MSMemo :
     */


    private String MSID;
    private String MSName;
    private String MSAddress;
    private String MSLinkMan;
    private String MSTel;
    private String MSGpsx;
    private String MSGpsy;
    private String MSCreateTime;
    private String MSMemo;

    public String getMSID() {
        return MSID;
    }

    public void setMSID(String MSID) {
        this.MSID = MSID;
    }

    public String getMSName() {
        return MSName;
    }

    public void setMSName(String MSName) {
        this.MSName = MSName;
    }

    public String getMSAddress() {
        return MSAddress;
    }

    public void setMSAddress(String MSAddress) {
        this.MSAddress = MSAddress;
    }

    public String getMSLinkMan() {
        return MSLinkMan;
    }

    public void setMSLinkMan(String MSLinkMan) {
        this.MSLinkMan = MSLinkMan;
    }

    public String getMSTel() {
        return MSTel;
    }

    public void setMSTel(String MSTel) {
        this.MSTel = MSTel;
    }

    public String getMSGpsx() {
        return MSGpsx;
    }

    public void setMSGpsx(String MSGpsx) {
        this.MSGpsx = MSGpsx;
    }

    public String getMSGpsy() {
        return MSGpsy;
    }

    public void setMSGpsy(String MSGpsy) {
        this.MSGpsy = MSGpsy;
    }

    public String getMSCreateTime() {
        return MSCreateTime;
    }

    public void setMSCreateTime(String MSCreateTime) {
        this.MSCreateTime = MSCreateTime;
    }

    public String getMSMemo() {
        return MSMemo;
    }

    public void setMSMemo(String MSMemo) {
        this.MSMemo = MSMemo;
    }

    //-----------------------新接口---------------------

    /**
     * UnitName : 上海发货单位
     * UnitAddress : 嘿嘿嘿嘿IEhi而后IE
     * ContactorName : Sender
     * ContactorPhone : 12122112211
     * UnitGpsx : UnitGpsx
     * UnitGpsy : UnitGpsy
     */

    private String UnitName;
    private String UnitAddress;
    private String ContactorName;
    private String ContactorPhone;
    private String UnitGpsx;
    private String UnitGpsy;
    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public String getUnitAddress() {
        return UnitAddress;
    }

    public void setUnitAddress(String UnitAddress) {
        this.UnitAddress = UnitAddress;
    }

    public String getContactorName() {
        return ContactorName;
    }

    public void setContactorName(String ContactorName) {
        this.ContactorName = ContactorName;
    }

    public String getContactorPhone() {
        return ContactorPhone;
    }

    public void setContactorPhone(String ContactorPhone) {
        this.ContactorPhone = ContactorPhone;
    }

    public String getUnitGpsx() {
        return UnitGpsx;
    }

    public void setUnitGpsx(String UnitGpsx) {
        this.UnitGpsx = UnitGpsx;
    }

    public String getUnitGpsy() {
        return UnitGpsy;
    }

    public void setUnitGpsy(String UnitGpsy) {
        this.UnitGpsy = UnitGpsy;
    }
}
