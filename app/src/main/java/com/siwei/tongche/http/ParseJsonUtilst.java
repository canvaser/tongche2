package com.siwei.tongche.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.utils.MyToastUtils;

import java.util.List;

/**
 * json解析工具类
 * @author hjl
 *
 * 2015年12月31日下午2:37:57
 */
public class ParseJsonUtilst {

	/**
	 * json解析单个对象
	 * @param context 当前上下文
	 * @param json 待解析的json字符串
	 * @param cls T的class
	 * @return <T>
	 */
	public static <T> T getDetail(Context context,String json,Class<T> cls){
 		try{
			JSONObject jsonStr = JSON.parseObject(json);
			String  status = jsonStr.getString("status");
			String messages = jsonStr.getString("messages");
			if(status.equals("1000")){
				JSONObject jsonObject = jsonStr.getJSONObject("resultData");
				return JSON.parseObject(jsonObject.toJSONString(),cls);
			}else if(status.equals("0005")){//异地登陆
				changetoLogin(context);
			}else{
				MyToastUtils.showToast(messages);
			}
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * json解析list数组数据
	 * @param context 当前上下文
	 * @param json 待解析的json字符串
	 * @param cls T的class
	 * @return List<T>
	 */
	public static <T> List<T> getDetailArray(Context context,String json, Class<T> cls){
		try {
			JSONObject object = JSON.parseObject(json);
			String  status = object.getString("status");
			String messages = object.getString("messages");
			if(status.equals("1000")){
				return JSON.parseArray(object.getJSONArray("resultData").toJSONString(),cls);
			}else if(status.equals("0005")){//异地登陆
				changetoLogin(context);
			}else{
				MyToastUtils.showToast(messages);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * json解析，单纯解析请求状态
	 * @param context 当前上下文
	 * @param json 待解析的json字符串
	 * @return true：操作成功 false：操作失败
	 */
	public static boolean getDetailStatus(Context context,String json){
		try {
			JSONObject object = JSON.parseObject(json);
			String  status = object.getString("status");
			String messages = object.getString("messages");
			if(status.equals("1000")){
				return true;
			}else if(status.equals("0005")){//异地登陆
				changetoLogin(context);
			}else{
				MyToastUtils.showToast(messages);
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private static void changetoLogin(Context context) {
//		/**
//		 * 跳转到登陆页面
//		 */
//			MyApplication.getMyApplication().exitApp();
//		 	MyDatabaseUtils.clearUserInfoModel();
//			MyToastUtils.showToast("账号在别的手机上登录,请重新登录！");
//			Intent intent=new Intent(context,LoginActivity.class);
//			context.startActivity(intent);
	}
}
