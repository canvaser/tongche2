package com.siwei.tongche.baidumap;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public abstract class BaiduMapBaseActivity extends BaseActivity {
    @Bind(R.id.baiduMap_content)
    public LinearLayout mBaiduMap_content;


    private MapView mMapView;
    private BaiduMap mBaiduMap;


    @Override
    public int getContentView() {
        return R.layout.activity_baidu_map_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = new MapView(this);
        mBaiduMap_content.addView(mMapView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        initBaiduMap();
    }

    private void initBaiduMap() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
        //开启交通图
        mBaiduMap.setTrafficEnabled(false);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //设置当前位置显示
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        //跳转到当前位置
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(50)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(CacheUtils.getLatitudes())
                .longitude(CacheUtils.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);


        LatLng ll = new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                MyLogUtils.e("onMapLoaded");
                isMapLoadedFinish=true;
                //标注
                if(mPoints!=null){
                    addOverlay(mPoints,mPicSrcId);
                }
                //路线
                if(mLinePoints!=null){
                    addLine(mLinePoints,mIsDottedLine,mStartSrcId,mEndSrcId);
                }
            }
        });

    }
    boolean isMapLoadedFinish=false;//地图时候加载完成

    //添加覆盖物
    ArrayList<LatLng> mPoints;
    int mPicSrcId;
    public void addOverlay(ArrayList<LatLng> points, int picSrcId) {
        mPoints=points;
        mPicSrcId=picSrcId;
        if(!isMapLoadedFinish){//setOnMapLoadedCallback没有执行  等待加载完成
            return;
        }
        mBaiduMap.clear();
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(picSrcId);
        List<OverlayOptions> allOpitons=new ArrayList<OverlayOptions>();

        for(int i=0;i<points.size(); i++) {
            MarkerOptions ooA = new MarkerOptions().position(points.get(i)).icon(bdA);
            Bundle bundle=new Bundle();
            bundle.putInt("index", i);
            ooA.extraInfo(bundle);
            //掉下动画
//            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//            //生长动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
            allOpitons.add(ooA);
        }
        mBaiduMap.addOverlays(allOpitons);//添加多个覆盖物
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMyMarkerClick(marker.getExtraInfo().getInt("index"));
                return false;
            }
        });

        if(points.size()>0){//有多个标注点，一次性显示在地图页面
            //动态的显示出所有的标注点
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(LatLng latLng:points){
                //点添加到集合中
                builder.include(latLng);
            }
            MyLogUtils.e("动态的显示出所有的标注点");
            LatLngBounds latlngBounds = builder.build();
            //mBaiduMap.setOnMapLoadedCallback  这个没有回调之前mMapView.getWidth()有问题，暂时先改成父控件的宽高
            //MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds,mMapView.getWidth(),mMapView.getHeight());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds,mBaiduMap_content.getWidth(),mBaiduMap_content.getHeight());
            mBaiduMap.animateMapStatus(u);
        }
    }



    public abstract  void onMyMarkerClick(int index);

    @Override
    protected void onDestroy() {
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(false);
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

    @OnClick(R.id.jump_current_location)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.jump_current_location://返回当前定位
                //跳转到当前位置
                LatLng ll = new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                break;
        }
    }


    /**
     * 将地图移到指定位置
     * @param latitudes
     * @param longitude
     */
    public void moveTo(double latitudes,double longitude){
        LatLng ll = new LatLng(latitudes, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private List<LatLng> mLinePoints;
    private boolean mIsDottedLine;
    private int mStartSrcId;
    private int mEndSrcId;

    /**
     * // 添加普通折线绘制
     * @param points
     * @param isDottedLine  是否虚线
     */
    public void addLine(List<LatLng> points, boolean isDottedLine, int startSrcId, int endSrcId){
        if(points==null||points.size()<2){
            return;
        }
        mLinePoints=points;
        mIsDottedLine=isDottedLine;
        mStartSrcId=startSrcId;
        mEndSrcId=endSrcId;

        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        mPolyline.setDottedLine(isDottedLine);

        //动态的显示出所有的标注点
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : points) {
            //点添加到集合中
            builder.include(latLng);
        }
        MyLogUtils.e("动态的显示出所有的标注点");
        LatLngBounds latlngBounds = builder.build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds, mBaiduMap_content.getWidth(), mBaiduMap_content.getHeight());
        mBaiduMap.animateMapStatus(u);

        //起点 以及终点
        addOverlay(points.get(0), startSrcId);
        addOverlay(points.get(points.size()-1),endSrcId);

    }

    /**
     * 添加单个view
     * @param point  坐标
     * @param picSrcId 图片资源
     */
    public void addOverlay(LatLng point, int picSrcId) {
        if(!isMapLoadedFinish){//setOnMapLoadedCallback没有执行  等待加载完成
            return;
        }
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(picSrcId);
            MarkerOptions ooA = new MarkerOptions().position(point).icon(bdA);
            //掉下动画
//            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//            //生长动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
        mBaiduMap.addOverlay(ooA);//添加多个覆盖物
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMyMarkerClick(marker.getExtraInfo().getInt("index"));
                return false;
            }
        });

    }
}
