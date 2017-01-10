package com.siwei.tongche.http;


import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.NetWorkStatusUtils;

import org.json.JSONArray;

import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * 网络请求工具类，基于android-async-http开源框架
 * @author lgy
 * @date 2015年11月24日 上午11:35:04
 */
public class MyHttpUtil {
	private final static AsyncHttpClient httpClient = new AsyncHttpClient(); // 实例话对象
	static {
		httpClient.setMaxRetriesAndTimeout(1, 1000 * 10);// 设置链接超时，如果不设置，默认为10s
		httpClient.setConnectTimeout(10 * 1000);
		httpClient.setResponseTimeout(10 * 1000);
		//网络超时
		httpClient.allowRetryExceptionClass(SocketTimeoutException.class);
	}
	
	/**
	 * 发送http请求，基于android-async-http开源框架
	 * @param context 当前上下文
	 * @param uri 请求路径
	 * @param params 请求参数
	 * @param type 返回值类型  ReturnType.OBJECT:返回单个对象，ReturnType.ARRAY:返回数组对象，ReturnType.ARRAY:返回boolean对象
	 * @param cls 待返回实体类Class字节码
	 * @param result 结果回调
	 */
	public static <T> void sendRequest(final Context context, final String uri, JSONObject params, final ReturnType type, final Class<T> cls, final HttpResult result) {
		sendRequest(context, uri, params, type, cls, "加载中...", result,true);
	}

