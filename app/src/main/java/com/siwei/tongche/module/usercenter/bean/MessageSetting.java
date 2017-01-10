package com.siwei.tongche.module.usercenter.bean;

/**
 * Created by HanJinLiang on 2016-06-06.
 */
public class MessageSetting {
//    ACCIDENT="1"，
//    SIGN_BACK="1"，
//    SIGN_LOSE="1"
//    TASK_PUST="1",
//    ARRIVED_MSG="1"
    private String ACCIDENT;
    private String SIGN_BACK;
    private String SIGN_LOSE;
    private String TASK_PUST;
    private String ARRIVED_MSG;

    public String getACCIDENT() {
        return ACCIDENT;
    }

    public void setACCIDENT(String ACCIDENT) {
        this.ACCIDENT = ACCIDENT;
    }

    public String getSIGN_BACK() {
        return SIGN_BACK;
    }

    public void setSIGN_BACK(String SIGN_BACK) {
        this.SIGN_BACK = SIGN_BACK;
    }

    public String getSIGN_LOSE() {
        return SIGN_LOSE;
    }

    public void setSIGN_LOSE(String SIGN_LOSE) {
        this.SIGN_LOSE = SIGN_LOSE;
    }

    public String getTASK_PUST() {
        return TASK_PUST;
    }

    public void setTASK_PUST(String TASK_PUST) {
        this.TASK_PUST = TASK_PUST;
    }

    public String getARRIVED_MSG() {
        return ARRIVED_MSG;
    }

    public void setARRIVED_MSG(String ARRIVED_MSG) {
        this.ARRIVED_MSG = ARRIVED_MSG;
    }
}
