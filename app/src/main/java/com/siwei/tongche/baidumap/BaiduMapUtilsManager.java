package com.siwei.tongche.baidumap;

import android.os.Bundle;

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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseBean;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MyLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanJinLiang on 2016-12-26.
 */

public class BaiduMapUtilsManager {
    final public static int MAP_TYPE_CAR=0X51;
    final public static int MAP_TYPE_PROJECT=0X52;
    final public static int MAP_TYPE_MIXINGSTATION=0X53;
    private static BaiduMap  mBaiduMap;
    private BaiduMapUtilsManager(){
    }
    public static BaiduMapUtilsManager initManager(MapView mapView){
        mBaiduMap=mapView.getMap();
        return new BaiduMapUtilsManager();
    }

    public  void initBaiduMap(BaiduMap.OnMapLoadedCallback onMapLoadedCallback ){
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
        mBaiduMap.setOnMapLoadedCallback(onMapLoadedCallback);
    }


    public List<Overlay> addOverlay(ArrayList<LatLng> points, final OnMyMarkerClick onMyMarkerClick) {
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(R.drawable.pic_express_car);
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
        List<Overlay> overlays=mBaiduMap.addOverlays(allOpitons);//添加多个覆盖物
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMyMarkerClick.onMyMarkerClick(marker.getExtraInfo().getInt("index"));
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
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds);
            mBaiduMap.animateMapStatus(u);
        }
        return overlays;
    }

    public List<Overlay> addOverlayWhitoutClick(ArrayList<LatLng> points,int dataType) {
        int pic_src=R.drawable.map_car;
        switch (dataType){
            case MAP_TYPE_CAR:
                pic_src=R.drawable.map_car;
                break;
            case MAP_TYPE_MIXINGSTATION:
                pic_src=R.drawable.map_mixingstation;
                break;
            case MAP_TYPE_PROJECT:
                pic_src=R.drawable.map_project;
                break;
        }
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(pic_src);
        List<OverlayOptions> allOpitons=new ArrayList<OverlayOptions>();

        for(int i=0;i<points.size(); i++) {
            MarkerOptions ooA = new MarkerOptions().position(points.get(i)).icon(bdA);
            Bundle bundle=new Bundle();
            bundle.putInt("index", i);
            bundle.putInt("dataType", dataType);
            ooA.extraInfo(bundle);
            //掉下动画
//            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//            //生长动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
            allOpitons.add(ooA);
        }
        List<Overlay> overlays=mBaiduMap.addOverlays(allOpitons);//添加多个覆盖物
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
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds);
            mBaiduMap.animateMapStatus(u);
        }
        return overlays;
    }

    /**
     * 地图页面存在多个不同类型图标时监听
     * @param onMultipleMarkerClick
     */
    public void setMultipleMarkerClicker(final OnMultipleMarkerClick onMultipleMarkerClick){
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMultipleMarkerClick.onMyMarkerClick(marker.getExtraInfo().getInt("dataType"),marker.getExtraInfo().getInt("index"));
                return false;
            }
        });
    }

    public Overlay addSingleOverlay(int dataType,LatLng latLng) {
        int pic_src=R.drawable.map_car;
        switch (dataType){
            case MAP_TYPE_CAR:
                pic_src=R.drawable.map_car;
                break;
            case MAP_TYPE_MIXINGSTATION:
                pic_src=R.drawable.map_mixingstation;
                break;
            case MAP_TYPE_PROJECT:
                pic_src=R.drawable.map_project;
                break;
        }
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(pic_src);
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA);
        //掉下动画
//            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//            //生长动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
        Overlay overlay = mBaiduMap.addOverlay(ooA);//添加多个覆盖物
        moveTo(latLng);
        return overlay;
    }


    public List<Overlay> addOverlayRandom(final OnMyMarkerClick onMyMarkerClick) {
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(R.drawable.pic_express_car);

        ArrayList<LatLng> points=new ArrayList<LatLng>();
        points.add(new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude()));
        points.add(new LatLng(CacheUtils.getLatitudes()+0.02, CacheUtils.getLongitude()));
        points.add(new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude()+0.02));
        points.add(new LatLng(CacheUtils.getLatitudes(), CacheUtils.getLongitude()+0.05));
        points.add(new LatLng(CacheUtils.getLatitudes()+0.05, CacheUtils.getLongitude()));
        points.add(new LatLng(CacheUtils.getLatitudes()-0.05, CacheUtils.getLongitude()+0.02));


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
        List<Overlay> overlays=mBaiduMap.addOverlays(allOpitons);//添加多个覆盖物
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMyMarkerClick.onMyMarkerClick(marker.getExtraInfo().getInt("index"));
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
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds);
            mBaiduMap.animateMapStatus(u);
        }

        return overlays;
    }

    /**
     * marker点击回调
     */
    public interface  OnMyMarkerClick{
         void onMyMarkerClick(int index);
    }
    /**
     * marker点击回调
     */
    public interface  OnMultipleMarkerClick{
        void onMyMarkerClick(int dataType,int index);
    }

    /**
     * 将地图移到指定位置
     * @param latitudes
     * @param longitude
     */
    public void moveTo(double latitudes,double longitude){
        LatLng ll = new LatLng(latitudes, longitude);
        moveTo(ll);
    }

    /**
     * 将地图移到指定位置
     LatLng latLng
     */
    public void moveTo(LatLng latLng){
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng);
        builder.zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 将地图移到指定位置
     LatLng latLng
     */
    public void clear(){
        mBaiduMap.clear();
    }


    /**
     * // 添加普通折线绘制
     * @param points
     * @param isDottedLine  是否虚线
     */
    public void addLine(List<LatLng> points,boolean isDottedLine){
        if(points==null||points.size()<2){
            return;
        }
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
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds, DensityUtil.getScreenWidth(),  DensityUtil.getScreenHeight());
        mBaiduMap.animateMapStatus(u);

    }
}
