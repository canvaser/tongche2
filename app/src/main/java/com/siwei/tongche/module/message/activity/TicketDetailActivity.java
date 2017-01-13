package com.siwei.tongche.module.message.activity;

import android.os.Bundle;
import android.view.View;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;

/**
 * Created by ${viwmox} on 2017-01-04.
 * 小票详情
 */

public class TicketDetailActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("小票详情");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_express_detail;
    }
}
