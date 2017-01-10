package com.siwei.tongche.common;

import java.io.Serializable;

/**
 * Created by HanJinLiang on 2016-05-09.
 */
public class BaseSelectBean implements Serializable {
    private boolean isSelected;
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
