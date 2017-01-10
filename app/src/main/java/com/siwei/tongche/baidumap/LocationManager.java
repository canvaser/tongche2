package com.siwei.tongche.baidumap;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.siwei.tongche.common.MyApplication;


/**
 * 百度地图获取位置
 * Created by HanJinLiang on 2016-05-04.
 */
public class LocationManager {
    public static int LocationOnce=0x11;//一次定位
    public static int LocationMultiple=0x12;//多次定位


    LocationClient mLocationClient;
    private static LocationManager mLocationManager;
    private  LocationManager(){
    }

    public  static LocationManager newInstance(){
        if(mLocationManager==null){
            mLocationManager=new LocationManager();
        }
        return mLocationManager;
    }

    /**
     *
     * @param type   定位类型  是多次还是一次
     * @param myListener
     */
    public void getLocation(int type,BDLocationListener myListener){
        mLocationClient = new LocationClient(MyApplication.getMyApplication());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        if(type==LocationOnce){
            initLocation(0);
        }else if(type==LocationMultiple){
            initLocation(60*1000);//隔一分钟重新定位一次
        }
        mLocationClient.start();
    }

    /**
     * 结束定位
     */
    public void stopLocation(){
        if(mLocationClient!=null&&mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    private void initLocation(int span){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps

        //不设置这个的话  当GPS定位时 会每隔1S刷新数据
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

}
