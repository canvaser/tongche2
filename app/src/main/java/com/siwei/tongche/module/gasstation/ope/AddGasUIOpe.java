package com.siwei.tongche.module.gasstation.ope;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class AddGasUIOpe extends BaseUIOpe{

    //金额
    @Bind(R.id.et_cost)
    EditText costEt;

    //数量
    @Bind(R.id.et_num)
    EditText numEt;

    //公里数
    @Bind(R.id.et_miles)
    EditText milesEt;

    //加油人
    @Bind(R.id.et_man)
    EditText manEt;

    //手机号
    @Bind(R.id.et_phone)
    EditText phoneEt;

    //时间
    @Bind(R.id.et_date)
    EditText dateEt;




    public AddGasUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public EditText getCostEt() {
        return costEt;
    }

    public EditText getDateEt() {
        return dateEt;
    }

    public EditText getManEt() {
        return manEt;
    }

    public EditText getMilesEt() {
        return milesEt;
    }

    public EditText getNumEt() {
        return numEt;
    }

    public EditText getPhoneEt() {
        return phoneEt;
    }
}
