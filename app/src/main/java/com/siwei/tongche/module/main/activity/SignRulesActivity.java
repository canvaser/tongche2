package com.siwei.tongche.module.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;

/**
 * 签收规则
 */
public class SignRulesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("签收规则");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_sign_rules;
    }
}
