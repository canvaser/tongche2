package com.siwei.tongche.module.usercenter.bean;

import com.siwei.tongche.common.BaseBean;

/**
 * Created by ${viwmox} on 2017-01-11.
 */

public class DriverBean extends BaseBean{


    /**
     * UserId : 1
     * UHeadImg : http://192.168.1.67:8088/File/HEAD/9baf8e627b9947688f83a6e4013e29131482508041.jpg
     * UName : 北岛
     * UPhone : 15203656547
     * UnitRole : 2
     * Auditor : 宫崎骏
     * BindTime : 2017-01-10T10:46:37.8222191+08:00
     * IsStop : 0
     */

    private String UserId;
    private String UHeadImg;
    private String UName;
    private String UPhone;
    private String UnitRole;
    private String Auditor;
    private String BindTime;
    private int IsStop;
    private boolean select;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUHeadImg() {
        return UHeadImg;
    }

    public void setUHeadImg(String UHeadImg) {
        this.UHeadImg = UHeadImg;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUPhone() {
        return UPhone;
    }

    public void setUPhone(String UPhone) {
        this.UPhone = UPhone;
    }

    public String getUnitRole() {
        return UnitRole;
    }

    public void setUnitRole(String UnitRole) {
        this.UnitRole = UnitRole;
    }

    public String getAuditor() {
        return Auditor;
    }

    public void setAuditor(String Auditor) {
        this.Auditor = Auditor;
    }

    public String getBindTime() {
        return BindTime;
    }

    public void setBindTime(String BindTime) {
        this.BindTime = BindTime;
    }

    public int getIsStop() {
        return IsStop;
    }

    public void setIsStop(int IsStop) {
        this.IsStop = IsStop;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
