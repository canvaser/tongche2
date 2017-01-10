package com.siwei.tongche.module.main.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.module.main.activity.MapCategoryActivity;
import com.siwei.tongche.module.main.activity.MapDetailInfoActivity;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HanJinLiang on 2016-12-23.
 * 地图Fragment
 */

public class MapInfoFragment extends Fragment {
    @Bind(R.id.toggle_selectLayout)
    LinearLayout mToggle_selectLayout;

    @Bind(R.id.layout_top_select)
    LinearLayout mLayout_top_select;


    @Bind(R.id.mapView)
    MapView mMapView;
    BaiduMapUtilsManager mBaiduMapUtilsManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_map,container,false);
        ButterKnife.bind(this,view);//绑定
        initView();
        return view;
    }

    private void initView() {
        mBaiduMapUtilsManager=BaiduMapUtilsManager.initManager(mMapView);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
            }
        });

//        mBaiduMapUtilsManager.addOverlayRandom(new BaiduMapUtilsManager.OnMyMarkerClick() {
//            @Override
//            public void onMyMarkerClick(int index) {
//                MyToastUtils.showToast("index=="+index);
//                startActivity(new Intent(getContext(),MapDetailInfoActivity.class));
//            }
//        });
    }

    @OnClick({R.id.toggle_selectLayout,R.id.layout_map_project_today,R.id.layout_map_project_my,R.id.layout_map_project_nearby
            ,R.id.layout_map_car_today,R.id.layout_map_car_online,R.id.layout_map_mixingStation_nearly})
    public void onClick(View view){
        String title="";
        switch (view.getId()){
            case R.id.toggle_selectLayout:
                toggleSelectLayout();
                return;
            case R.id.layout_map_project_today://今日工程
                title="今日工程";
                break;
            case R.id.layout_map_project_my://我的工程
                title="我的工程";
                break;
            case R.id.layout_map_project_nearby://我的工程
                title="我的工程";
                break;
            case R.id.layout_map_car_today://今日车辆
                title="今日车辆";
                break;
            case R.id.layout_map_car_online://排队车辆
                title="排队车辆";
                break;
            case R.id.layout_map_mixingStation_nearly://附近搅拌站
                title="附近搅拌站";
                break;
        }
        startActivity(new Intent(getContext(), MapCategoryActivity.class).putExtra("title",title));
//        toggleSelectLayout();
    }

    LayoutStatus mLayoutStatus=LayoutStatus.OPEN;
    /**
     * 切换顶部显示
     */
    private void toggleSelectLayout() {
        if(mLayoutStatus==LayoutStatus.ANIMING){//动画中  直接返回
            return;
        }
        final  int totalHeight=DensityUtil.dip2px(140);
        if(mLayoutStatus==LayoutStatus.OPEN){//当前状态为打开  关闭
            mLayoutStatus=LayoutStatus.ANIMING;
            ValueAnimator valueAnimator= ValueAnimator.ofFloat(0,1).setDuration(500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value=(float)valueAnimator.getAnimatedValue();
                    if(value==1){
                        mLayoutStatus=LayoutStatus.CLOSE;
                    }
                    ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) mLayout_top_select.getLayoutParams();
                    params.topMargin= (int) (-value*totalHeight);
                    mLayout_top_select.setLayoutParams(params);
                    mLayout_top_select.setBackgroundColor(Color.argb(255-(int) (255*value),66,90,116));
                }
            });
            valueAnimator.start();
        }else if(mLayoutStatus==LayoutStatus.CLOSE){//当前状态为关闭  打开
            mLayoutStatus=LayoutStatus.ANIMING;
            ValueAnimator valueAnimator= ValueAnimator.ofFloat(1,0).setDuration(500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value=(float)valueAnimator.getAnimatedValue();
                    if(value==1){
                        mLayoutStatus=LayoutStatus.OPEN;
                    }
                    ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) mLayout_top_select.getLayoutParams();
                    params.topMargin= (int) (-value*totalHeight);
                    mLayout_top_select.setLayoutParams(params);
                    mLayout_top_select.setBackgroundColor(Color.argb(255-(int) (255*value),66,90,116));
                }
            });
            valueAnimator.start();
        }

    }
    enum LayoutStatus{
        OPEN,CLOSE,ANIMING
    }
}
