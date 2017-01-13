package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.income_setting.IncomeSettingActivity;
import com.siwei.tongche.module.usercenter.ope.SettingUIOpe;
import com.siwei.tongche.views.SDSimpleSetItemView;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    SettingUIOpe uiOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe= new SettingUIOpe(this,rootView);
        setTitle("设置");
    }
    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.setting_layout_messageSetting,R.id.setting_layout_incomeSetting,R.id.setting_layout_feedBack,R.id.setting_layout_updatePwd,R.id.setting_layout_about})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_layout_messageSetting://消息设置
                startActivity(new Intent(this,MessageSettingActivity.class));
                break;
            case R.id.setting_layout_incomeSetting://工资设置
                startActivity(new Intent(this,IncomeSettingActivity.class));
                break;
            case R.id.setting_layout_feedBack://意见反馈
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
            case R.id.setting_layout_updatePwd://重置密码
                startActivity(new Intent(this,UpdatePwdActivity.class));
                break;
            case R.id.setting_layout_about://关于
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }
}
