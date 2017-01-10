package com.siwei.tongche.module.message.bean;

import java.io.Serializable;

/**
 * Created by ${viwmox} on 2017-01-04.
 */

public class MessageDetailBean implements Serializable{


    /**
     * MGuid : f235870ce996423fbbc1a09855c3dbad
     * MUID : d7889e8a047846738dc3a6e900a79fcc
     * MSName : 13524051358
     * MRUID : 61fb01292e7641408c04a6f000a88bbf
     * MRName : 唐杰
     * MType : 2
     * MContent : 13524051358希望成为贵公司用户
     * MStatus : 0
     * MAuditor :
     * MTime : 2017/1/3 15:57:46
     */

    private String MGuid;
    private String MUID;
    private String MSName;
    private String MRUID;
    private String MRName;
    private String MType;
    private String MContent;
    private String MStatus;
    private String MAuditor;
    private String MTime;

    public String getMGuid() {
        return MGuid;
    }

    public void setMGuid(String MGuid) {
        this.MGuid = MGuid;
    }

    public String getMUID() {
        return MUID;
    }

    public void setMUID(String MUID) {
        this.MUID = MUID;
    }

    public String getMSName() {
        return MSName;
    }

    public void setMSName(String MSName) {
        this.MSName = MSName;
    }

    public String getMRUID() {
        return MRUID;
    }

    public void setMRUID(String MRUID) {
        this.MRUID = MRUID;
    }

    public String getMRName() {
        return MRName;
    }

    public void setMRName(String MRName) {
        this.MRName = MRName;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getMContent() {
        return MContent;
    }

    public void setMContent(String MContent) {
        this.MContent = MContent;
    }

    public String getMStatus() {
        return MStatus;
    }

    public void setMStatus(String MStatus) {
        this.MStatus = MStatus;
    }

    public String getMAuditor() {
        return MAuditor;
    }

    public void setMAuditor(String MAuditor) {
        this.MAuditor = MAuditor;
    }

    public String getMTime() {
        return MTime;
    }

    public void setMTime(String MTime) {
        this.MTime = MTime;
    }
}
