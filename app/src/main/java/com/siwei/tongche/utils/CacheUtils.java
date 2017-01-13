package com.siwei.tongche.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.siwei.tongche.common.MyApplication;
import com.siwei.tongche.module.login.bean.UserInfo;


public class CacheUtils {
	
	public static void savePrefString(Context context, String key, String vaule) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = settings.edit();
		edit.putString(key, vaule);
		edit.commit();
	}
	public static void savePrefBoolean(Context context, String key, boolean def) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = settings.edit();
		edit.putBoolean(key, def);
		edit.commit();
	}
	public static void savePrefInt(Context context, String key, int value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = settings.edit();
		edit.putInt(key, value);
		edit.commit();
	}
	
	public static String getSavePrefString(Context context,String key){
		SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, "");
	}
	
	
	public static String getSavePrefString(Context context,String key,String def){
		SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, def);
	}
	
	/**
	 * 获取 boolean数据
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getSavePrefBoolean(Context context,String key){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getBoolean(key, false);
	}
	
	/**
	 * 获取 Int数据
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getSavePrefInt(Context context,String key){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getInt(key, 1);
	}
	
	/**
	 * 清除缓存   除了第一次登录 记录
	 * @param context
	 */
	public static void clearCache(Context context){
		boolean isFirst = getIsFirst(context);
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = sp.edit();
		edit.clear();
		edit.commit();
		savePrefBoolean(context, "isFirst", isFirst);
	}
	/**
	 * 清除制定缓存
	 * @param key
	 */
	public static void clearCacheByKey(Context context,String key) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = settings.edit();
		edit.remove(key);
		edit.commit();
	}
	 
	/**
	 * 获取是否第一次登录
	 * @param context
	 * @return
	 */
	public static boolean getIsFirst(Context context ){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getBoolean("isFirst", true);
	}

	/**
	 *保存第一次登录
	 * @param context
	 * @return
	 */
	public static void saveIsFirst(Context context,boolean isFirst ){
		 savePrefBoolean(context, "isFirst", isFirst);
	}

	/**
	 * 经度
	 * @return
	 */
	public static double getLongitude(){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getMyApplication());
		return MyFormatUtils.toDouble(sp.getString("longitude", ""));
	}
	public static void setLongitude(String longitude){
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getMyApplication());
		Editor edit = settings.edit();
		edit.putString("longitude",longitude);
		edit.commit();
	}

	/**
	 * 维度
	 * @return
	 */
	public static double getLatitudes(){
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getMyApplication());
		return MyFormatUtils.toDouble(sp.getString("latitudes","")) ;
	}
	public static void setLatitudes(String  latitudes){
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getMyApplication());
		Editor edit = settings.edit();
		edit.putString("latitudes", latitudes);
		edit.commit();
	}

	/**
	 * 地址
	 * @return
	 */
	public static String getAddress(){
		return getSavePrefString(MyApplication.getMyApplication(),"address");
	}
	public static void setAddress(String  address){
		savePrefString(MyApplication.getMyApplication(),"address",address);
	}

	/**
	 * 保存本地用户信息
	 * @param userInfo
	 */
	public static void  setLocalUserInfo(UserInfo userInfo){
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getMyApplication());
		Editor edit = settings.edit();
		edit.putString("UID", userInfo.getUID());
		edit.putString("UName", userInfo.getUName());
		edit.putString("UPhone", userInfo.getUPhone());
		edit.putString("UUnitId", userInfo.getUUnitId());
		edit.putString("UAuditStatus", userInfo.getUAuditStatus());
		edit.putString("UAuditorId", userInfo.getUAuditorId());
		edit.putString("UBindUnitTime", userInfo.getUBindUnitTime());
		edit.putString("UDriveImg", userInfo.getUDriveImg());
		edit.putString("UDriveNo", userInfo.getUDriveNo());
		edit.putString("UHeadImg", userInfo.getUHeadImg());
		edit.putString("UBindVehicleStatus",userInfo.getUBindVehicleStatus());
		edit.putString("UBindSendUnitStatus",userInfo.getUBindSendUnitStatus());
		edit.putString("UBindSendUnitAuditor",userInfo.getUBindSendUnitAuditor());
		edit.putString("UBindSendUnitTime",userInfo.getUBindSendUnitTime());
		edit.putString("UWorkStatus",userInfo.getUWorkStatus());
		edit.putString("UUserType",userInfo.getUUserType());
		edit.putString("URoleCode",userInfo.getURoleCode());
		edit.putString("UUnitRole",userInfo.getUUnitRole());
		edit.putString("UStatus",userInfo.getUStatus());
		edit.commit();
	}

	/**
	 * 返回本地用户信息
	 * @return
	 */
	public static UserInfo  getLocalUserInfo(){
		UserInfo userInfo=new UserInfo();
		userInfo.setUID(getSavePrefString(MyApplication.getMyApplication(), "UID"));
		userInfo.setUName(getSavePrefString(MyApplication.getMyApplication(), "UName"));
		userInfo.setUPhone(getSavePrefString(MyApplication.getMyApplication(), "UPhone"));
		userInfo.setUUnitId(getSavePrefString(MyApplication.getMyApplication(), "UUnitId"));
		userInfo.setUAuditStatus(getSavePrefString(MyApplication.getMyApplication(), "UAuditStatus"));
		userInfo.setUAuditorId(getSavePrefString(MyApplication.getMyApplication(), "UAuditorId"));
		userInfo.setUBindUnitTime(getSavePrefString(MyApplication.getMyApplication(), "UBindUnitTime"));
		userInfo.setUDriveImg(getSavePrefString(MyApplication.getMyApplication(), "UDriveImg"));
		userInfo.setUDriveNo(getSavePrefString(MyApplication.getMyApplication(), "UDriveNo"));
		userInfo.setUHeadImg(getSavePrefString(MyApplication.getMyApplication(), "UHeadImg"));
		userInfo.setUBindVehicleStatus(getSavePrefString(MyApplication.getMyApplication(), "UBindVehicleStatus"));
		userInfo.setUBindSendUnitStatus(getSavePrefString(MyApplication.getMyApplication(),"UBindSendUnitStatus"));
		userInfo.setUBindSendUnitAuditor(getSavePrefString(MyApplication.getMyApplication(), "UBindSendUnitAuditor"));
		userInfo.setUBindSendUnitTime(getSavePrefString(MyApplication.getMyApplication(), "UBindSendUnitTime"));
		userInfo.setUWorkStatus(getSavePrefString(MyApplication.getMyApplication(), "UWorkStatus"));
		userInfo.setUUserType(getSavePrefString(MyApplication.getMyApplication(), "UUserType"));
		userInfo.setURoleCode(getSavePrefString(MyApplication.getMyApplication(), "URoleCode"));
		userInfo.setUUnitRole(getSavePrefString(MyApplication.getMyApplication(), "UUnitRole"));
		userInfo.setUStatus(getSavePrefString(MyApplication.getMyApplication(), "UStatus"));
		return userInfo;
	}

