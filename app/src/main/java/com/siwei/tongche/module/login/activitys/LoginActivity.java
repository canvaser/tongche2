package com.siwei.tongche.module.login.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.activity.MainActivity;
import com.siwei.tongche.module.main.activity.SelectRoleActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MD5Util;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.PersonalScrollView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 * @author hjl
 *
 * 2016年1月7日下午4:51:04
 */
public class LoginActivity extends BaseActivity {
	@Bind(R.id.iv_personal_bg)
	ImageView iv_personal_bg;//


	@Bind(R.id.et_user_name)
	EditText et_user_name;//用户名
	@Bind(R.id.et_password)
	EditText et_password;//密码

	@Bind(R.id.image_logo)
	ImageView image_logo;//密码



	private String user_name;//用户名
	private String user_pwd;//密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
	}

	private void initView(){

		et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId== EditorInfo.IME_ACTION_GO){//登录
					loginByPwd();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public int getContentView() {
		return R.layout.activity_login;
	}


	@OnClick({R.id.btn_login,R.id.tv_forget_pwd,R.id.btn_register})
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.btn_login://登陆
//  			startActivity(new Intent(this,SelectRoleActivity.class));
				loginByPwd();
				break;
			case R.id.tv_forget_pwd://
				Intent intent = new Intent(this,ForgetActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_register://注册
				startActivity(new Intent(this,RegisterActivity.class));
				break;
			default:
				break;
		}
	}

	/**
	 * 密码登陆
	 */
	private void loginByPwd() {
		//IdentityType(登录方式1手机号登录、2 QQ登录、3 微信登录、4 微博登录)、
		//Identifier（用户手机号或第三方应用的唯一标识）、
		//Credential（密码凭证）
		//第一次第三方登录 UPhone(手机号)、UUserType（用户类型）、UName（用户名称）

		user_name = et_user_name.getText().toString();
		user_pwd=et_password.getText().toString();
		if(MyRegexpUtils.isEmpty(user_name)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(MyRegexpUtils.isEmpty(user_pwd)){
			MyToastUtils.showToast("请输入密码");
			return;
		}
		if(!MyRegexpUtils.isPhone(user_name)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		if(!MyRegexpUtils.isPassword(user_pwd)){
			MyToastUtils.showToast("请输入6-16位数字或字母的密码");
			return;
		}
//		user_mobile	String	手机号
//		user_pwd	String	密码
		JSONObject params=new JSONObject();
		params.put("Identifier", user_name);
		params.put("Credential", MD5Util.md5(et_password.getText().toString()));
		params.put("IdentityType","1");
		MyHttpUtil.sendRequest(this, MyUrls.LOGIN, params, MyHttpUtil.ReturnType.OBJECT, UserInfo.class, "登录中...",new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if(object!=null){
						parseLoginJson((UserInfo) object);
						//保存用户名密码
						CacheUtils.savePrefString(getApplicationContext(), "user_name", user_name);
						CacheUtils.savePrefString(getApplicationContext(), "user_pwd", user_pwd);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		String user_name = CacheUtils.getSavePrefString(getApplicationContext(), "user_name");
		String user_pwd = CacheUtils.getSavePrefString(getApplicationContext(), "user_pwd");
		et_user_name.setText(user_name);
		et_password.setText(user_pwd);
	}

//	/**
//	 * 解析登陆时候返回数据
//	 * @param userInfo
//	 */
	protected void parseLoginJson(UserInfo userInfo) {
		CacheUtils.setLocalUserInfo(userInfo);
		if(userInfo.getUUserType()==null || userInfo.getUUserType().equals("0")){
			startActivity(new Intent(this,SelectRoleActivity.class));
			finish();
			return;
		}
		startActivity(new Intent(this, MainActivity.class));
	}

}
