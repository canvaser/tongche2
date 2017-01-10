package com.siwei.tongche.module.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class MapDetailInfoActivity extends BaseActivity {
    @Bind(R.id.mapView)
    MapView mMapView;
    BaiduMapUtilsManager mBaiduMapUtilsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("南通大明");
        initView();
    }

    private void initView() {
        mBaiduMapUtilsManager= BaiduMapUtilsManager.initManager(mMapView);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
            }
        });

        mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_CAR,new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude()));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_map_detail_info;
    }

    @OnClick({R.id.map_guide})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.map_guide://导航
                MyToastUtils.showToast("导航");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        //开启定位图层
        mMapView.getMap().setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
