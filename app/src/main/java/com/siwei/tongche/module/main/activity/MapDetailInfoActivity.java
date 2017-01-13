package com.siwei.tongche.module.main.activity;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduGuideManager;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.main.bean.MapMixingstation;
import com.siwei.tongche.module.main.bean.MapProject;
import com.siwei.tongche.module.main.bean.MapVehiclePosition;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.PerfectClickListener;

import butterknife.Bind;
import butterknife.OnClick;

public class MapDetailInfoActivity extends BaseActivity {
    public static final String MAP_TYPE="MapDetailInfoActivity.MAP_TYPE";
    public static final String MAP_DATA="MapDetailInfoActivity.MAP_DATA";

    public static final int MAP_PROJECT=0x71;
    public static final int MAP_MIXING_STATION=0x72;
    public static final int MAP_CAR=0x73;

    @Bind(R.id.mapView)
    MapView mMapView;
    BaiduMapUtilsManager mBaiduMapUtilsManager;


    @Bind(R.id.map_guide)
    ImageButton mMap_guide;

    @Bind(R.id.layout_bottom)
    LinearLayout mLayout_bottom;

    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.distance)
    TextView mDistance;
    @Bind(R.id.linkMan_name)
    TextView mLinkMan_name;
    @Bind(R.id.address)
    TextView mAddress;



    MapProject mMapProject;
    MapMixingstation mMapMixingstation;
    MapVehiclePosition mMapVehiclePosition;

    String mMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        switch (getIntent().getIntExtra(MAP_TYPE,0)){
            case MAP_PROJECT://工程
                mMapProject= (MapProject) getIntent().getSerializableExtra(MAP_DATA);
                mMobile=mMapProject.getPGCLinkTel();
                initMapProject();
                break;
            case MAP_MIXING_STATION://搅拌站
                mMapMixingstation= (MapMixingstation) getIntent().getSerializableExtra(MAP_DATA);
                mMobile=mMapMixingstation.getMSTel();
                initMixingStation();
                break;
            case MAP_CAR://车辆
                break;
        }

        mMap_guide.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //导航
                startGuide();
            }
        });

        mBaiduMapUtilsManager= BaiduMapUtilsManager.initManager(mMapView);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
                if(mMapProject!=null){
                    mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_PROJECT,
                            new LatLng(MyFormatUtils.toDouble(mMapProject.getPGCGpsy()), MyFormatUtils.toDouble(mMapProject.getPGCGpsx())));
                }else if(mMapMixingstation!=null){
                    mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_MIXINGSTATION,
                            new LatLng(MyFormatUtils.toDouble(mMapMixingstation.getMSGpsy()), MyFormatUtils.toDouble(mMapMixingstation.getMSGpsx())));
                }else if(mMapVehiclePosition!=null){
                    mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_MIXINGSTATION,
                            new LatLng(MyFormatUtils.toDouble(mMapMixingstation.getMSGpsy()), MyFormatUtils.toDouble(mMapMixingstation.getMSGpsx())));
                }

            }
        });
    }

    /**
     * 搅拌站
     */
    private void initMixingStation() {
         if(mMapMixingstation!=null){
             setTitle(mMapMixingstation.getMSName());
             mName.setText(mMapMixingstation.getMSName());
             showDistance(mMapMixingstation.getMSGpsy(),mMapMixingstation.getMSGpsx(),mDistance);
             mLinkMan_name.setText(mMapMixingstation.getMSLinkMan());
             mAddress.setText(mMapMixingstation.getMSAddress());
         }
    }

    /**
     * 工地
     */
    private void initMapProject() {
        if(mMapProject!=null){
            setTitle(mMapProject.getPGCName());
            mName.setText(mMapProject.getPGCName());
            showDistance(mMapProject.getPGCGpsy(),mMapProject.getPGCGpsx(),mDistance);
            mLinkMan_name.setText(mMapProject.getPGCLinkMan());
            mAddress.setText(mMapProject.getPGCAddress());
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_map_detail_info;
    }

    @OnClick({R.id.linkMan_name})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.linkMan_name://
                CallUtils.gotoCall(this,mMobile);
                break;
        }
    }

    /**
     * 开始导航
     */
    private void startGuide() {
        String longitude="";
        String latitudes="";
        String address="";
        switch (getIntent().getIntExtra(MAP_TYPE,0)){
            case MAP_PROJECT://工程
                longitude=mMapProject.getPGCGpsx();
                latitudes=mMapProject.getPGCGpsy();
                address=mMapProject.getPGCAddress();
                break;
            case MAP_MIXING_STATION://搅拌站
                longitude=mMapMixingstation.getMSGpsx();
                latitudes=mMapMixingstation.getMSGpsy();
                address=mMapMixingstation.getMSAddress();
                break;
            case MAP_CAR://车辆
                break;
        }

        MyToastUtils.showToast("开始导航，请稍等");
        //导航
        final BNRoutePlanNode sNode=new BNRoutePlanNode(CacheUtils.getLongitude(), CacheUtils.getLatitudes(), CacheUtils.getAddress(), null, BNRoutePlanNode.CoordinateType.BD09LL);
 		final BNRoutePlanNode eNode=new BNRoutePlanNode(MyFormatUtils.toDouble(longitude), MyFormatUtils.toDouble(latitudes), address, null, BNRoutePlanNode.CoordinateType.BD09LL);

        final BaiduGuideManager guideManager= BaiduGuideManager.newInstance();
        guideManager.initManager(this);
        if (BaiduNaviManager.isNaviInited()) {
            guideManager.startGuide(sNode, eNode);
        }else{
            guideManager.setInitSuccessCallback(new BaiduGuideManager.OnInitSuccessCallBack() {
                @Override
                public void OnInitSuccess() {
                    if (BaiduNaviManager.isNaviInited()) {
                        guideManager.startGuide(sNode, eNode);
                    }
                }
            });
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


    /**
     * 路线规划 得到路径
     */
    private void showDistance(String y,String x,final  TextView tvDistance){
        LatLng sLatLng=new LatLng(CacheUtils.getLatitudes(),CacheUtils.getLongitude());
        LatLng eLatLng=new LatLng(MyFormatUtils.toDouble(y),MyFormatUtils.toDouble(x));
        final RoutePlanSearch mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                //获取步行线路规划结果
            }
            public void onGetTransitRouteResult(TransitRouteResult result) {
                //获取公交换乘路径规划结果
            }
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                //获取驾车线路规划结果
                mSearch.destroy();
                if(result==null||result.getRouteLines()==null){
                    MyLogUtils.e("路径规划失败");
                    return;
                }
                //获取线路规划的第一条方案的线路距离   单位M
                int distance=result.getRouteLines().get(0).getDistance();
                String disStr=distance<1000?(distance+"米"):(MyFormatUtils.doubleSave1(distance/1000.0)+"公里");
                tvDistance.setText(disStr);
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        mSearch.setOnGetRoutePlanResultListener(listener);
        PlanNode stNode = PlanNode.withLocation(sLatLng);//new LatLng( CacheUtils.getLatitudes(),CacheUtils.getLongitude())
        PlanNode enNode = PlanNode.withLocation(eLatLng);

        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    /**
     * 根据经纬度查询地址
     * @param latLng
     */
    private void  getAddress(LatLng latLng){
        latLng=new LatLng(CacheUtils.getLatitudes(),CacheUtils.getLongitude());
        GeoCoder geoCoder = GeoCoder.newInstance();
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                }
                //经纬度转换成地址
                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    if (reverseGeoCodeResult == null ||  reverseGeoCodeResult.error != ReverseGeoCodeResult.ERRORNO.NO_ERROR) {
                       return;
                    }
                    String address=reverseGeoCodeResult.getAddress();
                    MyToastUtils.showToast(address);
                }
            });
    }
}
