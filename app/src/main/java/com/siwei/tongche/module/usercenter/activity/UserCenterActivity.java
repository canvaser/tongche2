package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyApplication;
import com.siwei.tongche.dialog.NormalAlertDialog;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.bind_unit.BindUserUnitActivity;
import com.siwei.tongche.module.carmanager.activity.AddCarActivity;
import com.siwei.tongche.module.carmanager.activity.CarManagerActivity;
import com.siwei.tongche.module.login.activitys.LoginActivity;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.message.MessageActivity;
import com.siwei.tongche.module.mycollect.MyCollectActivity;
import com.siwei.tongche.module.usercenter.bean.DriverVehicleInfoBean;
import com.siwei.tongche.module.usercenter.dialog.QRCodeDialog;
import com.siwei.tongche.module.usercenter.ope.UserCenterUIOpe;
import com.siwei.tongche.module.usermanager.UserManagerActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;

import butterknife.OnClick;

/**
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity implements View.OnClickListener {


    UserCenterUIOpe uiOpe;

    @Override
    public int getContentView() {
        return R.layout.activity_user_center;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe= new UserCenterUIOpe(this,rootView);
        setTitle("个人中心");
        getNetUserBaseInfo();
        getNetDriverBaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uiOpe.initUI();
    }

    @OnClick({R.id.userCenter_layout_forwardingUnit,R.id.userCenter_layout_userManage,R.id.userCenter_layout_driverManage,R.id.userCenter_layout_carManage,
            R.id.userCenter_layout_setting,R.id.userCenter_layout_myCollect,R.id.userCenter_layout_message,
            R.id.btn_login_out,R.id.userCenter_top,R.id.userCenter_QR_Code,R.id.layout_driver_car})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userCenter_layout_forwardingUnit://发货单位
                Intent intent = new Intent(this,BindUserUnitActivity.class);
                intent.putExtra(BindUserUnitActivity.KEY_UNIT,BindUserUnitActivity.SEND_UNIT);
                startActivity(intent);
                break;
            case R.id.userCenter_layout_userManage://用户管理
                startActivity(new Intent(this,UserManagerActivity.class));
                break;
            case R.id.userCenter_layout_driverManage://驾驶员管理
                startActivity(new Intent(this,UserManagerActivity.class));
                break;
            case R.id.userCenter_layout_carManage://车辆管理
                startActivity(new Intent(this,CarManagerActivity.class));
                break;
            case R.id.userCenter_layout_setting://消息设置
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.userCenter_layout_myCollect://我的收藏
                startActivity(new Intent(this,MyCollectActivity.class));
                break;
            case R.id.userCenter_layout_message://消息
                startActivity(new Intent(this,MessageActivity.class));
                break;

            case R.id.btn_login_out:
                showLoginOutDailog();
                break;
            case R.id.userCenter_top://详细信息
                startActivity(new Intent(this,UserBaseInfoActivity.class));
                break;
            case R.id.userCenter_QR_Code:
                showQRCodeDialog();
                break;
            case R.id.layout_driver_car:
                startActivity(new Intent(this,AddCarActivity.class));
                break;
        }
    }


    public void getNetUserBaseInfo(){
        RequestParams params=new RequestParams();
        final UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_DRIVER_INFO, params, MyHttpUtil.ReturnType.JSONSTRING,null, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try{
                    JSONObject data= JSON.parseObject(object.toString());
                    String status=data.getString("status");
                    if(status.equals("1000")){
                      JSONObject resultData=  data.getJSONObject("resultData");
                       String UHeadImg= resultData.getString("UHeadImg");
                        String UName= resultData.getString("UName");
                        String UPhone= resultData.getString("UPhone");
                        String UDriveNo= resultData.getString("UDriveNo");
                        String UAuditStatus= resultData.getString("UAuditStatus");
                        String UnitName= resultData.getString("UnitName");


                        userInfo.setUHeadImg(UHeadImg);
                        userInfo.setUName(UName);
                        userInfo.setUPhone(UPhone);
                        userInfo.setUDriveNo(UDriveNo);
                        userInfo.setUAuditStatus(UAuditStatus);
                        userInfo.setUnitName(UnitName);
                        CacheUtils.setLocalUserInfo(userInfo);
                        uiOpe.initUI();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                MyLogUtils.e(object+"");
            }
        });
    }

    public void getNetDriverBaseInfo(){
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_DRIVER_VEHICLE_INFO, params, MyHttpUtil.ReturnType.OBJECT, DriverVehicleInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {

                DriverVehicleInfoBean infoBean = (DriverVehicleInfoBean) object;
                uiOpe.initDriverInfo(infoBean);
            }
        });
    }


    QRCodeDialog mQrCodeDialog;
    /**
     * 二维码弹窗
     */
    private void showQRCodeDialog() {
        if(mQrCodeDialog==null){
            mQrCodeDialog= new QRCodeDialog(this);
        }
        mQrCodeDialog.show();
    }

    /**
     * 退出登录对话框
     */
    private void showLoginOutDailog() {
        NormalAlertDialog exitDialog=new NormalAlertDialog(this,"确定退出当前账号？");
        exitDialog.setClicklistener(new NormalAlertDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                exitLoginOut();
            }

            @Override
            public void doCancel() {

            }
        });
        exitDialog.show();
    }

    protected void exitLoginOut() {
        MyApplication.getMyApplication().exitApp();
        //清楚缓存数据
        CacheUtils.clearCache(getApplicationContext());
        startActivity(new Intent(this,LoginActivity.class));
    }

//    @Override
//    protected void onAddCompanySuccess() {
//        //取消弹窗
//        if(mQrCodeDialog!=null&&mQrCodeDialog.isShowing()){
//            mQrCodeDialog.dismiss();
//            mQrCodeDialog=null;
//        }
//        //刷新单位显示
//        UserInfo userInfo=CacheUtils.getLocalUserInfo();
//        mCompany.setText(userInfo.getUNIT_NAME());
//    }
}
