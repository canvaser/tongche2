package com.siwei.tongche.module.usercenter.ope;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.image.ImageUtils;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.usercenter.bean.DriverVehicleInfoBean;
import com.siwei.tongche.module.usercenter.constant.UserCenterConstant;
import com.siwei.tongche.utils.BitmapUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.views.RoundImageView;
import com.siwei.tongche.views.SDSimpleSetItemView;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2016-12-27.
 */

public class UserCenterUIOpe extends BaseUIOpe{



    @Bind(R.id.userCenter_headerImg)
    RoundImageView mHeadImg;//用户头像
    @Bind(R.id.userCenter_userName)
    TextView mUserName;//用户名
    @Bind(R.id.userCenter_mobile)
    TextView mMobile;//手机号
    @Bind(R.id.userCenter_company)
    TextView mCompany;//公司名称
    @Bind(R.id.userCenter_driverid)
    TextView mDriverId;//驾驶证号


    @Bind(R.id.userCenter_layout_userManage)
    SDSimpleSetItemView mItemUserManage;//用户管理
    @Bind(R.id.userCenter_layout_driverManage)
    SDSimpleSetItemView mItemDriverManage;//司机管理
    @Bind(R.id.userCenter_layout_carManage)
    SDSimpleSetItemView mItemCarManage;//车辆管理

    @Bind(R.id.userCenter_layout_setting)
    SDSimpleSetItemView mItemSetting;//消息设置
    @Bind(R.id.userCenter_layout_myCollect)
    SDSimpleSetItemView mItemMyCollect;//我的收藏
    @Bind(R.id.userCenter_layout_message)
    SDSimpleSetItemView mItemMessage;//消息
    @Bind(R.id.userCenter_layout_forwardingUnit)
    SDSimpleSetItemView mItemForwardingUnit;//发货单位
    @Bind(R.id.userCenter_layout_calleach)
    SDSimpleSetItemView callEach;//对讲机
    @Bind(R.id.layout_driver_car)
    View driverCar;
    @Bind(R.id.tv_carno)
    TextView carNo;//车牌
    @Bind(R.id.tv_carid)
    TextView carID;//车号
    @Bind(R.id.tv_brade)
    TextView bradeTV;//品牌
    @Bind(R.id.tv_guige)
    TextView guiGeTV;//规格

    View[] views;

    @Bind(R.id.iv_car)
    ImageView carIV;
    @Bind(R.id.iv_lisence)
    ImageView lisenceIV;

    UserCenterConstant userCenterConstant=new UserCenterConstant();

    public UserCenterUIOpe(Context context, View containerView) {
        super(context, containerView);
        init();
        checkUserType();
    }

    public void initUI(){
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        ImageLoaderManager.getHeaderImage(userInfo.getUHeadImg(),mHeadImg);
        mUserName.setText(userInfo.getUName());
        mMobile.setText(userInfo.getUPhone());
        mCompany.setText(userInfo.getUnitName());
        mDriverId.setText(userInfo.getUDriveNo());

    }

    public void initDriverInfo(DriverVehicleInfoBean infoBean){
        if(infoBean==null){
            return;
        }
        carNo.setText(infoBean.getV_NO());
        carID.setText(infoBean.getVPlateNumber());
        guiGeTV.setText(infoBean.getVGgxh());
        bradeTV.setText(infoBean.getVBrand());
        ImageLoaderManager.getImageLoader().displayImage(infoBean.getVImg(),carIV);
        ImageLoaderManager.getImageLoader().displayImage(infoBean.getVTravelImg(),lisenceIV);
    }

