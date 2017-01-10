package com.siwei.tongche.module.bind_unit.bean;

import com.siwei.tongche.common.BaseSelectBean;

/**
 * Created by HanJinLiang on 2016-12-22.
 * 单位信息
 */

public class UnitInfo extends BaseSelectBean {

    /**
     * UnitName : 哈哈哈
     * UnitType : 3
     * UID : 61fb01292e7641408c04a6f000a88bbf
     * UPhone : 18721607438
     * UName : 唐杰
     */

    private String UnitName;
    private String UnitType;
    private String UID;
    private String UPhone;
    private String UName;

    public UnitInfo() {
    }

    public UnitInfo(String UID, String UName, String unitName, String unitType, String UPhone) {
        this.UID = UID;
        this.UName = UName;
        UnitName = unitName;
        UnitType = unitType;
        this.UPhone = UPhone;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public String getUnitType() {
        return UnitType;
    }

    public void setUnitType(String UnitType) {
        this.UnitType = UnitType;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUPhone() {
        return UPhone;
    }

    public void setUPhone(String UPhone) {
        this.UPhone = UPhone;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }
}
