package com.siwei.tongche.module.message.bean;

/**
 * Created by HanJinLiang on 2016-06-06.
 */
public class CompanyUserList {
//  USER_ID："",USER_NAME:"用户昵称"
    private String USER_ID;
    private String USER_NAME;
    private String HEADER_IMG;

    public String getHEADER_IMG() {
        return HEADER_IMG;
    }

    public void setHEADER_IMG(String HEADER_IMG) {
        this.HEADER_IMG = HEADER_IMG;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }
}
