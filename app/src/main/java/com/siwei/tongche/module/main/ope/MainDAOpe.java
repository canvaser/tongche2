package com.siwei.tongche.module.main.ope;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseDAOpe;
import com.siwei.tongche.dialog.SelectMenuBean;
import com.siwei.tongche.module.login.bean.UserInfo;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-01-12.
 */

public class MainDAOpe extends BaseDAOpe{



    ArrayList<String> mTypeData = new ArrayList<String>();

    ArrayList<SelectMenuBean> mMenuData = new ArrayList<SelectMenuBean>();

    public MainDAOpe(UserInfo userInfo) {
        initData(userInfo);
    }

    /**
     * 首页 加号菜单数据初始化
     * @param userInfo
     */
    private void  initData(UserInfo userInfo){
        switch (userInfo.getURoleCode()) {
            case AppConstants.USER_TYPE.TYPE_DRIVER://司机
                mMenuData.add(new SelectMenuBean("扫一扫  ", R.drawable.menu_scan));
                mMenuData.add(new SelectMenuBean("故障报告", R.drawable.menu_accident_report));
                mMenuData.add(new SelectMenuBean("加点油吧", R.drawable.menu_gas_station));
                break;
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT://发货单位工作人员
                mTypeData.add("任务");
                mTypeData.add("地图");
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                break;
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT://收货单位工作人员
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                //管理员
                if (userInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR) || userInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)) {
                    //是管理员可以发送任务
                    mMenuData.add(new SelectMenuBean("新建任务", R.drawable.menu_scan));
                    mTypeData.add("地图");
                    mTypeData.add("任务");
                    //
                } else {
                    mTypeData.add("小票");
                    mTypeData.add("任务");
                }
                break;
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT://租赁公司工作人员
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                break;
        }
    }

    public ArrayList<String> getmTypeData() {
        return mTypeData;
    }

    public void setmTypeData(ArrayList<String> mTypeData) {
        this.mTypeData = mTypeData;
    }

    public ArrayList<SelectMenuBean> getmMenuData() {
        return mMenuData;
    }

    public void setmMenuData(ArrayList<SelectMenuBean> mMenuData) {
        this.mMenuData = mMenuData;
    }
}
