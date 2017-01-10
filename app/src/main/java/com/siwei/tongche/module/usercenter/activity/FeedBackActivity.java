package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.feedBack_content)
    EditText feedBack_content;

    @Bind(R.id.feedBack_RatingBar)
    RatingBar feedBack_RatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        setTitle("意见反馈");
        feedBack_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
                    feedBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_feedback;
    }


    @OnClick(R.id.btn_feedBack)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_feedBack:
                feedBack();
                break;
        }
    }

    private void feedBack() {
        String content=feedBack_content.getText().toString().trim();
        if(MyRegexpUtils.isEmpty(content)){
            MyToastUtils.showToast("请输入意见反馈内容");
            return;
        }

        float rating=feedBack_RatingBar.getRating();
//        USERID、CONTENT(反馈内容)、STAR(评论星级)
        JSONObject params=new JSONObject();
        params.put("USERID", CacheUtils.getLocalUserInfo().getUID());
        params.put("CONTENT",content);
        params.put("STAR",rating);
        MyHttpUtil.sendRequest(this, MyUrls.FEEDBACK, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    if ((boolean) object) {
                        showSuccessDailog();
                    }
                } catch (Exception e) {
                }
            }
        });

    }

    private void showSuccessDailog() {
        MyToastUtils.showToast("反馈成功，谢谢！");
        finish();
    }

}
