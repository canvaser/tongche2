package com.siwei.tongche.module.carmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${viwmox} on 2017-01-11.
 */

public class CarBean implements Serializable{


    /**
     * VehicleId : 1
     * VPlateNumber : 沪C8888
     * V_NO : #1
     * VGgxh : 10方
     * VBrand : 越野870
     * VGpsx : 121.3651
     * VGpsy : 35.3214
     * DriverList : [{"DriverId":"1","DriverName":"张培"},{"DriverId":"2","DriverName":"纳木错"}]
     */

    private String VehicleId;
    private String VPlateNumber;
    private String V_NO;
    private String VGgxh;
    private String VBrand;
    private String VGpsx;
    private String VGpsy;
    private List<DriverListBean> DriverList;



    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String VehicleId) {
        this.VehicleId = VehicleId;
    }

    public String getVPlateNumber() {
        return VPlateNumber;
    }

    public void setVPlateNumber(String VPlateNumber) {
        this.VPlateNumber = VPlateNumber;
    }

    public String getV_NO() {
        return V_NO;
    }

    public void setV_NO(String V_NO) {
        this.V_NO = V_NO;
    }

    public String getVGgxh() {
        return VGgxh;
    }

    public void setVGgxh(String VGgxh) {
        this.VGgxh = VGgxh;
    }

    public String getVBrand() {
        return VBrand;
    }

    public void setVBrand(String VBrand) {
        this.VBrand = VBrand;
    }

    public String getVGpsx() {
        return VGpsx;
    }

    public void setVGpsx(String VGpsx) {
        this.VGpsx = VGpsx;
    }

    public String getVGpsy() {
        return VGpsy;
    }

    public void setVGpsy(String VGpsy) {
        this.VGpsy = VGpsy;
    }

    public List<DriverListBean> getDriverList() {
        return DriverList;
    }

    public String getDriverListStr() {
        String s = "";
        if(DriverList!=null){
            if(DriverList.size()==1){
                s= DriverList.get(0).getDriverName();
            }
            if(DriverList.size()>1){
                s= DriverList.get(0).getDriverName()+","+DriverList.get(1).getDriverName().subSequence(0,1);
            }
        }
        return s;
    }

    public void setDriverList(List<DriverListBean> DriverList) {
        this.DriverList = DriverList;
    }

    public static class DriverListBean implements Serializable {
        /**
         * DriverId : 1
         * DriverName : 张培
         */

        private String DriverId;
        private String DriverName;

        public DriverListBean() {
        }

        public DriverListBean(String driverId, String driverName) {
            DriverId = driverId;
            DriverName = driverName;
        }

        public String getDriverId() {
            return DriverId;
        }

        public void setDriverId(String DriverId) {
            this.DriverId = DriverId;
        }

        public String getDriverName() {
            return DriverName;
        }

        public void setDriverName(String DriverName) {
            this.DriverName = DriverName;
        }
    }
}
