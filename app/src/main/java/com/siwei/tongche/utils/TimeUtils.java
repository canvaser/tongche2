package com.siwei.tongche.utils;

/**
 * Created by HanJinLiang on 2017-01-12.
 */

public class TimeUtils {
    /**
     * 计算时间间隔
     * @param startTime
     * @return
     */
    public static String getTimeWaitting(String startTime){
        long startTimeSec= MyFormatUtils.toLong(startTime);
        long currentTimeSec=System.currentTimeMillis()/1000;
        long waittingSec=currentTimeSec-startTimeSec;
        if(waittingSec<60){//1分钟以下
            return waittingSec+"";
        }else if(waittingSec<60*60&&waittingSec>=60){//一分钟以上 一小时以下
            return waittingSec/60+":"+waittingSec%60;
        }else if(waittingSec>=3600){
            return waittingSec/3600+":"+(waittingSec%3600)/60+":"+(waittingSec%3600)%60 ;
        }
        return null;
    }
}
