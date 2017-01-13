package com.siwei.tongche.module.usercenter.ope;

import android.content.Context;
import android.view.View;

import com.kyleduo.switchbutton.SwitchButton;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2017-01-12.
 */

public class MessageSettingUIOpe extends BaseUIOpe{

    @Bind({R.id.sb_all,R.id.sb_express, R.id.sb_car,R.id.sb_user,R.id.sb_task,R.id.sb_notify,R.id.sb_system})
    List<SwitchButton> sb_lists;

    public MessageSettingUIOpe(Context context, View containerView) {
        super(context, containerView);
        init();
    }

    private void init(){
        UserInfo info = CacheUtils.getLocalUserInfo();
        //驾驶员没有 车辆和任务
        if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){
            sb_lists.get(2).setVisibility(View.GONE);
            sb_lists.get(4).setVisibility(View.GONE);
        }


        //收货人员普通没有 任务
        if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)&& info.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_NORMAL)){
            sb_lists.get(4).setVisibility(View.GONE);
        }

        //租赁公司没有 车辆和任务
        if(info.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_RENT_UNIT)){
            sb_lists.get(1).setVisibility(View.GONE);
            sb_lists.get(4).setVisibility(View.GONE);
        }

    }

    public List<SwitchButton> getSb_lists() {
        return sb_lists;
    }
}
