package com.siwei.tongche.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.events.EventTag;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.sunday.busevent.SDBaseEvent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 新建消息
 */
public class BuildMessageActivity extends BaseActivity {
    private final int SELECT_TARGET=0X61;//选目标人
    @Bind(R.id.build_targets)
    TextView mBuild_targets;//发送给谁

    @Bind(R.id.build_message_content)
    EditText mMessage_content;//消息内容

    private String mTargets_ids;

    @Override
    public int getContentView() {
        return R.layout.activity_build_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("发送公告");
        initView();

        String targets=getIntent().getStringExtra("targets");
        mTargets_ids=getIntent().getStringExtra("targets_ids");
        isSelectedStr=getIntent().getStringArrayListExtra("isSelectedStr");
        mBuild_targets.setText(targets);
    }

    private void initView() {
        mMessage_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
                    buildMessage();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.message_send,R.id.btn_select})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.message_send://发消息
                buildMessage();
                break;
            case R.id.btn_select:
                selectTargets();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void buildMessage() {
       String content= mMessage_content.getText().toString().trim();
        if(MyRegexpUtils.isEmpty(content)){
            MyToastUtils.showToast("请输入发送内容");
            mMessage_content.setText("");
            mMessage_content.requestFocus();
            return;
        }
        if(MyRegexpUtils.isEmpty(mTargets_ids)){
            MyToastUtils.showToast("请选择接收人");
            selectTargets();
            return;
        }
//        USER_ID(用户ID)、接收人(JSR_IDS  多个用，号隔开)、消息内容（CONTENT）
        JSONObject params=new JSONObject();
        params.put("USER_ID", CacheUtils.getLocalUserInfo().getUID());
        params.put("JSR_IDS",mTargets_ids);
        params.put("CONTENT",content);
        MyHttpUtil.sendRequest(this, MyUrls.BUILD_MESSAGE, params, MyHttpUtil.ReturnType.BOOLEAN,null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    if ((boolean) object) {
                        MyToastUtils.showToast("发送成功");
//                        EventBus.getDefault().post(new SDBaseEvent(null, EventTag.EVENT_BUILD_MESSAGES));
//                        finish();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public void  selectTargets(){
        MyLogUtils.e(isSelectedStr==null?"isSelectedStr is null":"isSelectedStr not null"+isSelectedStr.toString());
        startActivityForResult(new Intent(this, SelectTargetActivity.class).putStringArrayListExtra("isSelectedStr",isSelectedStr), SELECT_TARGET);
    }
    ArrayList<String> isSelectedStr;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==SELECT_TARGET){
            String targets=data.getStringExtra("targets");
            mTargets_ids=data.getStringExtra("targets_ids");
            isSelectedStr=data.getStringArrayListExtra("isSelectedStr");
            mBuild_targets.setText(targets);
        }
    }
}
