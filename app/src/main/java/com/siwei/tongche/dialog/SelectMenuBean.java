package com.siwei.tongche.dialog;

/**
 * Created by HanJinLiang on 2016-12-15.
 * 首页顶部菜单
 *
 */

public class SelectMenuBean {
    private String menuName;
    private int iconRes;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public SelectMenuBean(String menuName, int iconRes) {
        this.menuName = menuName;
        this.iconRes = iconRes;
    }
}
