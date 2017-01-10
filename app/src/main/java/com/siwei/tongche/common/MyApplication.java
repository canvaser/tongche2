package com.siwei.tongche.common;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.LocationManager;
import com.siwei.tongche.events.EventTag;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.sunday.busevent.SDBaseEvent;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/*
 *全局 MyApplication
 */
public class MyApplication extends Application {
	private static MyApplication mApplication = null;
	@Override
	public void onCreate() {
		super.onCreate();
		mApplication=this;
		//初始化百度地图
		SDKInitializer.initialize(this);

		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush

//		CrashHandler crashHandler = CrashHandler.getInstance();
//		//注册crashHandler
//		crashHandler.init(getApplicationContext());

		AppConstants.DIMEN_1 = (int) getResources().getDimension(R.dimen.dimen_1);
	}

	/**
	 * 返回全局变量
	 * @return
	 */
	public static MyApplication getMyApplication(){
		return mApplication;
	}
	
	public void exitApp()
	{
		releaseResource();
		EventBus.getDefault().post(new SDBaseEvent(null, EventTag.EVENT_EXIT_APP));
	}

	/**
	 * 释放资源
	 */
	private void releaseResource() {
		LocationManager.newInstance().stopLocation();
	}


	@Override
	public void onLowMemory() {
		exitApp();
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onLowMemory();
	}


}
