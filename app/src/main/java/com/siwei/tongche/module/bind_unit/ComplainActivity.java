package com.siwei.tongche.module.bind_unit;

import android.os.Bundle;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.carmanager.activity.ComplainUIOpe;

/**
 * Created by ${viwmox} on 2016-12-28.
 */
//申诉
public class ComplainActivity extends BaseActivity{

    ComplainUIOpe uiOpe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe= new ComplainUIOpe(this,rootView);
        setTitle("申诉");

    }

    @Override
    public int getContentView() {
        return R.layout.activity_complain;
    }
}
