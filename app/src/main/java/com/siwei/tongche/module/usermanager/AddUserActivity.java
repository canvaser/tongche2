package com.siwei.tongche.module.usermanager;

import android.os.Bundle;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;

/**
 * 新建用户
 */
public class AddUserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新建用户");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_user;
    }
}
