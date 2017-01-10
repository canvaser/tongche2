package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.income_setting.IncomeSettingActivity;
import com.siwei.tongche.views.SDSimpleSetItemView;

import butterknife.Bind;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.setting_layout_messageSetting)
    SDSimpleSetItemView mSetting_layout_messageSetting;

    @Bind(R.id.setting_layout_incomeSetting)
    SDSimpleSetItemView mSetting_layout_incomeSetting;

    @Bind(R.id.setting_layout_updatePwd)
    SDSimpleSetItemView mSetting_layout_updatePwd;
    @Bind(R.id.setting_layout_feedBack)
    SDSimpleSetItemView mSetting_layout_feedBack;
    @Bind(R.id.setting_layout_about)
    SDSimpleSetItemView mSetting_layout_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        initView();
    }

    private void initView() {
        mSetting_layout_messageSetting.setTitleText("消息设置");
        mSetting_layout_messageSetting.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);
        mSetting_layout_messageSetting.setOnClickListener(this);

        mSetting_layout_incomeSetting.setTitleText("工资设置");
        mSetting_layout_incomeSetting.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);
        mSetting_layout_incomeSetting.setOnClickListener(this);

        mSetting_layout_updatePwd.setTitleText("重置密码");
        mSetting_layout_updatePwd.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);
        mSetting_layout_updatePwd.setOnClickListener(this);

        mSetting_layout_feedBack.setTitleText("意见反馈");
        mSetting_layout_feedBack.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);
        mSetting_layout_feedBack.setOnClickListener(this);

        mSetting_layout_about.setTitleText("关于我们");
        mSetting_layout_about.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);
        mSetting_layout_about.setOnClickListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
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
