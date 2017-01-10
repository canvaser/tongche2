package com.siwei.tongche.module.gasstation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.gasstation.ope.AddGasUIOpe;

import butterknife.OnClick;

/**
 * 加油记录
 */
public class AddGasActivity extends BaseActivity {


    AddGasUIOpe uiOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe= new AddGasUIOpe(this,rootView);
        setTitle("加点油吧");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_gas;
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:

                break;
        }
    }


}