    public void init(){
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        //驾驶证号
        if(userInfo.getUDriveNo()!=null){
            mDriverId.setText(userInfo.getUDriveNo());
        }else{
            mDriverId.setVisibility(View.GONE);
        }

        views = new View[]{mItemForwardingUnit,mItemUserManage,mItemDriverManage,mItemCarManage,mItemSetting,mItemMyCollect,mItemMessage,callEach};

        mItemForwardingUnit.setTitleText("发货单位");
        mItemForwardingUnit.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemUserManage.setTitleText("用户管理");
        mItemUserManage.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemDriverManage.setTitleText("驾驶员管理");
        mItemDriverManage.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemCarManage.setTitleText("车辆管理");
        mItemCarManage.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemSetting.setTitleText("设置");
        mItemSetting.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemMyCollect.setTitleText("我的收藏");
        mItemMyCollect.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        mItemMessage.setTitleText("消息");
        mItemMessage.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

        callEach.setTitleText("对讲机");
        callEach.setTitleImage(R.drawable.selector_pressed_usercenter_manaager_cars);

    }

    public void checkUserType() {
        UserInfo userInfo =CacheUtils.getLocalUserInfo();
        String role = userInfo.getURoleCode()+userInfo.getUUnitRole();
        //用户角色0驾驶员、1发货单位工作人员2收货单位工作人员、3租赁公司工作人员
        //用户在单位角色0创建者、1管理员、2普通员工


        boolean[] booleen =null;
        switch (role){
            case AppConstants.USER_TYPE.TYPE_DRIVER+AppConstants.USER_UNIT_ROLE.ROLE_CREATOR:
            case AppConstants.USER_TYPE.TYPE_DRIVER+AppConstants.USER_UNIT_ROLE.ROLE_MANAGER:
            case AppConstants.USER_TYPE.TYPE_DRIVER+AppConstants.USER_UNIT_ROLE.ROLE_NORMAL:
                booleen=userCenterConstant.roles[0];
                driverCar.setVisibility(View.VISIBLE);
                break;
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_NORMAL:
                booleen=userCenterConstant.roles[1];
                break;
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_MANAGER:
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_CREATOR:
                booleen=userCenterConstant.roles[2];
                break;
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_NORMAL:
                booleen=userCenterConstant.roles[3];
                break;
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_MANAGER:
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_CREATOR:
                booleen=userCenterConstant.roles[4];
                break;
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_NORMAL:
                booleen=userCenterConstant.roles[5];
                break;
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_MANAGER:
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT+AppConstants.USER_UNIT_ROLE.ROLE_CREATOR:
                booleen=userCenterConstant.roles[6];
                break;
        }
        for(int i=0;i<booleen.length;i++){
            if(!booleen[i]){
            //    views[i].setVisibility(View.GONE);
            }
        }
    }

    public SDSimpleSetItemView getCallEach() {
        return callEach;
    }

    public View getDriverCar() {
        return driverCar;
    }

    public TextView getmCompany() {
        return mCompany;
    }

    public TextView getmDriverId() {
        return mDriverId;
    }

    public RoundImageView getmHeadImg() {
        return mHeadImg;
    }

    public SDSimpleSetItemView getmItemCarManage() {
        return mItemCarManage;
    }

    public SDSimpleSetItemView getmItemDriverManage() {
        return mItemDriverManage;
    }

    public SDSimpleSetItemView getmItemForwardingUnit() {
        return mItemForwardingUnit;
    }

    public SDSimpleSetItemView getmItemMessage() {
        return mItemMessage;
    }

    public SDSimpleSetItemView getmItemMyCollect() {
        return mItemMyCollect;
    }

    public SDSimpleSetItemView getmItemSetting() {
        return mItemSetting;
    }

    public SDSimpleSetItemView getmItemUserManage() {
        return mItemUserManage;
    }

    public TextView getmMobile() {
        return mMobile;
    }

    public TextView getmUserName() {
        return mUserName;
    }

    public View[] getViews() {
        return views;
    }

    public TextView getBradeTV() {
        return bradeTV;
    }

    public TextView getCarID() {
        return carID;
    }

    public ImageView getCarIV() {
        return carIV;
    }

    public TextView getCarNo() {
        return carNo;
    }

    public TextView getGuiGeTV() {
        return guiGeTV;
    }

    public ImageView getLisenceIV() {
        return lisenceIV;
    }


}
