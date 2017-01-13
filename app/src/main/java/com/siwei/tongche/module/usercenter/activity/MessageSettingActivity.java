package com.siwei.tongche.module.usercenter.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.kyleduo.switchbutton.SwitchButton;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.usercenter.bean.MessageSetting;
import com.siwei.tongche.module.usercenter.ope.MessageSettingUIOpe;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;

import java.util.List;

import butterknife.Bind;

/**
 * 消息设置
 */
public class MessageSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    private MessageSetting mMessageSetting;

    MessageSettingUIOpe uiOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe= new MessageSettingUIOpe(this,rootView);
        initView();
        loadData();
    }

    private void loadData() {
        JSONObject params=new JSONObject();
        params.put("USERID", CacheUtils.getLocalUserInfo().getUID());
        MyHttpUtil.sendRequest(this, MyUrls.GET_MESSAGE_SETTING, params, MyHttpUtil.ReturnType.OBJECT, MessageSetting.class, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                        mMessageSetting = (MessageSetting) object;
                    if (mMessageSetting != null) {
                        uiOpe.getSb_lists().get(0).setChecked(string2Boolean(mMessageSetting.getACCIDENT()));
                        uiOpe.getSb_lists().get(1).setChecked(string2Boolean(mMessageSetting.getSIGN_BACK()));
                        uiOpe.getSb_lists().get(2).setChecked(string2Boolean(mMessageSetting.getSIGN_LOSE()));
                        uiOpe.getSb_lists().get(3).setChecked(string2Boolean(mMessageSetting.getTASK_PUST()));
                        uiOpe.getSb_lists().get(4).setChecked(string2Boolean(mMessageSetting.getARRIVED_MSG()));
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * 1 0 转 true false
     * @param strBool
     * @return
     */
    private boolean string2Boolean(String strBool){
        if(strBool!=null&&strBool.equals("1")){
            return true;
        }
        return false;
    }

    /**
     *true false转  1 0
     * @param booleanValue
     * @return
     */
    private String boolean2Str(boolean booleanValue){
        if(booleanValue){
            return "1";
        }
        return "0";
    }

    private void initView() {
        setTitle("消息设置");
        for (SwitchButton switchButton:uiOpe.getSb_lists()){
            switchButton.setBackMeasureRatio(2.0f);//设置背景长度
            switchButton.setOnCheckedChangeListener(this);
        }

    }

    @Override
    public int getContentView() {
        return R.layout.activity_message_setting;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(mMessageSetting==null){
            return;
        }
        switch (buttonView.getId()){
            case R.id.sb_all://全部
                for(SwitchButton switchButton:uiOpe.getSb_lists()){
                    if(switchButton!=buttonView){
                        switchButton.setChecked(isChecked);
                    }
                }
                break;
            case R.id.sb_express://小票
                mMessageSetting.setSIGN_BACK(boolean2Str(isChecked));
                break;
            case R.id.sb_car://车辆
                mMessageSetting.setSIGN_LOSE(boolean2Str(isChecked));
                break;
            case R.id.sb_user://用户
                mMessageSetting.setTASK_PUST(boolean2Str(isChecked));
                break;
            case R.id.sb_task://任务
                mMessageSetting.setARRIVED_MSG(boolean2Str(isChecked));
                break;
            case R.id.sb_notify://公告
                mMessageSetting.setARRIVED_MSG(boolean2Str(isChecked));
                break;
            case R.id.sb_system://系统
                mMessageSetting.setARRIVED_MSG(boolean2Str(isChecked));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSetting();
    }

    private void saveSetting() {
        if(mMessageSetting==null){
            return;
        }
//        USERID、ACCIDENT(车辆故障)、SIGN_BACK(签收
//                退回)、SIGN_LOSE(签收损失) （传 0 关闭 1 开启）
//        TASK_PUST(新任务推送),ARRIVED_MSG(进站提醒)
        JSONObject params=new JSONObject();
        params.put("USERID", CacheUtils.getLocalUserInfo().getUID());
        params.put("ACCIDENT",mMessageSetting.getACCIDENT());
        params.put("SIGN_BACK",mMessageSetting.getSIGN_BACK());
        params.put("SIGN_LOSE", mMessageSetting.getSIGN_LOSE());
        params.put("TASK_PUST",mMessageSetting.getTASK_PUST());
        params.put("ARRIVED_MSG",mMessageSetting.getARRIVED_MSG());
        MyHttpUtil.sendRequest(this, MyUrls.MESSSAGE_SETTING, params, MyHttpUtil.ReturnType.BOOLEAN, MessageSetting.class, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    if ((boolean)object) {
                        MyLogUtils.e("设置成功");
                    }
                    finish();
                } catch (Exception e) {
                }
            }
        });
    }
}
