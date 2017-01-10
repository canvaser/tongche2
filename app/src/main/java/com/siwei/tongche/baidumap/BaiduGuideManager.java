package com.siwei.tongche.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2016-05-04.
 */
public class BaiduGuideManager {
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "TongChe";

    private static BaiduGuideManager mBaiduGuideManager;
    private String authinfo;
    private Activity mActvity;
    private BaiduGuideManager(){
    }

    public void initManager(Activity activity){
        mActvity=activity;
        if (initDirs()) {
            if(!BaiduNaviManager.isNaviInited()){
                initNavi();
            }
        }
    }

    public static BaiduGuideManager newInstance(){
        if(mBaiduGuideManager==null){
            mBaiduGuideManager=new BaiduGuideManager();
        }
        return mBaiduGuideManager;
    }
    public void startGuide(BNRoutePlanNode sNode, BNRoutePlanNode eNode){
        ArrayList<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(sNode);
//        BNRoutePlanNode Node=new BNRoutePlanNode( 121.520413, 31.308973,  "江湾体育场",  "江湾体育场", BNRoutePlanNode.CoordinateType.BD09LL);
//        list.add(Node);
        list.add(eNode);
        BaiduNaviManager.getInstance().launchNavigator(mActvity, list, 1, true, new DemoRoutePlanListener(sNode));
    }


    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }


        @Override
        public void onJumpToNavigator() {
            MyLogUtils.e("BNDemoGuideActivity");
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */
            Intent intent = new Intent(mActvity, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            mActvity.startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            MyLogUtils.e("算路失败");
            MyToastUtils.showToast("导航失败");
        }
    }


    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(mActvity, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                MyLogUtils.e(authinfo);
            }

            public void initSuccess() {
                MyLogUtils.e("百度导航引擎初始化成功");
                initSetting();
                if(onInitSuccessCallBack!=null){
                    onInitSuccessCallBack.OnInitSuccess();
                }
            }

            public void initStart() {
                MyToastUtils.showToast("百度导航引擎初始化开始");
            }

            public void initFailed() {
                MyToastUtils.showToast("百度导航引擎初始化失败");
            }


        },  null, ttsHandler, null);

    }

    private void initSetting(){
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }


    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    MyLogUtils.e("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    MyLogUtils.e("Handler : TTS play end");
                    break;
                }
                default :
                    break;
            }
        }
    };
    private OnInitSuccessCallBack onInitSuccessCallBack;
    public void setInitSuccessCallback(OnInitSuccessCallBack onInitSuccessCallBack){
        this.onInitSuccessCallBack=onInitSuccessCallBack;
    }
    public interface OnInitSuccessCallBack{
        public void OnInitSuccess();
    }
}
