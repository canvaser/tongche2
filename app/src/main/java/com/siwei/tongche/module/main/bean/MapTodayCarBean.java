package com.siwei.tongche.module.main.bean;

import com.siwei.tongche.common.BaseBean;

import java.util.List;

/**
 * Created by HanJinLiang on 2017-01-12.
 */

public class MapTodayCarBean extends BaseBean {

    /**
     * TaskId : dc18feec5e7d472ebf87f04e5d09cc3b
     * TaskNo : NO.201701120003
     * Tpz : C30细石
     * Rtld : 120±30
     * Jzfs : 62米泵
     * "SendUnitName":"上海发货单位",
     * "RecvUnitName":"上海收货单位"
     * CompleteCs : 15
     * SendCs : 20
     * LjNum : 30
     * PlanNum : 80
     * AcceptTime : 1484234007
     * Jzbw : 东方明珠电视塔A15标段旋转餐厅楼板
     * vehicles : [{"VehicleId":"f6284d300eb149db9fe31385ea2df229","VGpsx":"VGpsx","VGpsy":"VGpsy"}]
     */

    private String TaskId;
    private String TaskNo;
    private String Tpz;
    private String Rtld;
    private String Jzfs;
    private int CompleteCs;
    private int SendCs;
    private int LjNum;
    private int PlanNum;
    private String AcceptTime;
    private String SendUnitName;
    private String RecvUnitName;
    private String Jzbw;
    private List<VehiclesBean> vehicles;

    public String getSendUnitName() {
        return SendUnitName;
    }

    public void setSendUnitName(String sendUnitName) {
        SendUnitName = sendUnitName;
    }

    public String getRecvUnitName() {
        return RecvUnitName;
    }

    public void setRecvUnitName(String recvUnitName) {
        RecvUnitName = recvUnitName;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String TaskId) {
        this.TaskId = TaskId;
    }

    public String getTaskNo() {
        return TaskNo;
    }

    public void setTaskNo(String TaskNo) {
        this.TaskNo = TaskNo;
    }

    public String getTpz() {
        return Tpz;
    }

    public void setTpz(String Tpz) {
        this.Tpz = Tpz;
    }

    public String getRtld() {
        return Rtld;
    }

    public void setRtld(String Rtld) {
        this.Rtld = Rtld;
    }

    public String getJzfs() {
        return Jzfs;
    }

    public void setJzfs(String Jzfs) {
        this.Jzfs = Jzfs;
    }

    public int getCompleteCs() {
        return CompleteCs;
    }

    public void setCompleteCs(int CompleteCs) {
        this.CompleteCs = CompleteCs;
    }

    public int getSendCs() {
        return SendCs;
    }

    public void setSendCs(int SendCs) {
        this.SendCs = SendCs;
    }

    public int getLjNum() {
        return LjNum;
    }

    public void setLjNum(int LjNum) {
        this.LjNum = LjNum;
    }

    public int getPlanNum() {
        return PlanNum;
    }

    public void setPlanNum(int PlanNum) {
        this.PlanNum = PlanNum;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String AcceptTime) {
        this.AcceptTime = AcceptTime;
    }

    public String getJzbw() {
        return Jzbw;
    }

    public void setJzbw(String Jzbw) {
        this.Jzbw = Jzbw;
    }

    public List<VehiclesBean> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehiclesBean> vehicles) {
        this.vehicles = vehicles;
    }

    public static class VehiclesBean {
        /**
         * VehicleId : f6284d300eb149db9fe31385ea2df229
         * VGpsx : VGpsx
         * VGpsy : VGpsy
         */

        private String VehicleId;
        private String VGpsx;
        private String VGpsy;

        public String getVehicleId() {
            return VehicleId;
        }

        public void setVehicleId(String VehicleId) {
            this.VehicleId = VehicleId;
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
    }
}
