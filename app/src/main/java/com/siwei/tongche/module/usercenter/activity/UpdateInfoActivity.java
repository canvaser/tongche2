package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 更改信息
 */
public class UpdateInfoActivity extends BaseActivity {
    public static final  int  UPDATE_NAME=0X21;//更改名字
    public static final  int  UPDATE_COMPANY=0X22;//更改公司

    @Bind(R.id.update_baseInfo)
    EditText mUpdateBaseInfo;//编辑框

    String mTitle;
    String mDefaultValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle=getIntent().getStringExtra("title");
        mDefaultValue=getIntent().getStringExtra("defaultValue");
        initView();
    }

    private void initView() {
        setTitle(mTitle);
        if(!MyRegexpUtils.isEmpty(mDefaultValue)){//显示默认值
            mUpdateBaseInfo.setText(mDefaultValue);
        }

    }

    @Override
    public int getContentView() {
        return R.layout.activity_update_baseinfo;
    }

    @OnClick(R.id.btn_save)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_save://保存
                saveValue();
                break;
        }
    }

    private void saveValue() {
       final String  newValue= mUpdateBaseInfo.getText().toString();
       final UserInfo userInfo= CacheUtils.getLocalUserInfo();
        if(MyRegexpUtils.isEmpty(newValue)){
            MyToastUtils.showToast("内容不能为空");
            return;
        }
        if(newValue.length()>8){
            MyToastUtils.showToast("长度不能超过8位");
            return;
        }
        switch (mTitle){
            case "更改姓名":
                updateUserName(mUpdateBaseInfo.getText().toString());
                break;
        }
    }


    /**
     * 修改姓名
     */
    public void updateUserName(String name){
        JSONObject params=new JSONObject();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UID",userInfo.getUID());
        params.put("UserName",name);
        MyHttpUtil.sendRequest(this, MyUrls.MODIFY_USER_NAME, params, MyHttpUtil.ReturnType.BOOLEAN, UserInfo.class, "获取中...",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                if((boolean)object){
                    finish();
                }
            }
        });
    }

}
