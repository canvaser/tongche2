package com.siwei.tongche.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *格式转换
 * @author hanjinliang
 *
 * 2015年12月12日 下午5:55:16
 */
public class MyFormatUtils {
	public static long toLong(String obj) {
		if(obj==null||obj.equals("")){
			return 0;
		}
		long l =Long.parseLong(obj);
		return l;
	}



	/**
	 * double保留两位有效数字
	 * @param value
	 * @return
	 */
	public static String doubleSave2(double value){
		DecimalFormat   df   =new  DecimalFormat("#0.00"); 
		return df.format(value);
	}

	/**
	 * double保留1位有效数字
	 * @param value
	 * @return
	 */
	public static String doubleSave1(double value){
		DecimalFormat   df   =new  DecimalFormat("#0.0");
		return df.format(value);
	}
	

	/**
	 * double保留三位有效数字
	 * @param value
	 * @return
	 */
	public static String doubleSave3(double value){
		DecimalFormat   df   =new  DecimalFormat("#0.000"); 
		return df.format(value);
	}
	
	private static final String ZERO = "0";

	// sqlite 中获得的空字符串 有为null 并为 "null"(字符串非空) 的情况 防止
	public static String nvl(Object obj) {	
		return obj == null ? "" : (("null").equals(obj.toString()) == true ? ""
				: obj.toString());
	}

	/**
	 * 判断一个字符串是否为空 或者为null   是则返回默认值   否则返回本身
	 * @param obj
	 * @param def
	 * @return
	 */
	public static String nvl(Object obj, String def) {
		return obj == null ||obj.equals("null")||obj.equals("") ? def : obj.toString();
	}



	/**
	 * 判断object是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNvl(Object obj) {
		if (null == obj || obj.equals("")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	/**
	 * 将obj类型转化为int
	 * 
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj) {
		if (obj != null && !obj.equals("null")) {
			return nvl(obj).equals("") ? 0 : (new BigDecimal(obj.toString()))
					.intValue();
		}
		return 0;
	}


	public static String toIntString(Object obj) {
		if (obj != null && !obj.equals("null")) {
			return nvl(obj).equals("") ? ZERO : String.valueOf((new BigDecimal(
					obj.toString())).intValue());
		}
		return ZERO;
	}

	/**
	 * 将Object类型转化为double
	 * 
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj) {
		try {
			if (obj != null && !obj.equals("")) {
				return nvl(obj).equals("") ? 0.0 : Double.parseDouble(obj
						.toString());
			}
		}catch (NumberFormatException e){
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * 将Object类型转化为double
	 *
	 * @param obj
	 * @return
	 */
	public static float toFloat(Object obj) {
		if (obj != null && !obj.equals("")) {
			return nvl(obj).equals("") ? 0.0f : Float.parseFloat(obj
					.toString());
		}
		return 0.0f;
	}

	public static int per2Int(Object obj) {
		return obj == null ? 0 : Integer.parseInt(obj.toString().substring(0,
				obj.toString().length() - 1));
	}
	/**
	 * 小数  转换为百分数
	 * @param decimals
	 * @return
	 */
	public static String decimals2Percent(String decimals){
		return doubleSave2(toDouble(decimals)*100)+"%";
	}
	
	/**
	 * 小数  转换为百分数
	 * @param decimals
	 * @return
	 */
	public static String decimals3Percent(String decimals){
		return doubleSave2(toDouble(decimals)*100)+"";
	}
	
	/**
	 * 将时间戳转换为"yyyy-MM-dd hh:mm"时间格式
	 * @param time
	 * @return
	 */
	public static String getLongTimeToTime(String time) {
		if(time==null){
			return "";
		}
		long timeLong = Long.parseLong(time);
		Date d = new Date(timeLong*1000l);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sf.format(d);
	}

	/**
	 * 将时间戳转换为"MM-dd hh:mm"时间格式
	 * @param time
	 * @return
	 */
	public static String getTimeWithoutYear(String time) {
		if(time==null||time.equals("")||time.equals("null")){
			return "";
		}
		long timeLong = Long.parseLong(time);
		Date d = new Date(timeLong*1000l);
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd HH:mm");
		return sf.format(d);
	}

	/**
	 * 将时间戳转换为"MM-dd hh:mm"时间格式
	 * @param time
	 * @return
	 */
	public static String getTimeToDayTime(String time) {
		if(time==null||time.equals("")||time.equals("null")){
			return "";
		}
		long timeLong = Long.parseLong(time);
		Date d = new Date(timeLong*1000l);
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		return sf.format(d);
	}

	/**
	 * 将时间戳转换为"yyyy-MM-dd hh:mm:ss"时间格式
	 * @param time
	 * @return
	 */
	public static String getDetailTimeByLongTime(String time) {
		if(time==null||time.equals("")||time.equals("null")){
			return "";
		}
		long timeLong = Long.parseLong(time);
		Date d = new Date(timeLong*1000l);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(d);
	}
	
	/**
	 * 将时间戳转换为"yyyy-MM-dd "时间格式
	 * @param time
	 * @return
	 */

	public static String getLongTimeToDate(String time) {
		if(time==null||time.equals("")||time.equals("null")){
			return "";
		}
		long timeLong = Long.parseLong(time);
		Date d = new Date(timeLong*1000l);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}
	
	/**
	 * 得到 "yyyyMMdd"时间格式
	 * @return
	 */
	public static String getCurrentDate() {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(d);
	}
	/*
	 *得到    15：59
	 */
	public static String getlongTime2time(long mills) {
		Date d = new Date(mills);
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		return sf.format(d);
	}
	
	/**
	 *  将字符串转为时间戳   单位为毫秒
	 * @param user_time
	 * @return
	 */
	 public static long getTime(String user_time) {
	  long time = 0;
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  Date d;
	  try {
	   d = sdf.parse(user_time);
	   time = d.getTime();
	  } catch (ParseException e) {
	   e.printStackTrace();
	  }
	  return time;
	 }


	/*
	 *得到    15：59
	 */
	public static String getDetailTime2HHmm(String detail) {
		if(MyRegexpUtils.isEmpty(detail)){
			return "";
		}
		return detail.substring(11,16);
	}
}
