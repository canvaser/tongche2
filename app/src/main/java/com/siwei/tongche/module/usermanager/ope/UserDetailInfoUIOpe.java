package com.siwei.tongche.module.usermanager.ope;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.carmanager.bean.CarBean;
import com.siwei.tongche.module.usercenter.bean.UserBean;
import com.siwei.tongche.views.RoundImageView;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2017-01-11.
 */

public class UserDetailInfoUIOpe extends BaseUIOpe{


    @Bind(R.id.baseInfo_header)
    RoundImageView headIV;

    @Bind(R.id.detailInfo_name)
    TextView nameTV;

    @Bind(R.id.detailInfo_mobile)
    TextView phoneTV;

    @Bind(R.id.detailInfo_auditor)
    TextView shrTV;

    @Bind(R.id.detailInfo_bind_time)
    TextView dateTV;

    @Bind(R.id.check)
    AppCompatCheckBox checkBox;

    @Bind(R.id.btn_submit)
    Button sumbitBTN;


    public UserDetailInfoUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public void initView(UserBean data){
        ImageLoaderManager.getImageLoader().displayImage(data.getUHeadImg(),headIV);
        nameTV.setText(data.getUName());
        phoneTV.setText(data.getUPhone());
        shrTV.setText(data.getAuditor());
        dateTV.setText(data.getBindTime());
    }

    public AppCompatCheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getDateTV() {
        return dateTV;
    }

    public RoundImageView getHeadIV() {
        return headIV;
    }

    public TextView getNameTV() {
        return nameTV;
    }

    public TextView getPhoneTV() {
        return phoneTV;
    }

    public TextView getShrTV() {
        return shrTV;
    }

    public Button getSumbitBTN() {
        return sumbitBTN;
    }
}
