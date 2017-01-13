package com.siwei.tongche.module.main.activity;

import android.os.Bundle;
import android.os.health.TimerStat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.common.OnFinishListener;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.main.bean.WaittingCarInfoBean;
import com.siwei.tongche.module.main.ope.WaittingUIOpe;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 排队车辆
 */
public class WaittingCarActivity extends BaseActivity {

    ArrayList<WaittingCarInfoBean> mData=new ArrayList<>();

    WaittingUIOpe mWaittingUIOpe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("小票列表");
        mWaittingUIOpe=new WaittingUIOpe(activity,rootView);
        mWaittingUIOpe.initView(new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                loadData();
            }
        });
        mWaittingUIOpe.getmPtrFrame().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaittingUIOpe.getmPtrFrame().autoRefresh(true);
            }
        }, 100);
        startTimer();
    }

    private void loadData() {
//        UID(用户ID)
        RequestParams params = new RequestParams();
//        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UID","b3ea8bbb180648d7b14ca6e30136b51b");
        MyHttpUtil.sendGetRequest(this, MyUrls.WAITTING_CAR_LIST, params, MyHttpUtil.ReturnType.ARRAY, WaittingCarInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    mWaittingUIOpe.getmPtrFrame().refreshComplete();
                     if(object!=null){
                         mData.clear();
                         mData.addAll((Collection<? extends WaittingCarInfoBean>) object);
                         mWaittingUIOpe.initView(mData);
                     }
                } catch (Exception e) {
                }
            }
        });
    }

    Timer mTimer;
    private void startTimer() {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWaittingUIOpe.getmWaittingAdapter().notifyDataSetChanged();
                    }
                });
            }
        },1000,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }
}
