package com.siwei.tongche.module.login.bean;

import com.siwei.tongche.common.BaseBean;

/**
 * 用户基本信息
 * Created by HanJinLiang on 2016-05-09.
 */
public class UserInfo extends BaseBean {
    private String UID;//	主键id
    private String UName;    //用户名
    private String UPhone;    //电话
    private String UUnitId;    //绑定所属单位id
    private String UAuditStatus;    //绑定到所属审核状态0未绑定、1已绑定、2审核中、3绑定失败
    private String UAuditorId;    //绑定单位审核人
    private String UBindUnitTime;    //绑定单位时间
    private String UDriveImg;    //驾驶证图片
    private String UDriveNo;    //驾驶证号
    private String UHeadImg;    //头像
    private String UVehicleId;//绑定车辆id
    private String UBindVehicleStatus;//绑定车辆状态
    private String UBindSendUnitId;    //绑定发货单位id
    private String UBindUnitStatus;//绑定发货单位状态0待审核（未绑定）、1审核通过（已绑定）、2审核不通过（绑定失败）
    private String UBindSendUnitAuditor;   //绑定发货单位审核人
    private String UBindSendUnitTime;    //绑定发货单位时间
    private String UWorkStatus;//上下班状态下班0、上班1
    private String UUserType;// 用户类型（1 驾驶员、2非驾驶员）
    private String URoleCode;//用户角色0驾驶员、1发货单位工作人员2收货单位工作人员、3租赁公司工作人员
    private String UUnitRole;//用户在单位角色0创建者、1管理员、2普通员工
    private String UStatus;//用户状态停用0、启用1
    private String UnitName;//单位名称

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getUUnitId() {
        return UUnitId;
    }

    public void setUUnitId(String UUnitId) {
        this.UUnitId = UUnitId;
    }

    public String getUAuditStatus() {
        return UAuditStatus;
    }

    public void setUAuditStatus(String UAuditStatus) {
        this.UAuditStatus = UAuditStatus;
    }

    public String getUAuditorId() {
        return UAuditorId;
    }

    public void setUAuditorId(String UAuditorId) {
        this.UAuditorId = UAuditorId;
    }

    public String getUBindUnitTime() {
        return UBindUnitTime;
    }

    public void setUBindUnitTime(String UBindUnitTime) {
        this.UBindUnitTime = UBindUnitTime;
    }

    public String getUDriveImg() {
        return UDriveImg;
    }

    public void setUDriveImg(String UDriveImg) {
        this.UDriveImg = UDriveImg;
    }

    public String getUDriveNo() {
        return UDriveNo;
    }

    public void setUDriveNo(String UDriveNo) {
        this.UDriveNo = UDriveNo;
    }

    public String getUHeadImg() {
        return UHeadImg;
    }

    public void setUHeadImg(String UHeadImg) {
        this.UHeadImg = UHeadImg;
    }

    public String getUVehicleId() {
        return UVehicleId;
    }

    public void setUVehicleId(String UVehicleId) {
        this.UVehicleId = UVehicleId;
    }

    public String getUBindVehicleStatus() {
        return UBindVehicleStatus;
    }

    public void setUBindVehicleStatus(String UBindVehicleStatus) {
        this.UBindVehicleStatus = UBindVehicleStatus;
    }

    public String getUBindSendUnitId() {
        return UBindSendUnitId;
    }

    public void setUBindSendUnitId(String UBindSendUnitId) {
        this.UBindSendUnitId = UBindSendUnitId;
    }

    public String getUBindUnitStatus() {
        return UBindUnitStatus;
    }

    public void setUBindUnitStatus(String UBindUnitStatus) {
        this.UBindUnitStatus = UBindUnitStatus;
    }

    public String getUBindSendUnitAuditor() {
        return UBindSendUnitAuditor;
    }

    public void setUBindSendUnitAuditor(String UBindSendUnitAuditor) {
        this.UBindSendUnitAuditor = UBindSendUnitAuditor;
    }

