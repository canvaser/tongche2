package com.siwei.tongche.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断 格式工具类
 * @author hanjinliang
 *
 * 2015年12月12日 下午5:53:50
 */
public class MyRegexpUtils {
	/**
	 * 判断是否是手机号码
	 * @param str   输入的手机号
	 * @return   true是  false不是
	 */
	public static boolean isPhone(String str){
		if(str==null||str.equals("")){
			return false;
		}
		Pattern p = Pattern.compile("1\\d{10}");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;

	}
	/**
	 * 判断邮箱是否合法
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){  
		if (null==email || "".equals(email)) return false;	
		//Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
		Matcher m = p.matcher(email);  
		return m.matches();  
	}
	/**
	 * 判断邮政编码
	 * @param zipString
	 * @return
	 */
	public static boolean isZipNO(String zipString){
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}

	/**
	 * 只能有数字 和字母组成的密码
	 * @param password
	 * @return
	 */
	public static boolean isPassword(String password){
		String str = "[0-9A-Za-z]{6,16}";
		return Pattern.compile(str).matcher(password).matches();
	}
	
	/**
	 * 只能有数字 和字母组成的密码
	 * @return
	 */
	public static boolean isEmpty(String content){
		if(content==null||content.length()==0){
			return true;
		}
		return false;
	}

	/**
	 * 是否是正确身份证号码
	 * @param card_no
	 * @return
     */
	public static boolean isIDNum(String card_no) {
		if(card_no==null||card_no.equals("")){
			return false;
		}
		card_no=card_no.toUpperCase();
		Pattern p = Pattern.compile("^\\d{17}([0-9]|X)$");
		Matcher m = p.matcher(card_no);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 车牌号正则
	 * @param carNO
	 * @return
     */
	public static boolean isCarNo(String carNO) {
		String re="^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";
		if(carNO==null||carNO.equals("")){
			return false;
		}
		carNO=carNO.toUpperCase();
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(carNO);
		boolean b = m.matches();
		return b;
	}
}
