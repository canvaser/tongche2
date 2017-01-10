package com.siwei.tongche.module.main.bean;

import com.siwei.tongche.common.BaseBean;

/**
 * Created by HanJinLiang on 2016-12-30.
 */

public class ExpressInfoBean extends BaseBean {

//    "TID": "d7889e8a047846738dc3a6e900a71234",
//            "PTRNo": "R001",
//            "RGCName": "上海中心大厦",
//            "TDriveID": "b3ea8bbb180648d7b14ca6e30136b51b",
//            "TDriveName": "jolin",
//            "TDriveMobile": "18965477896",
//            "TQSRID": "61fb01292e7641408c04a6f000a88bbf",
//            "TQSRName": "唐杰",
//            "TQSRMobile": "18721607438",
//            "TSendUnitName": "上海远安贸易有限公司",
//            "TReceiveUnitName": "上海远安贸易有限公司2",
//            "RTLjCs": "0",
//            "RTLjNum": "0",
//            "TTNo": "X001",
//            "V_NO": "#1",
//            "VPlateNumber": "沪C8888",
//            "VBrand": "东风重卡",
//            "TSendNum": "0",
//            "TQSNum": "0",
//            "TRemainNum": "0",
//            "TYKNum": "0",
//            "TQProgress": "0",
//            "PTRuleCode": "1",
//            "PTGcAddress": "银城中路",
//            "RTTpz": "C30",
//            "RTLd": "120±30",
//            "RTJzfs": "泵送",
//            "RTJzbw": "墙壁",
//            "RTCompleteCs": "0",
//            "RTSendCs": "0",
//            "RTPlanNum": "0",
//            "TOutFactoryTime": "2016/12/28 17:28:57",
//            "TArriveTime": "2016/12/28 17:38:57",
//            "TJzTime": "2016/12/28 17:38:57",
//            "TLeaveTime": "2016/12/28 17:48:57",
//            "TSignTime": "2016/12/28 17:40:57",
//            "TGoBackFactoryTime": "2016/12/28 17:58:57",
//            "IsCollect": "0",
//            "TMemo": ""

    private String TDriveID;
    private String RGCName;
    private String PTGcAddress;
    private String RTTpz;
    private String RTLd;
    private String RTJzfs;
    private String RTJzbw;
    private String RTCompleteCs;
    private String RTSendCs;
    private String RTPlanNum;
    private String TOutFactoryTime;
    private String TArriveTime;
    private String TJzTime;
    private String TLeaveTime;
    private String TSignTime;
    private String TGoBackFactoryTime;
    private String PTRNo;//任务编号
    private String TDriveName;//司机姓名
    private String TDriveMobile;//司机电话
    private String TQSRID;//签收人ID
    private String TQSRName;//签收人姓名
    private String TQSRMobile;//签收人电话
    private String TSendUnitName;//发货单位名称
    private String TReceiveUnitName;//收货单位名称
    private String RTLjCs;//累计车数
    private String RTLjNum;//累计方量
    private String TTNo;//小票编号
    private String V_NO;//车号
    private String VPlateNumber;//车牌号
    private String VBrand;//车品牌
    private String TSendNum;//发货量
    private String TQSNum;//签收量
    private String TRemainNum;//剩余量
    private String TYKNum;//盈亏量
    private String TQProgress;//小票状态
    private String PTRuleCode;//签收规则
    private String IsCollect;//是否收藏
    private String TMemo;//备注
    private String TQSStatus;//小票签收状态 未签收0、已签收1、作废2

    public String getTQSStatus() {
        return TQSStatus;
    }

    public void setTQSStatus(String TQSStatus) {
        this.TQSStatus = TQSStatus;
    }

    public String getPTRNo() {
        return PTRNo;
    }

    public void setPTRNo(String PTRNo) {
        this.PTRNo = PTRNo;
    }

    public String getTDriveName() {
        return TDriveName;
    }

    public void setTDriveName(String TDriveName) {
        this.TDriveName = TDriveName;
    }

    public String getTDriveMobile() {
        return TDriveMobile;
    }

    public void setTDriveMobile(String TDriveMobile) {
        this.TDriveMobile = TDriveMobile;
    }

    public String getTQSRMobile() {
        return TQSRMobile;
    }

    public void setTQSRMobile(String TQSRMobile) {
        this.TQSRMobile = TQSRMobile;
    }

    public String getTSendUnitName() {
        return TSendUnitName;
    }

    public void setTSendUnitName(String TSendUnitName) {
        this.TSendUnitName = TSendUnitName;
    }

    public String getTReceiveUnitName() {
        return TReceiveUnitName;
    }

    public void setTReceiveUnitName(String TReceiveUnitName) {
        this.TReceiveUnitName = TReceiveUnitName;
    }

    public String getRTLjCs() {
        return RTLjCs;
    }

    public void setRTLjCs(String RTLjCs) {
        this.RTLjCs = RTLjCs;
    }

