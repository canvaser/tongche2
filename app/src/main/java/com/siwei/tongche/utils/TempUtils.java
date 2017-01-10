package com.siwei.tongche.utils;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.siwei.tongche.R;


public class TempUtils {
	/**
	 * 
	 * @return
	 */
	public static String getSexString(String sex){
		if(sex.equals("0")){
			return "女";
		}
		return "男";
	}
	
	/**
	 * 设置空视图
	 * @param listView
	 * @param msg
	 */
	public static void setEmptyView(Context context,ListView listView,String msg){
 
		TextView emptyView=new TextView(context);
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText(msg);

		emptyView.setTextColor(ContextCompat.getColor(context, R.color.text_gray));
		emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
		//下面两行很重要
		emptyView.setVisibility(View.VISIBLE);  
		((ViewGroup)listView.getParent()).addView(emptyView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		if(listView.getEmptyView()!=null){
			return;
		}
		listView.setEmptyView(emptyView);
	}
	
	 /**
	  * 跳转到H5页面
	  * @param context
	  * @param type
	  * 注册条款1      收件员招募2    操作指南 3      常见问题4   关于顺路5   10下载
	  */
	 public static void changeH5Activity(Context context,int type){
//		 Intent intent=new Intent(context,HtmlActivity.class);
//		 intent.putExtra("type", type);
//		 context.startActivity(intent);
	 }
	 
	/**
	  * 显示确认对话框
	  * @param context   必须是Activity.this
	  * @param title
	  * @param listener
	  */
	 public static void showAlertDailog(Context context,String title,OnClickListener listener){
		 new AlertDialog.Builder(context).setMessage(title)
				 .setPositiveButton("确认", listener)
				 .setNegativeButton("取消", null)
				 .show();
	 }

//	/**
//	 * 返回运输单状态
//	 * @param status
//	 * @return
//	 */
//	public static String getOrderStatus(String status){
//		String statusStr=null;
//		switch (status){
//			case AppConstants.ORDER_STATUS.STATUS_CREATE:
//				statusStr="等待签收";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_NORMAL_SIGN:
//				statusStr="已签收";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_ACCIDENT:
//				statusStr="车辆故障";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_UNUSUAL_SIGN:
//				statusStr="司机确认";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_DRIVER_CONFIRM:
//				statusStr="异常签收";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_CANCEL:
//				statusStr="撤销确认";
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_CANCEL_FINISH:
//				statusStr="已撤销";
//				break;
//		}
//		return statusStr;
//	}

//	public static int getOrderStatusColor(Context context,String order_state) {
//		int color= ContextCompat.getColor(context,R.color.text_gray);
//		switch (order_state){
//			case AppConstants.ORDER_STATUS.STATUS_CREATE:
//			case AppConstants.ORDER_STATUS.STATUS_NORMAL_SIGN:
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_ACCIDENT:
//				color= ContextCompat.getColor(context,R.color.text_red);
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_UNUSUAL_SIGN:
//				color= ContextCompat.getColor(context,R.color.text_red);
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_DRIVER_CONFIRM:
//				color= ContextCompat.getColor(context,R.color.text_red);
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_CANCEL:
//				color= ContextCompat.getColor(context,R.color.text_red);
//				break;
//			case AppConstants.ORDER_STATUS.STATUS_CANCEL_FINISH:
//				color= ContextCompat.getColor(context,R.color.text_red);
//				break;
//		}
//		return color;
//	}
}
