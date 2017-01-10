package com.siwei.tongche.module.main.ope;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.scan.fragment.BindUnitFragmet;
import com.siwei.tongche.module.scan.fragment.DriverInfoFragment;
import com.siwei.tongche.module.scan.fragment.TicketFrag;
import com.siwei.tongche.module.scan.fragment.UserInfoFrag;
import com.siwei.tongche.utils.CacheUtils;

import java.io.Serializable;

/**
 * Created by ${viwmox} on 2017-01-09.
 */

public class ScanResultOpe implements Serializable{


    public ScanResultOpe(){}

    //根据扫码的结果去决定跳转到哪个界面
    public Fragment analyze(Context context, String data){
        Fragment fragment = null;
        UserInfo scaninfo = CacheUtils.getLocalUserInfo();
        UserInfo myInfo = CacheUtils.getLocalUserInfo();

        /**驾驶员*/

        //7.1.1.1当驾驶员扫描租赁单位管理员二维码，页面出现管理员信息与【绑定单位】按钮，驾驶员点击【绑定单位】，租赁单位管理员即收到员工消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT) && scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","绑定单位");
            return fragment;
        }

        //7.1.1.2当驾驶员扫描发货单位管理员二维码，页面出现管理员信息与“绑定发货单位”按钮，驾驶员点击“绑定发货单位”，发货单位管理员即收到员工消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT) && scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","绑定发货单位");
            return fragment;
        }

        //7.1.1.3 当发货单位和单位为同一单位时，系统检测到驾驶员在尚未绑定单位时第一次扫码单位为发货单位，页面跳转到【绑定单位】，管理员同意后驾驶员的单位和发货单位同时显示为“已绑定”。

        /**发货单位*/
        //当普通用户扫描管理员二维码，页面出现管理员信息与【绑定单位】按钮，用户点击【绑定单位】，管理员即收到用户消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","绑定单位");
            return fragment;
        }

        //当管理员扫描普通用户二维码，页面出现普通用户信息与【添加用户】按钮，用户点击【添加用户】，普通用户即收到添加消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }



        //创建者扫描普通用户二维码，页面跳转后可指定该用户是否为管理员。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new UserInfoFrag();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }


        //当管理员扫描驾驶员二维码，页面出现驾驶员信息与【添加】按钮，用户点击【添加】，驾驶员即收到添加消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_SENDER_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new DriverInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加");
            return fragment;
        }

        /**收货单位*/

        //当普通用户扫描管理员二维码，页面出现管理员信息与【绑定单位】按钮，用户点击【绑定单位】，管理员即收到用户消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","绑定单位");
            return fragment;
        }

        //当管理员扫描普通用户二维码，页面出现普通用户信息与【添加用户】按钮，用户点击【添加用户】，普通用户即收到添加消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }


        //当签收员扫描驾驶员二维码，页面跳转到小票列表页，页面顶部为当前驾驶员的小票信息。签收员左划点击【签收】即可签收。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){
            fragment = new TicketFrag();
            Bundle bundle = new Bundle();
            return fragment;
        }

        //创建者扫描普通用户二维码，页面跳转后可指定该用户是否为管理员。（管理员扫描普通用户二维码无此功能）。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new UserInfoFrag();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }



        /**租赁单位*/
        //当普通用户扫描管理员二维码，页面出现管理员信息与【绑定单位】按钮，用户点击【绑定单位】，管理员即收到用户消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","绑定单位");
            return fragment;
        }


        //当管理员扫描普通用户二维码，页面出现普通用户信息与【添加用户】按钮，用户点击【添加用户】，普通用户即收到添加消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new BindUnitFragmet();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }


        //创建者扫描普通用户二维码，页面跳转后可指定该用户是否为管理员。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)
                &&myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR)&&scaninfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            fragment = new UserInfoFrag();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加用户");
            return fragment;
        }


        //当管理员、创建者扫描驾驶员二维码，页面出现驾驶员信息与【添加】按钮，用户点击【添加】，驾驶员即收到添加消息，点击【接受】即可。
        if(myInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT) && scaninfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)
                &&(myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)||myInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR))){
            fragment = new DriverInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("button","添加");
            return fragment;
        }



        return fragment;
    }


}
