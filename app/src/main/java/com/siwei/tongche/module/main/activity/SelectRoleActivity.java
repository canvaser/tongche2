package com.siwei.tongche.module.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.activitys.RegisterActivity;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择用户角色
 */
public class SelectRoleActivity extends BaseActivity {
    @Bind(R.id.select_role_group)
    RadioGroup mSelect_role_group;
    @Bind(R.id.select_role_driver)
    RadioButton select_role_driver;
    @Bind(R.id.select_role_undriver)
    RadioButton select_role_undriver;

    private int mRoleId=1;//默认是驾驶员

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择用户角色");
        initView();
    }

    private void initView() {
        mSelect_role_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.select_role_driver:
                        mRoleId=1;
                        break;
                    case R.id.select_role_undriver:
                        mRoleId=2;
                        break;
                }
            }
        });
        select_role_driver.setChecked(true);
    }

    @OnClick(R.id.select_role_commit)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.select_role_commit://选择用户角色
                selectRole();
                break;
        }
    }

    private void selectRole() {
        final UserInfo userInfo=CacheUtils.getLocalUserInfo();
//        UID(用户ID)、UUserType（用户类型（1 驾驶员、2非驾驶员））
        JSONObject params = new JSONObject();
        params.put("UID",userInfo.getUID());
        params.put("UUserType",mRoleId+"");
        MyHttpUtil.sendRequest(this, MyUrls.SELELCT_ROLE, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if((boolean)object){
                        //更新本地用户角色
                        userInfo.setUUserType(mRoleId+"");
                        CacheUtils.setLocalUserInfo(userInfo);
                        startActivity(new Intent(SelectRoleActivity.this,MainActivity.class));
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_select_role;
    }
}
