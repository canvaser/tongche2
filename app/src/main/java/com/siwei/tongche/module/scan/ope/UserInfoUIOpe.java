package com.siwei.tongche.module.scan.ope;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class UserInfoUIOpe extends BaseUIOpe{


    //当普通用户扫描管理员二维码，页面出现管理员信息与【绑定单位】按钮，用户点击【绑定单位】，管理员即收到用户消息，点击【接受】即可。
    //当管理员扫描普通用户二维码，页面出现普通用户信息与【添加用户】按钮，用户点击【添加用户】，普通用户即收到添加消息，点击【接受】即可。
    //创建者扫描普通用户二维码，页面跳转后可指定该用户是否为管理员。
    //当管理员扫描驾驶员二维码，页面出现驾驶员信息与【添加】按钮，用户点击【添加】，驾驶员即收到添加消息，点击【接受】即可。

    @Bind(R.id.userCenter_headerImg)
    ImageView headIV;
    @Bind(R.id.userCenter_userName)
    TextView nameTV;
    @Bind(R.id.userCenter_phone)
    TextView phoneTV;
    @Bind(R.id.userCenter_company)
    TextView addrTV;

    @Bind(R.id.check)
    AppCompatCheckBox checkBox;

    public UserInfoUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    //a------->b
    public void initView(String a,String b){
        switch (a+b){
            //case AppConstants.USER_UNIT_ROLE.ROLE_NORMAL+
        }
    }

    public TextView getAddrTV() {
        return addrTV;
    }

    public ImageView getHeadIV() {
        return headIV;
    }

    public TextView getNameTV() {
        return nameTV;
    }

    public TextView getPhoneTV() {
        return phoneTV;
    }

    public AppCompatCheckBox getCheckBox() {
        return checkBox;
    }
}
