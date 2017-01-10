package com.siwei.tongche.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;


import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.MyToastUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 自动更新
 * @author hjl
 *
 * 2016年3月29日下午2:44:01
 */
public class AutoUpdateManager {
	
	public final String verXmlUrl= MyUrls.APP_DOMAIN+"verupdate/update.xml";
	public Context mContext;
	private final static  String TAG="AutoUpdateManager";
	
	public AutoUpdateManager(Context context){
		mContext=context;
	}
	
	/**
	 * 检测最新版本
	 */
	public static  void checkAutoUpdate(final Context context,final boolean isShowCurrentVer) {
		final AutoUpdateManager updateManager = new AutoUpdateManager(context);
		final String currentVer = updateManager.getCurrentVer();
		updateManager.checkVer(new CheckVerCallback() {

			@Override
			public void verInfo(final UpdataInfo updataInfo) {

				if (!updataInfo.getVersion().equals(currentVer)) {
					final String url = updataInfo.getUrl();
					((Activity) context).runOnUiThread(new Runnable() {
						public void run() {
							 Builder buttonBuilder = new Builder(context)
							.setMessage("检测到新版本，是否立即更新？")
							.setPositiveButton("确定",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//exitApp(context);//清除缓存
									updateManager.downLoadApk(url);
								}
							});
							if(updataInfo.getIsForce().equals("1")){//强制更新
								AlertDialog dialog=buttonBuilder.create();
								dialog.setCanceledOnTouchOutside(false);
								dialog.show();
							}else{
								buttonBuilder.setNegativeButton("取消", null);
								buttonBuilder.create().show();
							}
						}
					});
				} else {
					if(isShowCurrentVer){
						MyToastUtils.showToast("当前为最新版本" + currentVer);
					}
					Log.e(TAG, "已经是最新版本");
				}
			}
		});
	}
	 
	/**
	 * 得到当前版本号
	 * @return
	 */
	public String getCurrentVer(){
		String versionName="";
		try {
			versionName=mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Log.e(TAG, "=====old ver===="+versionName);
		return versionName;
	}
	
	/**
	 * 得到当前版本代码
	 * @return
	 */
	public int getCurrentVerCode(){
		int versionCode=0;
		try {
			versionCode=mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Log.e(TAG, "=====versionCode===="+versionCode);
		return versionCode;
	}
 
	/* 
	 * 从服务器中下载APK 并且自动更新
	 */  
	public void downLoadApk(final String apkFileUrl) {  
	    final ProgressDialog pd;    //进度条对话框  
	    pd = new  ProgressDialog(mContext);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	            	File file = DownLoadManager.getFileFromServer(apkFileUrl, pd); 
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	                 
	            }  
	        }}.start();  
	}  
	
		//安装apk   
		protected void installApk(File file) { 
		    Intent intent = new Intent();  
		    //执行动作  
		    intent.setAction(Intent.ACTION_VIEW);  
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置启动模式   否则安装完不会自动进入应用
		    //执行的数据类型  
		    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
		    mContext.startActivity(intent);  
		}  
		
		 public  InputStream getContent( String urlpath,String encoding) throws Exception {
			    URL url = new URL(urlpath);
			    //实例化一个HTTP连接对象conn
			    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			    //定义请求方式为GET，其中GET的格式需要注意
			    conn.setRequestMethod("GET");
			    //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
			    conn.setConnectTimeout(10*1000);
			    if(conn.getResponseCode()== 200){
			    	return conn.getInputStream();
			    } 
			    return null;
			   } 
		 /**
		  * 检测版本号
		  * @param callback
		  */
		 public void checkVer(final CheckVerCallback callback){
			 new Thread(){
				 public void run() {
					 URL url;
					try {
						url = new URL(verXmlUrl);
						 //实例化一个HTTP连接对象conn
					    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					    //定义请求方式为GET，其中GET的格式需要注意
					    conn.setRequestMethod("GET");
					    //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
					    conn.setConnectTimeout(5*1000);
					    if(conn.getResponseCode()== 200){
					    	callback.verInfo(getUpdataInfo(conn.getInputStream()));
					    } else{
					    	Log.e(TAG, "网络状态异常code=="+conn.getResponseCode());
					    	 return ;
					    }
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					   
					   
				 };
			 }.start();
		 }

		/**
		 * 解析服务器端xml
		 * @param is
		 * @return
		 * @throws Exception
		 */
		public   UpdataInfo getUpdataInfo(InputStream is) throws Exception{
			XmlPullParser  parser = Xml.newPullParser();  
			parser.setInput(is, "utf-8");
			int type = parser.getEventType();
			UpdataInfo info = new UpdataInfo();
			while(type != XmlPullParser.END_DOCUMENT ){
				switch (type) {
				case XmlPullParser.START_TAG:
					if("version".equals(parser.getName())){
						info.setVersion(parser.nextText());	
					}else if ("url".equals(parser.getName())){
						info.setUrl(parser.nextText());	
					}else if ("isForce".equals(parser.getName())){
						info.setIsForce(parser.nextText());	
					}  
					break;
				}
				type = parser.next();
			}
			Log.e(TAG, "====new ver=="+info.toString());
			return info;
		}
		
		/**
		 * 检测网络版本号  回调
		 * @author Administrator
		 *
		 */
	 public interface CheckVerCallback{
		 public void verInfo(UpdataInfo updataInfo);
	 }
}
