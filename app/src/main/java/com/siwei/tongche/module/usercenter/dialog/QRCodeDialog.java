package com.siwei.tongche.module.usercenter.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.views.RoundImageView;
import com.siwei.tongche.zxing.ZXingQRCodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by lg on 2015/10/22.
 */
public class QRCodeDialog extends Dialog {
	private Context context;

	@Bind(R.id.userCenter_headerImg)
	RoundImageView mHeadImg;//用户头像
	@Bind(R.id.userCenter_userName)
	TextView mUserName;//用户名
	@Bind(R.id.userCenter_mobile)
	TextView mMobile;//手机号
	@Bind(R.id.userCenter_company)
	TextView mCompany;//公司名称

	@Bind(R.id.img_qrCode)
	ImageView mImg_qrCode;//二维码图片
	public QRCodeDialog(Context context) {
		super(context, R.style.dialogBase);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_qr_code, null);
		setContentView(view);
		ButterKnife.bind(this, view);
		initView();
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 显示用户信息
	 */
	private void initView() {
		UserInfo userInfo= CacheUtils.getLocalUserInfo();
		ImageLoaderManager.getHeaderImage(userInfo.getUHeadImg(), mHeadImg);
		mUserName.setText(userInfo.getUName());
		mMobile.setText(userInfo.getUPhone());
		mCompany.setText(userInfo.getUUnitId());

		JSONObject params=new JSONObject();
//		params.put("id", userInfo.getUSER_ID());
//		params.put("roleId", userInfo.getROLEID());
//		params.put("headerImage", userInfo.getHEAD_PROTRAIT());
//		params.put("name", userInfo.getUSER_NAME());
//		params.put("carNo", userInfo.getPLATE_NUMBER());
//		params.put("driverMobile", userInfo.getMOBILE());
//		params.put("carType", userInfo.getVEHICLE_TYPE_CODE());//（车辆类型代码 0砼车、1泵车）
//		params.put("carSize", userInfo.getUNITAGE());
//		if(userInfo.getROLEID().equals(AppConstants.USER_TYPE.TYPE_DRIVER_MANAGER)){//车队长
//			params.put("unitName", userInfo.getUNIT_NAME());//单位
//		}
		mImg_qrCode.setImageBitmap(ZXingQRCodeUtils.createQRcode(context, params.toString()));
	}

}
