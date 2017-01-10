package com.siwei.tongche.module.usercenter.ope;

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

public class UpdateDriverIdUIOpe extends BaseUIOpe{

    @Bind(R.id.et_input)
    EditText inputET;//请输入驾驶证号码

    @Bind(R.id.iv_addpic)
    ImageView addPicIV;

    @Bind(R.id.btn_save)
    Button saveBtn;


    public UpdateDriverIdUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public ImageView getAddPicIV() {
        return addPicIV;
    }

    public EditText getInputET() {
        return inputET;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }
}
