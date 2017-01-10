package com.siwei.tongche.module.usercenter.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.views.RoundImageView;
import com.siwei.tongche.zxing.ZXingQRCodeUtils;

import butterknife.Bind;

/**
 * 二维码名片
 */
public class QRCodeCardActivity extends BaseActivity {
    @Bind(R.id.userCenter_headerImg)
    RoundImageView mHeadImg;//用户头像
    @Bind(R.id.userCenter_userName)
    TextView mUserName;//用户名
    @Bind(R.id.userCenter_mobile)
    TextView mMobile;//手机号
    @Bind(R.id.userCenter_company)
    TextView mCompany;//公司名称

    @Bind(R.id.img_qrCode)
    ImageView mImg_qrCode;//二维码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private Bitmap mQRCodeBitmap;
    private void initView() {
        setTitle("二维码名片");
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
//        ImageLoaderManager.getHeaderImage(userInfo.getHEAD_PROTRAIT(), mHeadImg);
//        mUserName.setText(userInfo.getUSER_NAME());
//        mMobile.setText(userInfo.getMOBILE());
//        mCompany.setText(userInfo.getUNIT_NAME());
//
         JSONObject params=new JSONObject();
//        params.put("id", userInfo.getUSER_ID());
//        params.put("roleId", userInfo.getROLEID());
//        params.put("headerImage", userInfo.getHEAD_PROTRAIT());
//        params.put("name", userInfo.getUSER_NAME());
//        params.put("carNo", userInfo.getPLATE_NUMBER());
//        params.put("driverMobile", userInfo.getMOBILE());
//        params.put("carType", userInfo.getVEHICLE_TYPE_CODE());//（车辆类型代码 0砼车、1泵车）
//        params.put("carSize", userInfo.getUNITAGE());
//        if(userInfo.getROLEID().equals(AppConstants.USER_TYPE.TYPE_DRIVER_MANAGER)){//车队长
//            params.put("unitName", userInfo.getUNIT_NAME());//单位
//        }
        mQRCodeBitmap= ZXingQRCodeUtils.createQRcode(this, params.toString());
        mImg_qrCode.setImageBitmap(mQRCodeBitmap);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mQRCodeBitmap!=null&&!mQRCodeBitmap.isRecycled()){
            mQRCodeBitmap.recycle();
            mQRCodeBitmap=null;
        }
    }

//    @Override
//    protected void onAddCompanySuccess() {
//        //刷新单位显示
//        UserInfo userInfo=CacheUtils.getLocalUserInfo();
//        mCompany.setText(userInfo.getUNIT_NAME());
//    }
}
