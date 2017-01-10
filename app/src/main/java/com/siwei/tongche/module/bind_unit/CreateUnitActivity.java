package com.siwei.tongche.module.bind_unit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class CreateUnitActivity extends BaseActivity {
    @Bind(R.id.rg_allUnit)
    RadioGroup mAllUnit;

    int UnitType = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择单位类别");
        initView();
    }

    private void initView() {
        mAllUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.unit_fahuo://发货
                        UnitType = 1;
                        break;
                    case R.id.unit_shouhuo://收货
                        UnitType = 3;
                        break;
                    case R.id.unit_zulin://租赁
                        UnitType = 2;
                        break;

                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_create_unit;
    }

    @OnClick({R.id.btn_createUnit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_createUnit://创建单位
                createUnit();
                break;
        }
    }

    private void createUnit() {
        String UnitName = getIntent().getStringExtra("UnitName");
        JSONObject params=new JSONObject();
        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UnitName", UnitName);
        params.put("UnitType", UnitType);
        MyHttpUtil.sendRequest(this, MyUrls.CREATE_UNIT, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    if ((Boolean) object) {
                        MyToastUtils.showToast("单位创建成功");
                        finish();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

}
