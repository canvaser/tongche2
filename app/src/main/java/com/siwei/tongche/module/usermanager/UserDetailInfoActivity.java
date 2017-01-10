package com.siwei.tongche.module.usermanager;

import android.os.Bundle;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;

public class UserDetailInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用户详情");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_detail_info;
    }
}
