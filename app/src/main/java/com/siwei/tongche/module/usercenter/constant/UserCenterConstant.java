package com.siwei.tongche.module.usercenter.constant;

/**
 * Created by ${viwmox} on 2016-12-27.
 */

public class UserCenterConstant {



    //发货单位,用户管理，驾驶员管理,车辆管理,设置,我的收藏,消息,对讲机
    public boolean[][] roles= {
            {true,false,false,false,true,true,true,true},//驾驶员
            {false,false,false,false,true,true,true,true},//发货单位(普通)
            {false,true,true,true,true,true,true,true},//发货单位(管理员)
            {false,false,false,false,true,true,true,false},//收货单位(普通)
            {false,true,false,false,true,true,true,false},//收货单位(管理员)
            {false,false,false,true,true,true,true,true},//租赁公司(普通)
            {false,true,true,true,true,true,true,true},//租赁公司(管理员)
    };
}
