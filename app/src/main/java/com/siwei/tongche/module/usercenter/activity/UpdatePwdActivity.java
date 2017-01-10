package com.siwei.tongche.module.usercenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改密码
 * @author hjl
 *
 * 2016年1月15日下午3:57:57
 */
public class UpdatePwdActivity extends BaseActivity {
	@Bind(R.id.update_old)
	EditText update_old;

	@Bind(R.id.update_new)
	EditText update_new;

	@Bind(R.id.update_re_new)
	EditText update_re_new;

	@Override
	public int getContentView() {
		return R.layout.activity_update_pwd;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitle();
	}

	private void initTitle() {
		setTitle("重置密码");
	}
	
	@OnClick({R.id.btn_update_pwd})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_update_pwd:
			updatePwd();
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 修改密码
	 */
	private void updatePwd() {
		String oldPwd = update_old.getText().toString();
		String newPwd  = update_new.getText().toString();
		String reNewPwd  = update_re_new.getText().toString();
		if(MyRegexpUtils.isEmpty(oldPwd)|| MyRegexpUtils.isEmpty(newPwd) || MyRegexpUtils.isEmpty(reNewPwd)){
			MyToastUtils.showToast("请输入密码");
			return;
		}
		if(!newPwd.equals(reNewPwd)){
			MyToastUtils.showToast("两次密码不一致");
			return;
		}
		
		if(!MyRegexpUtils.isPassword(newPwd) || !MyRegexpUtils.isPassword(reNewPwd)){
			MyToastUtils.showToast("请输入6-16位数字或字母的密码");
			return;
		}
//		USERID、OLD_PWD(旧密码)、NEW_PWD(旧密码)
		JSONObject params=new JSONObject();
		params.put("USERID", CacheUtils.getLocalUserInfo().getUID());
		params.put("OLD_PWD",oldPwd);
		params.put("NEW_PWD",newPwd);
		MyHttpUtil.sendRequest(this, MyUrls.RESET_PWD, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
			@Override
			public void onResult(Object object) {
				try {
					if ((boolean) object) {
						MyToastUtils.showToast("设置成功,下次登录请用新新密码登录");
						finish();
					}
				} catch (Exception e) {
				}
			}
		});
		
	}
}
