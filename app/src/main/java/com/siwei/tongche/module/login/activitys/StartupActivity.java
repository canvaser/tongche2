package com.siwei.tongche.module.login.activitys;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.permissions_m.PermissionsActivity;
import com.siwei.tongche.permissions_m.PermissionsChecker;
import com.siwei.tongche.utils.CacheUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;

/**
 * 注册页面
 * @author hjl
 *
 * 2016年1月7日下午4:51:04
 */
public class StartupActivity extends BaseActivity {
	private String user_name;
	private String user_pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user_name = CacheUtils.getSavePrefString(getApplicationContext(), "user_name");
		user_pwd = CacheUtils.getSavePrefString(getApplicationContext(), "user_pwd");
		mPermissionsChecker = new PermissionsChecker(this);
		getSystemConfig();

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				startActivity(new Intent(StartupActivity.this,LoginActivity.class));
				finish();
			}
		},500);
	}

	/**
	 * 获取系统参数
	 */
	private void getSystemConfig() {
//		MyHttpUtil.sendRequest(this, MyUrls.GET_SYSTEM_CONFIG, new JSONObject(), MyHttpUtil.ReturnType.OBJECT, SystemConfig.class, "", new MyHttpUtil.HttpResult() {
//			@Override
//			public void onResult(Object object) {
//				if(object!=null){
//					SystemConfig systemConfig= (SystemConfig) object;
//					CacheUtils.setSystemConfig(systemConfig);//保存到本地
//				}
//			}
//		});
	}


	@Override
	public int getContentView() {
		return R.layout.activity_startup;
	}

	@Override
	protected void setTranslucentStatus(boolean on) {
		//重写此方法    引导页全屏显示
	}
	
	/**
	 * 密码登陆
	 */
	private void loginByPwd() {
//		user_mobile	String	手机号
//		user_pwd	String	密码
//		JSONObject params=new JSONObject();
//		params.put("MOBILE", user_name);
//		params.put("PASSWORD",user_pwd);
//		MyHttpUtil.sendRequest(this, MyUrls.LOGIN, params, MyHttpUtil.ReturnType.OBJECT, UserInfo.class, null, new MyHttpUtil.HttpResult() {
//
//			@Override
//			public void onResult(Object object) {
//				try {
//					if(object==null){
//						startActivityWithoutAnim(new Intent(StartupActivity.this, LoginActivity.class));
//						finishWithoutAnim();
//						return;
//					}
//					CacheUtils.setLocalUserInfo((UserInfo) object);//保存本地信息
//					startActivityWithoutAnim(new Intent(StartupActivity.this, MainActivity.class));
//					finishWithoutAnim();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		
	}

	@Override
	public  void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		// 缺少权限时, 进入权限配置页面
		if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
			startPermissionsActivity();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}


	private static final int REQUEST_CODE = 0; // 请求码

	// 所需的全部权限
	static final String[] PERMISSIONS = new String[]{
			Manifest.permission.CAMERA,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.CALL_PHONE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	private PermissionsChecker mPermissionsChecker; // 权限检测器

	private void startPermissionsActivity() {
		PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