//	/**
//	 * 保存本地用户信息
//	 * @param systemConfig
//	 */
//	public static void  setSystemConfig(SystemConfig systemConfig){
//		SharedPreferences settings = PreferenceManager
//				.getDefaultSharedPreferences(MyApplication.getMyApplication());
//		Editor edit = settings.edit();
//		edit.putString("CANCEL_TIME", systemConfig.getCANCEL_TIME());
//		edit.putString("CHAT_TIME", systemConfig.getCHAT_TIME());
//		edit.putString("POSITION_TIME", systemConfig.getPOSITION_TIME());
//		edit.putString("SIGN_NUM_TEP", systemConfig.getSIGN_NUM_TEP());
//		edit.commit();
//	}

//	/**
//	 * 返回本地用户信息
//	 * @return
//	 */
//	public static SystemConfig getSystemConfig(){
//		SystemConfig systemConfig=new SystemConfig();
//		systemConfig.setCANCEL_TIME(getSavePrefString(MyApplication.getMyApplication(), "CANCEL_TIME","30"));//默认值
//		systemConfig.setCHAT_TIME(getSavePrefString(MyApplication.getMyApplication(), "CHAT_TIME","10"));//默认值
//		systemConfig.setPOSITION_TIME(getSavePrefString(MyApplication.getMyApplication(), "POSITION_TIME","1"));//默认值
//		systemConfig.setSIGN_NUM_TEP(getSavePrefString(MyApplication.getMyApplication(), "SIGN_NUM_TEP","0.5"));//默认值
//		return systemConfig;
//	}

}
