package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.SDSendValidateButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 更改手机号
 */
public class UpdateMobileActivity extends BaseActivity   {
	@Bind(R.id.btn_old_mobile_code)
	SDSendValidateButton btn_old_mobile_code;
	@Bind(R.id.btn_new_mobile_code)
	SDSendValidateButton btn_new_mobile_code;

	@Bind(R.id.et_old_mobile_code)
	EditText et_old_mobile_code;//旧号码 验证码
	@Bind(R.id.et_current_bind_mobile)
	EditText et_current_bind_mobile;//手机号码


	@Bind(R.id.et_new_mobile_code)
	EditText et_new_mobile_code;//新号码 验证码
	@Bind(R.id.et_new_mobile)
	EditText et_new_mobile;//手机号码

	@Bind(R.id.btn_checkmobile_next)
	Button btn_checkmobile_next;//按钮 下一步


    private String mobileStr;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		et_current_bind_mobile.setText(CacheUtils.getLocalUserInfo().getUPhone());
		et_current_bind_mobile.setFocusable(false);

		btn_old_mobile_code.setmListener(new SDSendValidateButton.SDSendValidateButtonListener() {
			@Override
			public void onClickSendValidateButton() {
				getMobileCode(1);
			}
		});

		btn_new_mobile_code.setmListener(new SDSendValidateButton.SDSendValidateButtonListener() {
			@Override
			public void onClickSendValidateButton() {
				getMobileCode(2);
			}
		});
	}

	@Override
	public int getContentView() {
		return R.layout.activity_update_mobile;
	}



	@OnClick({R.id.btn_checkmobile_next})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_checkmobile_next://下一步
			next();
			break;
		default:
			break;
		}
	}

	private void next() {
		final String mobile = et_new_mobile.getText().toString().trim();
		String code = et_new_mobile_code.getText().toString().trim();
		if(MyRegexpUtils.isEmpty(mobileStr)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobileStr)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		final UserInfo userInfo=CacheUtils.getLocalUserInfo();
//		USERID、MOBILE(手机号)、
//		CODE(验证码)
		JSONObject params=new JSONObject();
		params.put("UID",userInfo.getUID());
		params.put("OldMobile", et_current_bind_mobile.getText().toString());
		params.put("OldMobileCode",et_old_mobile_code.getText().toString());
		params.put("Mobile", et_new_mobile.getText().toString());//edit check
		params.put("MobileCode", et_new_mobile_code.getText().toString());//edit check
		MyHttpUtil.sendRequest(this,MyUrls.UPDATE_MOBILE, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if((boolean)object){
							MyToastUtils.showToast("号码更换成功");
							userInfo.setUPhone(mobile);
							CacheUtils.setLocalUserInfo(userInfo);
							finish();
					}
				} catch (Exception e) {
				}
			}
		});
	}


	public void getMobileCode(final int type) {
		if(type==1){
			mobileStr = et_current_bind_mobile.getText().toString().trim();
		}else{
			mobileStr = et_new_mobile.getText().toString().trim();
		}

		if(MyRegexpUtils.isEmpty(mobileStr)){
			MyToastUtils.showToast("请输入手机号码");
			return;
		}
		if(!MyRegexpUtils.isPhone(mobileStr)){
			MyToastUtils.showToast("手机号码有误");
			return;
		}
		RequestParams params=new RequestParams();
		params.put("PPhone",mobileStr);
		params.put("PType", "3");//edit check
		MyHttpUtil.sendGetRequest(this, MyUrls.SMS_CODE, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {

			@Override
			public void onResult(Object object) {
				try {
					if((boolean)object){
						MyToastUtils.showToast("短信验证码发送成功");
						if(type==1){
							btn_old_mobile_code.startTickWork();
						}else{
							btn_new_mobile_code.startTickWork();
						}

					}else{
						if(type==1){
							btn_old_mobile_code.stopTickWork();//取消计时
						}else{
							btn_new_mobile_code.stopTickWork();//取消计时
						}
					}
				} catch (Exception e) {
				}
			}
		});
	}


}
