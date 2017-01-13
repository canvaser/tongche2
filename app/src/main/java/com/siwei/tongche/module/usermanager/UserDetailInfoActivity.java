package com.siwei.tongche.module.usermanager;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.carmanager.bean.CarBean;
import com.siwei.tongche.module.usercenter.bean.UserBean;
import com.siwei.tongche.module.usermanager.ope.UserDetailInfoUIOpe;

import java.util.Date;

import butterknife.OnClick;

public class UserDetailInfoActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    UserBean data;

    UserDetailInfoUIOpe uiOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用户详情");
        data = (UserBean) getIntent().getSerializableExtra("data");
        uiOpe= new UserDetailInfoUIOpe(this,rootView);
        uiOpe.initView(data);
        uiOpe.getCheckBox().setOnCheckedChangeListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_detail_info;
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
