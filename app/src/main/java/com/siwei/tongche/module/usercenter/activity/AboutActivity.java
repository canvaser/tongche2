package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.update.AutoUpdateManager;
import com.siwei.tongche.views.SDSimpleSetItemView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.about_version_code)
    TextView mVersion_code;//版本号

    @Bind(R.id.setting_layout_solution)//常见问题
     TextView solution;
    @Bind(R.id.setting_layout_function)
    TextView function;//功能介绍
    @Bind(R.id.setting_layout_markme)
    TextView markeme;//给我评分
    @Bind(R.id.about_call)
    TextView mAbout_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        JSONObject params=new JSONObject();
        MyHttpUtil.sendRequest(this, MyUrls.ABOUT, params, MyHttpUtil.ReturnType.OBJECT, null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    JSONObject data = (JSONObject) object;
                    if (data != null) {
                        String COMPANY_MOBILE=data.getString("COMPANY_MOBILE");
                        String COMPANY_EMAIL=data.getString("COMPANY_EMAIL");


                    }
                } catch (Exception e) {
                }
            }
        });
    }

    private void initView() {
        setTitle("关于我们");
        mVersion_code.setText(getVersion());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_about;
    }

    @OnClick({R.id.about_call,R.id.about_version_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.about_call:
                markCall();
                break;
            case R.id.about_version_code://检测版本更新
                AutoUpdateManager.checkAutoUpdate(this,true);
                break;
        }
    }

    private void markCall() {
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mAbout_call.getText()));
        startActivity(intent);
    }

    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "version "+ version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未知版本号";
        }
    }

}
