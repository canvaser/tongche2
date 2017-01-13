package com.siwei.tongche.common;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.siwei.tongche.R;
import com.siwei.tongche.events.EventTag;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.utils.DialogUtil;
import com.sunday.busevent.SDBaseEvent;
import com.sunday.busevent.SDEvent;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 通用
 *
 * @author js02
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements SDEvent {
	public static final String EXTRA_IS_START_BY_NOTIFICATION = "extra_is_start_by_notification";

	public DialogUtil mDialogUtil = null;

	public Dialog mBaseDialog = null;

	private boolean mIsStartByNotification = false;
	private boolean mIsFinishByUser = false;
	protected boolean mIsNeedFinishWhenLogout = true;

	protected View rootView;

	protected FragmentActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		activity =this;
		rootView = LayoutInflater.from(this).inflate(getContentView(),null);
		setContentView(rootView);
		ButterKnife.bind(this);
		// 禁止横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(getTopColor());// 通知栏所需颜色
		baseInit();
	}

	public abstract int getContentView();

	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();

		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * 返回状态栏颜色
	 * 
	 * @return
	 */
	public int getTopColor() {
		return R.color.top_color;
	}

	private void baseInit()
	{
		initIntentData();
		mDialogUtil = new DialogUtil(this);
		EventBus.getDefault().register(this);
	}

	private void initIntentData()
	{
		mIsStartByNotification = getIntent().getBooleanExtra(EXTRA_IS_START_BY_NOTIFICATION, false);

	}

	public void showLoadingDialog(String msg)
	{
		if (msg != null&&!msg.equals(""))
		{
			if (mBaseDialog != null && mBaseDialog.isShowing())
			{
				mBaseDialog.dismiss();
				mBaseDialog = null;
			}
			mBaseDialog = mDialogUtil.showLoading(msg);
		}
	}

	public void hideLoadingDialog()
	{
		if (mBaseDialog != null && mBaseDialog.isShowing())
		{
			try{
			mBaseDialog.dismiss();
			}catch(Exception e){
				
			}
		}
	}

	@Override
	protected void onDestroy()
	{
		EventBus.getDefault().unregister(this);
		super.onDestroy();
		MyHttpUtil.cancelRequest(this);
	}

	@Override
	public void onEvent(SDBaseEvent event)
	{
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (event.getEventTagInt())
		{
			case EventTag.EVENT_EXIT_APP:
				finish();
				break;
			case EventTag.EVENT_LOGOUT_SUCCESS:
				if (mIsNeedFinishWhenLogout)
				{
					finish();
				}
				break;
			case EventTag.EVENT_REGISTER_AND_LOGIN_SUCCESS:
				registerAndLoginSuccess();
				break;
			case EventTag.EVENT_LOGIN_SUCCESS:
				onLoginSuccess();
				break;
			default:
				break;
		}
	}


	protected void onLoginSuccess(){

	}

	protected void registerAndLoginSuccess(){

	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{
	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{
	}


	@Override
	public void onBackPressed()
	{
		finishActivity(true);
		super.onBackPressed();
	}

	protected void finishActivity(boolean isFinishByUser)
	{
		this.mIsFinishByUser = isFinishByUser;
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	TextView mTextTitle;
	/**
	 * 设置头部名称
	 * @param title
	 */
	public void setTitle(String  title) {
		if (mTextTitle == null) {
			mTextTitle = (TextView) findViewById(R.id.tv_title);
		}
		if (mTextTitle != null) {
			mTextTitle.setText(title);
		}
	}
	
    /**
     * 左边的图片和文字
     * @param drawable_id   图片的id
     * @param text   显示的文字
     *  @param flag   是否显示
     */
	public void setLeft(int drawable_id, String text,boolean flag) {
		TextView tv_left = (TextView) findViewById(R.id.tv_left);
		if(!flag){
		    tv_left.setVisibility(View.INVISIBLE);
		    return;
		}
		tv_left.setText(text);
		try {
		Drawable drawable=getResources().getDrawable(drawable_id);
		drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_left.setCompoundDrawables(drawable, null, null, null);
		} catch (Exception e) {
		}
	}
	/**
	 * 右边的图片和文字
	 * @param drawable_id  图片的id
	 * @param text  显示的文字
	 */
	public void setRight(int drawable_id, String text) {
		TextView tv_right = (TextView) findViewById(R.id.tv_right);
		if(tv_right!=null){
			tv_right.setText(text);
			if(drawable_id!=0){
				//资源获取不到，不显示
				Drawable drawable=getResources().getDrawable(drawable_id);
				drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
				tv_right.setCompoundDrawables(drawable, null, null, null);
			}
		}
	}
	
	/**
	 * 是否显示右上角图标
	 * @param isShow
	 */
	public void setRightShowing(boolean isShow) {
		TextView tv_right = (TextView) findViewById(R.id.tv_right);
		 if(isShow){
			 tv_right.setVisibility(View.VISIBLE);
		 }else{
			 tv_right.setVisibility(View.INVISIBLE);
		 }
	}
	
	/**
	 * Header 左边  图片  的点击事件
	 */
	public void onLeftArrow(View view) {
		finish();
	}
	
	/**
	 * Header 左边  文字 的点击事件 
	 */
	public void onLeftText(View view) {
		finish();
	}
 
	/**
	 * Header 右边TextView 的点击事件
	 */
	public void onRightText(View view) {
	}
}
