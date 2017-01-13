package com.siwei.tongche.module.usercenter.bean;

import com.siwei.tongche.common.BaseBean;

/**
 * Created by ${viwmox} on 2017-01-10.
 */

public class UserBean extends BaseBean{


    /**
     * UserId : 1
     * UHeadImg : http://localhost:8087/File/HEAD/9baf8e627b9947688f83a6e4013e29131482508041.jpg
     * UName : 宫崎骏
     * UPhone : 15203654874
     * UnitRole : 0
     * Auditor : 张北川
     * BindTime : 2017-01-07T17:11:45.3082958+08:00
     * IsStop : 1
     */

    // UserId(用户ID)、UHeadImg（头像）、UName(姓名)、UPhone(电话)、UnitRole(角色（0创建者、1管理员、2普通员工）)、Auditor(审核人)、BindTime(绑定时间)，IsStop(是否停用0 是 1 否 )

    private String UserId;
    private String UHeadImg;
    private String UName;
    private String UPhone;
    private String UnitRole;
    private String Auditor;
    private String BindTime;
    private int IsStop;

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
}