    public String getUBindSendUnitTime() {
        return UBindSendUnitTime;
    }

    public void setUBindSendUnitTime(String UBindSendUnitTime) {
        this.UBindSendUnitTime = UBindSendUnitTime;
    }

    public String getUWorkStatus() {
        return UWorkStatus;
    }

    public void setUWorkStatus(String UWorkStatus) {
        this.UWorkStatus = UWorkStatus;
    }

    public String getUUserType() {
        return UUserType;
    }

    public void setUUserType(String UUserType) {
        this.UUserType = UUserType;
    }

    public String getURoleCode() {
        return URoleCode;
    }

    public void setURoleCode(String URoleCode) {
        this.URoleCode = URoleCode;
    }

    public String getUUnitRole() {
        return UUnitRole;
    }

    public void setUUnitRole(String UUnitRole) {
        this.UUnitRole = UUnitRole;
    }

    public String getUStatus() {
        return UStatus;
    }

    public void setUStatus(String UStatus) {
        this.UStatus = UStatus;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public UserInfo(String UID, String UName, String UPhone, String UUnitId, String UAuditStatus, String UAuditorId, String UBindUnitTime, String UDriveImg, String UDriveNo, String UHeadImg, String UVehicleId, String UBindVehicleStatus, String UBindSendUnitId, String UBindUnitStatus, String UBindSendUnitAuditor, String UBindSendUnitTime, String UWorkStatus, String UUserType, String URoleCode, String UUnitRole, String UStatus) {
        this.UID = UID;
        this.UName = UName;
        this.UPhone = UPhone;
        this.UUnitId = UUnitId;
        this.UAuditStatus = UAuditStatus;
        this.UAuditorId = UAuditorId;
        this.UBindUnitTime = UBindUnitTime;
        this.UDriveImg = UDriveImg;
        this.UDriveNo = UDriveNo;
        this.UHeadImg = UHeadImg;
        this.UVehicleId = UVehicleId;
        this.UBindVehicleStatus = UBindVehicleStatus;
        this.UBindSendUnitId = UBindSendUnitId;
        this.UBindUnitStatus = UBindUnitStatus;
        this.UBindSendUnitAuditor = UBindSendUnitAuditor;
        this.UBindSendUnitTime = UBindSendUnitTime;
        this.UWorkStatus = UWorkStatus;
        this.UUserType = UUserType;
        this.URoleCode = URoleCode;
        this.UUnitRole = UUnitRole;
        this.UStatus = UStatus;
    }

    public  UserInfo(){}

    @Override
    public String toString() {
        return "UserInfo{" +
                "UID='" + UID + '\'' +
                ", UName='" + UName + '\'' +
                ", UPhone='" + UPhone + '\'' +
                ", UUnitId='" + UUnitId + '\'' +
                ", UAuditStatus='" + UAuditStatus + '\'' +
                ", UAuditorId='" + UAuditorId + '\'' +
                ", UBindUnitTime='" + UBindUnitTime + '\'' +
                ", UDriveImg='" + UDriveImg + '\'' +
                ", UDriveNo='" + UDriveNo + '\'' +
                ", UHeadImg='" + UHeadImg + '\'' +
                ", UVehicleId='" + UVehicleId + '\'' +
                ", UBindVehicleStatus='" + UBindVehicleStatus + '\'' +
                ", UBindSendUnitId='" + UBindSendUnitId + '\'' +
                ", UBindUnitStatus='" + UBindUnitStatus + '\'' +
                ", UBindSendUnitAuditor='" + UBindSendUnitAuditor + '\'' +
                ", UBindSendUnitTime='" + UBindSendUnitTime + '\'' +
                ", UWorkStatus='" + UWorkStatus + '\'' +
                ", UUserType='" + UUserType + '\'' +
                ", URoleCode='" + URoleCode + '\'' +
                ", UUnitRole='" + UUnitRole + '\'' +
                ", UStatus='" + UStatus + '\'' +
                '}';
    }
}