	/**
	 * 发送http请求，基于android-async-http开源框架
	 * @param context 当前上下文
	 * @param uri 请求路径
	 * @param params 请求参数
	 * @param type 返回值类型  ReturnType.OBJECT:返回单个对象，ReturnType.ARRAY:返回数组对象，ReturnType.ARRAY:返回boolean对象
	 * @param cls 待返回实体类Class字节码
	 * @param result 结果回调
	 * @param  isNeedToast  是否需要给出Toast提示信息   默认是true
	 */
	public static <T> void sendRequest(final Context context, final String uri, JSONObject params, final ReturnType type, final Class<T> cls, String loadingStr, final HttpResult result, final boolean isNeedToast) {
		if(!NetWorkStatusUtils.isNetworkConnected()){
			if(isNeedToast){
				MyToastUtils.showToast("网络未连接");
			}
			switch (type) {
				case BOOLEAN:	//boolean值，标示请求是否成功
					result.onResult(false);
					break;
				default:
					result.onResult(null);
					break;
			}
			return;
		}
		//可能为空
		BaseActivity baseActivity=((BaseActivity)context);
		if(baseActivity!=null){
			baseActivity.showLoadingDialog(loadingStr);
		}

		StringEntity se = null;
		try {
			if(params!=null){
				MyLogUtils.e(uri+"?"+params.toString());
				se = new StringEntity(params.toString(),"utf-8");
			}
		} catch (Exception e) {
			return;
		}
		httpClient.addHeader("Content-Type", "application/json; charset=UTF-8");
		httpClient.setURLEncodingEnabled(true);
		httpClient.post(context, uri, se, "", new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
				MyLogUtils.e("statusCode=="+statusCode);
				handleFailed(throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				MyLogUtils.e("statusCode=="+statusCode);
				handleFailed(throwable);
			}

			private void handleFailed(Throwable throwable) {
				((BaseActivity) context).hideLoadingDialog();
				switch (type) {
					case BOOLEAN:	//boolean值，标示请求是否成功
						result.onResult(false);
						break;
					default:
						result.onResult(null);
						break;
				}
				MyLogUtils.e(throwable.getMessage());
				if(isNeedToast) {
					MyToastUtils.showToast("网络异常");
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				handleFailed(throwable);
				MyLogUtils.e("statusCode=="+statusCode);
			}


			@Override
			public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
				MyLogUtils.e(uri, response.toString());
				((BaseActivity) context).hideLoadingDialog();
				switch (type) {
					case OBJECT://返回单个对象数据
						result.onResult(ParseJsonUtilst.getDetail(context, response.toString(), cls));
						break;
					case ARRAY://返回数组对象数据
						result.onResult(ParseJsonUtilst.getDetailArray(context, response.toString(), cls));
						break;
					case BOOLEAN://boolean值，标示请求是否成功
						result.onResult(ParseJsonUtilst.getDetailStatus(context, response.toString()));
						break;
					case JSONSTRING:
						result.onResult(response.toString());
						break;
				}

			}
		});
	}
	/**
	 * 发送http请求，基于android-async-http开源框架
	 * @param context 当前上下文
	 * @param uri 请求路径
	 * @param params 请求参数
	 * @param type 返回值类型  ReturnType.OBJECT:返回单个对象，ReturnType.ARRAY:返回数组对象，ReturnType.ARRAY:返回boolean对象
	 * @param cls 待返回实体类Class字节码
	 * @param result 结果回调
	 */
	public static <T> void sendGetRequest(final Context context, final String uri, RequestParams params, final ReturnType type, final Class<T> cls, final HttpResult result) {
		sendGetRequest(context, uri, params, type, cls, "加载中...", result,true);
	}
	public static <T> void sendGetRequest(final Context context, final String uri, RequestParams params, final ReturnType type, final Class<T> cls, String loadingStr,final HttpResult result) {
		sendGetRequest(context, uri, params, type, cls, loadingStr, result,true);
	}
	/**
	 * 发送http请求，基于android-async-http开源框架
	 * @param context 当前上下文
	 * @param uri 请求路径
	 * @param params 请求参数
	 * @param type 返回值类型  ReturnType.OBJECT:返回单个对象，ReturnType.ARRAY:返回数组对象，ReturnType.ARRAY:返回boolean对象
	 * @param cls 待返回实体类Class字节码
	 * @param result 结果回调
	 * @param  isNeedToast  是否需要给出Toast提示信息   默认是true
	 */
	public static <T> void sendGetRequest(final Context context, final String uri, RequestParams params, final ReturnType type, final Class<T> cls, String loadingStr, final HttpResult result, final boolean isNeedToast) {
		if(!NetWorkStatusUtils.isNetworkConnected()){
			if(isNeedToast){
				MyToastUtils.showToast("网络未连接");
			}
			switch (type) {
				case BOOLEAN:	//boolean值，标示请求是否成功
					result.onResult(false);
					break;
				default  :    //返回单个对象数据
					result.onResult(null);
					break;
			}
			return;
		}
		//可能为空
		BaseActivity baseActivity=((BaseActivity)context);
		if(baseActivity!=null){
			baseActivity.showLoadingDialog(loadingStr);
		}

		httpClient.addHeader("Content-Type", "application/json; charset=UTF-8");
		httpClient.setURLEncodingEnabled(true);
		httpClient.get(uri, params, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
				handleFailed(throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				handleFailed(throwable);
			}

			private void handleFailed(Throwable throwable) {
				((BaseActivity) context).hideLoadingDialog();
				switch (type) {
					case BOOLEAN:    //boolean值，标示请求是否成功
						result.onResult(false);
						break;
					default  :    //返回单个对象数据
						result.onResult(null);
						break;
				}
				MyLogUtils.e(throwable.getMessage());
				if(isNeedToast) {
					MyToastUtils.showToast("网络异常");
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				handleFailed(throwable);
			}


			@Override
			public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
				MyLogUtils.e(uri, response.toString());
				((BaseActivity) context).hideLoadingDialog();
				switch (type) {
					case OBJECT://返回单个对象数据
						result.onResult(ParseJsonUtilst.getDetail(context, response.toString(), cls));
						break;
					case ARRAY://返回数组对象数据
						result.onResult(ParseJsonUtilst.getDetailArray(context, response.toString(), cls));
						break;
					case BOOLEAN://boolean值，标示请求是否成功
						result.onResult(ParseJsonUtilst.getDetailStatus(context, response.toString()));
						break;
					case JSONSTRING:
						result.onResult(response.toString());
						break;
				}

			}
		});
	}
	
	/**
	 * 发送http请求，基于android-async-http开源框架
	 * @param context 当前上下文
	 * @param uri 请求路径
	 * @param params 请求参数
	 * @param type 返回值类型  ReturnType.OBJECT:返回单个对象，ReturnType.ARRAY:返回数组对象，ReturnType.ARRAY:返回boolean对象
	 * @param cls 待返回实体类Class字节码
	 * @param result 结果回调
	 */
	public static <T> void sendRequest(final Context context, final String uri, JSONObject params, final ReturnType type, final Class<T> cls, String loadingStr, final HttpResult result) {
		sendRequest(context, uri, params, type, cls, loadingStr, result,true);
	}

	/**
	 * 取消请求
	 * @param context
	 */
	public static void cancelRequest(final Context context) {
		httpClient.cancelRequests(context, true);
	}

	/**
	 * http请求结果回调
	 */
	public interface HttpResult {
		public void onResult(Object object);
	}
	
	/**
	 * 枚举，表示http请求返回值类型
	 */
	public enum ReturnType{
		OBJECT,ARRAY,BOOLEAN,JSONSTRING;
	}
}