    public String getRTLjNum() {
        return RTLjNum;
    }

    public void setRTLjNum(String RTLjNum) {
        this.RTLjNum = RTLjNum;
    }

    public String getTTNo() {
        return TTNo;
    }

    public void setTTNo(String TTNo) {
        this.TTNo = TTNo;
    }

    public String getV_NO() {
        return V_NO;
    }

    public void setV_NO(String v_NO) {
        V_NO = v_NO;
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

    public String getTSendNum() {
        return TSendNum;
    }

    public void setTSendNum(String TSendNum) {
        this.TSendNum = TSendNum;
    }

    public String getTQSNum() {
        return TQSNum;
    }

    public void setTQSNum(String TQSNum) {
        this.TQSNum = TQSNum;
    }

    public String getTRemainNum() {
        return TRemainNum;
    }

    public void setTRemainNum(String TRemainNum) {
        this.TRemainNum = TRemainNum;
    }

    public String getTYKNum() {
        return TYKNum;
    }

    public void setTYKNum(String TYKNum) {
        this.TYKNum = TYKNum;
    }

    public String getTQProgress() {
        return TQProgress;
    }

    public void setTQProgress(String TQProgress) {
        this.TQProgress = TQProgress;
    }

    public String getPTRuleCode() {
        return PTRuleCode;
    }

    public void setPTRuleCode(String PTRuleCode) {
        this.PTRuleCode = PTRuleCode;
    }

    public String getIsCollect() {
        return IsCollect;
    }

    public void setIsCollect(String isCollect) {
        IsCollect = isCollect;
    }

    public String getTMemo() {
        return TMemo;
    }

    public void setTMemo(String TMemo) {
        this.TMemo = TMemo;
    }

    public String getTDriveID() {
        return TDriveID;
    }

    public void setTDriveID(String TDriveID) {
        this.TDriveID = TDriveID;
    }

    public String getTQSRID() {
        return TQSRID;
    }

    public void setTQSRID(String TQSRID) {
        this.TQSRID = TQSRID;
    }

    public String getTQSRName() {
        return TQSRName;
    }

    public void setTQSRName(String TQSRName) {
        this.TQSRName = TQSRName;
    }

    public String getRGCName() {
        return RGCName;
    }

    public void setRGCName(String RGCName) {
        this.RGCName = RGCName;
    }

    public String getPTGcAddress() {
        return PTGcAddress;
    }

    public void setPTGcAddress(String PTGcAddress) {
        this.PTGcAddress = PTGcAddress;
    }

    public String getRTTpz() {
        return RTTpz;
    }

    public void setRTTpz(String RTTpz) {
        this.RTTpz = RTTpz;
    }

    public String getRTLd() {
        return RTLd;
    }

    public void setRTLd(String RTLd) {
        this.RTLd = RTLd;
    }

    public String getRTJzfs() {
        return RTJzfs;
    }

    public void setRTJzfs(String RTJzfs) {
        this.RTJzfs = RTJzfs;
    }

    public String getRTJzbw() {
        return RTJzbw;
    }

    public void setRTJzbw(String RTJzbw) {
        this.RTJzbw = RTJzbw;
    }

    public String getRTCompleteCs() {
        return RTCompleteCs;
    }

    public void setRTCompleteCs(String RTCompleteCs) {
        this.RTCompleteCs = RTCompleteCs;
    }

    public String getRTSendCs() {
        return RTSendCs;
    }

    public void setRTSendCs(String RTSendCs) {
        this.RTSendCs = RTSendCs;
    }

    public String getRTPlanNum() {
        return RTPlanNum;
    }

    public void setRTPlanNum(String RTPlanNum) {
        this.RTPlanNum = RTPlanNum;
    }

    public String getTOutFactoryTime() {
        return TOutFactoryTime;
    }

    public void setTOutFactoryTime(String TOutFactoryTime) {
        this.TOutFactoryTime = TOutFactoryTime;
    }

    public String getTArriveTime() {
        return TArriveTime;
    }

    public void setTArriveTime(String TArriveTime) {
        this.TArriveTime = TArriveTime;
    }

    public String getTJzTime() {
        return TJzTime;
    }

    public void setTJzTime(String TJzTime) {
        this.TJzTime = TJzTime;
    }

    public String getTLeaveTime() {
        return TLeaveTime;
    }

    public void setTLeaveTime(String TLeaveTime) {
        this.TLeaveTime = TLeaveTime;
    }

    public String getTSignTime() {
        return TSignTime;
    }

    public void setTSignTime(String TSignTime) {
        this.TSignTime = TSignTime;
    }

    public String getTGoBackFactoryTime() {
        return TGoBackFactoryTime;
    }

    public void setTGoBackFactoryTime(String TGoBackFactoryTime) {
        this.TGoBackFactoryTime = TGoBackFactoryTime;
    }
}
