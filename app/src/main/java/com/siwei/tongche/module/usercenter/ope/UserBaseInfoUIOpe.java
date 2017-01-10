package com.siwei.tongche.module.usercenter.ope;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.views.RoundImageView;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2016-12-27.
 */

public class UserBaseInfoUIOpe extends BaseUIOpe{


    @Bind(R.id.baseInfo_header)
    RoundImageView mBaseInfo_header;//头像

    @Bind(R.id.baseInfo_name)
    TextView mBaseInfo_name;//用户名
    @Bind(R.id.baseInfo_mobile)
    TextView mBaseInfo_mobile;//手机号码
    @Bind(R.id.baseInfo_company)
    TextView mBaseInfo_company;//单位
    @Bind(R.id.baseInfo_driverid)
    TextView mBaseInfo_driverid;
    @Bind(R.id.baseInfo_layout_userType)
    LinearLayout baseInfo_layout_userType;
    @Bind(R.id.baseInfo_userType)
    TextView baseInfo_userType;
    @Bind(R.id.baseInfo_layout_driverid)
    LinearLayout baseInfo_layout_driverid;

    public UserBaseInfoUIOpe(Context context, View containerView) {
        super(context, containerView);
        init();
        checkUserType();
    }

    private void init(){
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        ImageLoaderManager.getHeaderImage(userInfo.getUHeadImg(), mBaseInfo_header);
        mBaseInfo_name.setText(userInfo.getUName());
        mBaseInfo_mobile.setText(userInfo.getUPhone());
        mBaseInfo_company.setText(MyRegexpUtils.isEmpty(userInfo.getUBindUnitTime())?"未绑定":userInfo.getUBindUnitTime());
        mBaseInfo_driverid.setText(userInfo.getUDriveNo());
    }

    private void checkUserType() {
        switch (CacheUtils.getLocalUserInfo().getURoleCode()){
            case AppConstants.USER_TYPE.TYPE_DRIVER://司机
                baseInfo_layout_driverid.setVisibility(View.VISIBLE);
                baseInfo_layout_userType.setVisibility(View.GONE);
                break;
        }
    }

    public TextView getmBaseInfo_company() {
        return mBaseInfo_company;
    }

    public TextView getmBaseInfo_driverid() {
        return mBaseInfo_driverid;
    }

    public RoundImageView getmBaseInfo_header() {
        return mBaseInfo_header;
    }

    public TextView getmBaseInfo_mobile() {
        return mBaseInfo_mobile;
    }

    public TextView getmBaseInfo_name() {
        return mBaseInfo_name;
    }
}
