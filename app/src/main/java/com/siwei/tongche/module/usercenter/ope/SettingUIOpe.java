package com.siwei.tongche.module.usercenter.ope;

import android.content.Context;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.views.SDSimpleSetItemView;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2017-01-12.
 */

public class SettingUIOpe extends BaseUIOpe{


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


    public SettingUIOpe(Context context, View containerView) {
        super(context, containerView);
        initView();

    }


    private void initView() {
        mSetting_layout_messageSetting.setTitleText("消息设置");
        mSetting_layout_messageSetting.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mSetting_layout_incomeSetting.setTitleText("工资设置");
        mSetting_layout_incomeSetting.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mSetting_layout_updatePwd.setTitleText("重置密码");
        mSetting_layout_updatePwd.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mSetting_layout_feedBack.setTitleText("意见反馈");
        mSetting_layout_feedBack.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mSetting_layout_about.setTitleText("关于我们");
        mSetting_layout_about.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        UserInfo userInfo = CacheUtils.getLocalUserInfo();
        if(userInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){
            mSetting_layout_incomeSetting.setVisibility(View.VISIBLE);
        }else{
            mSetting_layout_incomeSetting.setVisibility(View.GONE);
        }
    }



    public SDSimpleSetItemView getmSetting_layout_about() {
        return mSetting_layout_about;
    }

    public SDSimpleSetItemView getmSetting_layout_feedBack() {
        return mSetting_layout_feedBack;
    }

    public SDSimpleSetItemView getmSetting_layout_incomeSetting() {
        return mSetting_layout_incomeSetting;
    }

    public SDSimpleSetItemView getmSetting_layout_messageSetting() {
        return mSetting_layout_messageSetting;
    }

    public SDSimpleSetItemView getmSetting_layout_updatePwd() {
        return mSetting_layout_updatePwd;
    }
}
