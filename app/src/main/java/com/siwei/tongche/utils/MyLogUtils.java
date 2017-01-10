package com.siwei.tongche.utils;
import android.text.TextUtils;
import android.util.Log;

public class MyLogUtils
{
	public static boolean Debug = true;
	 private  static final String TAG="SiWeiSoftware";
	 /**
	  * 打印 log
	  * @param content
	  */
	 public static void e(String content){
		 if(Debug&&!TextUtils.isEmpty(content)){
			 Log.e(TAG, content);
		 }
	 }
	 
	 /**
	  * 打印 log
	  * @param content
	  */
	 public static void e(String tag,String content){
		 if(Debug&&!TextUtils.isEmpty(content)){
			 Log.e(tag, content);
		 }
	 }

}
