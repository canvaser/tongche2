package com.siwei.tongche.module.gasstation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.gasstation.ope.AddGasListUIOpe;


/**
 * 故障报告列表
 */
public class AddGasListActivity extends BaseActivity {

    AddGasListUIOpe  uiOpe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe =new AddGasListUIOpe(this,rootView);
        setTitle("加点油吧");
        setRight(R.drawable.title_add,"");
    }



    @Override
    public void onRightText(View view) {
        super.onRightText(view);
        startActivity(new Intent(this,AddGasActivity.class));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }

}
