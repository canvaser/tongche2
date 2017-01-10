package com.siwei.tongche.module.usercenter.bean;

import java.io.Serializable;

/**
 * Created by ${viwmox} on 2017-01-04.
 */

public class DriverVehicleInfoBean implements Serializable{


    /**
     * V_NO : #1
     * VPlateNumber : 沪C8888
     * VBrand : 东风重卡
     * VGgxh : 10方
     * VImg :
     * VTravelImg :
     */

    private String V_NO;
    private String VPlateNumber;
    private String VBrand;
    private String VGgxh;
    private String VImg;
    private String VTravelImg;

    public String getV_NO() {
        return V_NO;
    }

    public void setV_NO(String V_NO) {
        this.V_NO = V_NO;
    }

    public String getVPlateNumber() {
        return VPlateNumber;
    }

    public void setVPlateNumber(String VPlateNumber) {
        this.VPlateNumber = VPlateNumber;
    }

    public String getVBrand() {
        return VBrand;
    }

    public void setVBrand(String VBrand) {
        this.VBrand = VBrand;
    }

    public String getVGgxh() {
        return VGgxh;
    }

    public void setVGgxh(String VGgxh) {
        this.VGgxh = VGgxh;
    }

    public String getVImg() {
        return VImg;
    }

    public void setVImg(String VImg) {
        this.VImg = VImg;
    }

    public String getVTravelImg() {
        return VTravelImg;
    }

    public void setVTravelImg(String VTravelImg) {
        this.VTravelImg = VTravelImg;
    }
}
