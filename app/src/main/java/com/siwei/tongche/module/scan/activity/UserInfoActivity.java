package com.siwei.tongche.module.scan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.scan.fragment.BindUnitFragmet;
import com.siwei.tongche.module.scan.fragment.DriverInfoFragment;
import com.siwei.tongche.module.scan.fragment.UserInfoFrag;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class UserInfoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = null;
        switch (getIntent().getStringExtra("fragment")){
            case "BindUnitFragmet":
                fragment = new BindUnitFragmet();
                break;
            case "DriverInfoFragment":
                fragment = new DriverInfoFragment();
                break;
            case "UserInfoFrag":
                fragment = new UserInfoFrag();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_root,fragment,fragment.getClass().getSimpleName()).commit();

    }



    @Override
    public int getContentView() {
        return R.layout.activity_userinfo;
    }
}
