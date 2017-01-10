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
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.main.bean.WaittingCarInfoBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;

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
    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.listView)
    ListView mWaittingCarList;

    MyBaseAdapter<WaittingCarInfoBean> mWaittingAdapter;

    ArrayList<WaittingCarInfoBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("小票列表");
        initView();
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
                    mPtrFrame.refreshComplete();
                     if(object!=null){
                         mData.clear();
                         mData.addAll((Collection<? extends WaittingCarInfoBean>) object);
                         mWaittingAdapter.notifyDataSetChanged();
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
                        mWaittingAdapter.notifyDataSetChanged();
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

    private void initView() {
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(this, mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mWaittingCarList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);

        mWaittingAdapter=new MyBaseAdapter<WaittingCarInfoBean>(mData,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, final WaittingCarInfoBean waittingCarInfoBean) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(WaittingCarActivity.this,convertView,parent,R.layout.item_waitting_car,position);
                 viewHolder.setHeaderImageLoader(R.id.imageView,waittingCarInfoBean.getUHeadImg());//司机头像
                viewHolder.setText(R.id.waitting_driver_name,waittingCarInfoBean.getUName());//司机姓名
                viewHolder.setText(R.id.waitting_plate_number,waittingCarInfoBean.getVPlateNumber());//车牌号
                viewHolder.setText(R.id.waitting_carNo,waittingCarInfoBean.getV_NO());//车号
                viewHolder.setText(R.id.waitting_car_brand,waittingCarInfoBean.getVBrand());//车品牌
                viewHolder.setText(R.id.waitting_car_size,waittingCarInfoBean.getVGgxh());//车大小
                viewHolder.setText(R.id.waitting_unit_name,waittingCarInfoBean.getUnitName());//单位名称
                viewHolder.setText(R.id.wait_time,"已等待:"+getTimeWaitting(waittingCarInfoBean.getVGoBackTime()));
                viewHolder.getView(R.id.waitting_driver_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CallUtils.gotoCall(WaittingCarActivity.this,waittingCarInfoBean.getUPhone());
                    }
                });
                return viewHolder.getConvertView();
            }
        };
        mWaittingCarList.setAdapter(mWaittingAdapter);
    }

    /**
     * 计算时间间隔
     * @param startTime
     * @return
     */
    private String getTimeWaitting(String startTime){
        long startTimeSec=MyFormatUtils.toLong(startTime);
        long currentTimeSec=System.currentTimeMillis()/1000;
        long waittingSec=currentTimeSec-startTimeSec;
        if(waittingSec<60){//1分钟以下
            return waittingSec+"";
        }else if(waittingSec<60*60&&waittingSec>=60){//一分钟以上 一小时以下
            return waittingSec/60+":"+waittingSec%60;
        }else if(waittingSec>=3600){
            return waittingSec/3600+":"+(waittingSec%3600)/60+":"+(waittingSec%3600)%60 ;
        }
        return null;
    }
    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }
}
