package com.siwei.tongche.module.login.activitys;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.MD5Util;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.ClearEditText;
import com.siwei.tongche.views.SDSendValidateButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 *注册
 */
public class RegisterActivity extends BaseActivity implements SDSendValidateButton.SDSendValidateButtonListener {
	@Bind(R.id.cb_register_rule)
	CheckBox mCb_register_rule;//用户协议

	@Bind(R.id.tv_user_role)
	TextView mTv_user_rule;//用户协议


	@Bind(R.id.btn_code)
	SDSendValidateButton btn_code;

	@Bind(R.id.et_mobile)
	ClearEditText et_mobile;//手机号
	@Bind(R.id.et_code)
	ClearEditText et_code;//验证码
	@Bind(R.id.register_pwd)
	ClearEditText register_pwd;//密码
	@Bind(R.id.register_pwd_re)
	ClearEditText register_pwd_re;//确认密码

	private String mobile_code;//请求验证码的手机号码


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	public int getContentView() {
		return R.layout.activity_register;
	}

	private void initView() {
		setTitle("注册");
		mTv_user_rule.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
		btn_code.setmListener(this);
	}

	@Override
	public void onClickSendValidateButton() {
		mobile_code = et_mobile.getText().toString().trim();
		if(MyRegexpUtils.isEmpty(mobile_code)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobile_code)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		RequestParams params=new RequestParams();
		params.put("PPhone", mobile_code);
		params.put("PType", "1");
		btn_code.startTickWork();
		MyHttpUtil.sendGetRequest(this, MyUrls.SMS_CODE, params, MyHttpUtil.ReturnType.BOOLEAN, null,new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if((boolean)object){
						MyToastUtils.showToast("短信验证码发送成功");
						btn_code.startTickWork();
					}else{
						btn_code.stopTickWork();//取消计时
					}
				} catch (Exception e) {
				}
			}
		});
	}

	@OnClick({R.id.register_btn_Go })
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.register_btn_Go:
 				register();//注册
				break;
			default:
				break;
		}
	}
		/**
         * 下一步
         */
	private void register() {
		if(!mCb_register_rule.isChecked()){
			MyToastUtils.showToast("请阅读并同意用户协议");
			return;
		}
		final String mobile = et_mobile.getText().toString().trim();
		final String pwd = register_pwd.getText().toString().trim();
		String re_pwd = register_pwd_re.getText().toString().trim();
		String code=et_code.getText().toString().trim();

		if(MyRegexpUtils.isEmpty(mobile)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobile)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		if(MyRegexpUtils.isEmpty(pwd)){
			MyToastUtils.showToast("请输入密码");
			return;
		}
		if(MyRegexpUtils.isEmpty(re_pwd)){
			MyToastUtils.showToast("请输入确认密码");
			return;
		}
		if(!MyRegexpUtils.isPassword(pwd)){
			MyToastUtils.showToast("请输入6-16位数字或字母的密码");
			return;
		}
		if(!pwd.equals(re_pwd)){
			MyToastUtils.showToast("密码不一致");
			return;
		}

		if(MyRegexpUtils.isEmpty(code)){
			MyToastUtils.showToast("请输入短信验证码");
			return;
		}
//		UPhone（手机号）、PCode（验证码）、PassWord（密码）、UUserType（用户类型（1 驾驶员、2非驾驶员））
		JSONObject params = new JSONObject();
		params.put("UPhone", mobile);
		params.put("PCode", code);
		params.put("PassWord", MD5Util.md5(pwd));
		MyHttpUtil.sendRequest(this, MyUrls.REGISTER, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if((boolean)object){
						finish();
					}
				} catch (Exception e) {
				}
			}
		});
	}

	protected void onDestroy() {
		super.onDestroy();
		btn_code.stopTickWork();
	}

}
