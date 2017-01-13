package com.siwei.tongche.module.main.ope;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.common.OnFinishListener;
import com.siwei.tongche.module.main.activity.WaittingCarActivity;
import com.siwei.tongche.module.main.bean.WaittingCarInfoBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by HanJinLiang on 2017-01-12.
 */

public class WaittingUIOpe extends BaseUIOpe {
    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.listView)
    ListView mWaittingCarList;

    MyBaseAdapter<WaittingCarInfoBean> mWaittingAdapter;

    public WaittingUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public void initView(final OnFinishListener listener){
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(context, getmPtrFrame());
        getmPtrFrame().setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, getmWaittingCarList(), header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(listener!=null){
                    listener.onFinish(null);
                }

            }
        });
    }

    public void initView(ArrayList<WaittingCarInfoBean> mData) {
     if(mWaittingAdapter!=null){
         mWaittingAdapter.notifyDataSetChanged();
     }else{
         mWaittingAdapter=new MyBaseAdapter<WaittingCarInfoBean>(mData,context) {
             @Override
             public View getItemView(int position, View convertView, ViewGroup parent, final WaittingCarInfoBean waittingCarInfoBean) {
                 MyViewHolder viewHolder=MyViewHolder.getViewHolder(context,convertView,parent,R.layout.item_waitting_car,position);
                 viewHolder.setHeaderImageLoader(R.id.imageView,waittingCarInfoBean.getUHeadImg());//司机头像
                 viewHolder.setText(R.id.waitting_driver_name,waittingCarInfoBean.getUName());//司机姓名
                 viewHolder.setText(R.id.waitting_plate_number,waittingCarInfoBean.getVPlateNumber());//车牌号
                 viewHolder.setText(R.id.waitting_carNo,waittingCarInfoBean.getV_NO());//车号
                 viewHolder.setText(R.id.waitting_car_brand,waittingCarInfoBean.getVBrand());//车品牌
                 viewHolder.setText(R.id.waitting_car_size,waittingCarInfoBean.getVGgxh());//车大小
                 viewHolder.setText(R.id.waitting_unit_name,waittingCarInfoBean.getUnitName());//单位名称
                 viewHolder.setText(R.id.wait_time,"已等待:"+ TimeUtils.getTimeWaitting(waittingCarInfoBean.getVGoBackTime()));
                 viewHolder.getView(R.id.waitting_driver_name).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         CallUtils.gotoCall(context,waittingCarInfoBean.getUPhone());
                     }
                 });
                 return viewHolder.getConvertView();
             }
         };
         mWaittingCarList.setAdapter(mWaittingAdapter);
     }
    }


    public PtrClassicFrameLayout getmPtrFrame() {
        return mPtrFrame;
    }

    public ListView getmWaittingCarList() {
        return mWaittingCarList;
    }

    public MyBaseAdapter<WaittingCarInfoBean> getmWaittingAdapter() {
        return mWaittingAdapter;
    }
}
