package com.siwei.tongche.module.carmanager.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class ComplainUIOpe extends BaseUIOpe{

    @Bind(R.id.et_input)
    EditText departET;//单位名称

    @Bind(R.id.iv_addlicensea)
    ImageView lisenceaIV;//营业执照正面

    @Bind(R.id.iv_addlicenseb)
    ImageView lisencebIV;//营业执照反面

    @Bind(R.id.iv_originid)
    ImageView originIV;//组织机构代码

    @Bind(R.id.btn_save)
    Button submitBtn;//提交审核

    public ComplainUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public EditText getDepartET() {
        return departET;
    }

    public ImageView getLisenceaIV() {
        return lisenceaIV;
    }

    public ImageView getLisencebIV() {
        return lisencebIV;
    }

    public ImageView getOriginIV() {
        return originIV;
    }

    public Button getSubmitBtn() {
        return submitBtn;
    }
}
