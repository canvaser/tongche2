package com.siwei.tongche.utils;



import android.content.Context;
import android.view.WindowManager;

import com.siwei.tongche.common.MyApplication;


public class DensityUtil{

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px( float dpValue) {  
        final float scale = MyApplication.getMyApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip( float pxValue) {  
        final float scale = MyApplication.getMyApplication().getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
    
    /** 获取屏幕的宽度 */
	public static int getScreenWidth() {
		WindowManager manager = (WindowManager)MyApplication.getMyApplication().getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getWidth();
	}

	/***
	 * 获取屏幕的高度
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		WindowManager manager = (WindowManager) MyApplication.getMyApplication().getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getHeight();
	}
}
