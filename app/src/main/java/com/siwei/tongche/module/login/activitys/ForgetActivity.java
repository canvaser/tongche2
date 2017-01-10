package com.siwei.tongche.module.login.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.SDSendValidateButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetActivity extends BaseActivity implements SDSendValidateButton.SDSendValidateButtonListener {

	@Bind(R.id.btn_forget_code)
	SDSendValidateButton btn_forget_code;

	@Bind(R.id.btn_forget)
	Button btn_forget;//登陆

	@Bind(R.id.et_forget_mobile)
	EditText et_login_mobile;//手机号码
	@Bind(R.id.et_forget_code)
	EditText et_forget_code;//验证码

	@Bind(R.id.et_new_pwd)
	EditText et_new_pwd;//新密码
	@Bind(R.id.et_re_new_pwd)
	EditText et_re_new_pwd;//确认新密码

    private String mobileStr;
    private String codeStr;
    private String newPwdStr;
    private String renewPwdStr;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("忘记密码");
		btn_forget_code.setmListener(this);
	}

	@Override
	public int getContentView() {
		return R.layout.activity_forget;
	}



	@OnClick({R.id.btn_forget})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_forget://确认
			forgetOk();
			break;
		default:
			break;
		}
	}

	private void forgetOk() {
		mobileStr = et_login_mobile.getText().toString().trim();
		codeStr = et_forget_code.getText().toString().trim();
		newPwdStr = et_new_pwd.getText().toString().trim();
		renewPwdStr = et_re_new_pwd.getText().toString().trim();
		if(MyRegexpUtils.isEmpty(mobileStr)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobileStr)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		if(MyRegexpUtils.isEmpty(newPwdStr)){
			MyToastUtils.showToast("请输入密码");
			return;
		}
		if(!MyRegexpUtils.isPassword(newPwdStr)){
			MyToastUtils.showToast("请输入6-16位数字或字母的密码");
			return;
		}
		if(!newPwdStr.equals(renewPwdStr)){
			MyToastUtils.showToast("密码不一致");
			return;
		}

		JSONObject params=new JSONObject();
		params.put("MOBILE", mobileStr);
		params.put("PASSWORD", newPwdStr);
		params.put("CODE", codeStr);
		MyHttpUtil.sendRequest(this, MyUrls.FORGET_PWD, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
			@Override
			public void onResult(Object object) {
				try {
					if ((Boolean) object) {
						MyToastUtils.showToast("密码设置成功");
						//保存用户名密码
						CacheUtils.savePrefString(getApplicationContext(), "user_name", mobileStr);
						CacheUtils.savePrefString(getApplicationContext(), "user_pwd", newPwdStr);
						loginByPwd(mobileStr,newPwdStr);
					}
				} catch (Exception e) {
				}
			}
		});
	}


	/**
	 * 密码登陆
	 */
	private void loginByPwd(String MOBILE,String PASSWORD) {
//		user_mobile	String	手机号
//		user_pwd	String	密码
//		JSONObject params=new JSONObject();
//		params.put("MOBILE", MOBILE);
//		params.put("PASSWORD",PASSWORD);
//		MyHttpUtil.sendRequest(this, MyUrls.LOGIN, params, MyHttpUtil.ReturnType.OBJECT, UserInfo.class, "登录中...",new MyHttpUtil.HttpResult() {
//
//			@Override
//			public void onResult(Object object) {
//				try {
//					if(object!=null){//自动登录成功
//						CacheUtils.setLocalUserInfo((UserInfo) object);
//						startActivity(new Intent(ForgetActivity.this, MainActivity.class));
//					}else{
//						startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
//					}
//					finish();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	@Override
	public void onClickSendValidateButton() {
		mobileStr = et_login_mobile.getText().toString().trim();
		if(MyRegexpUtils.isEmpty(mobileStr)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobileStr)){
			MyToastUtils.showToast("请输入正确手机号码");
			return;
		}
		JSONObject params=new JSONObject();
		params.put("MOBILE", mobileStr);
		params.put("OTYPE", "forget");
		btn_forget_code.startTickWork();
		MyHttpUtil.sendRequest(this, MyUrls.SMS_CODE, params, MyHttpUtil.ReturnType.BOOLEAN, null,new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if((boolean)object){
						MyToastUtils.showToast("短信验证码发送成功");
						btn_forget_code.startTickWork();
					}else{
						btn_forget_code.stopTickWork();//取消计时
					}
				} catch (Exception e) {
				}
			}
		});
	}



}
