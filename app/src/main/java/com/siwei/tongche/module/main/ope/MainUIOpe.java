package com.siwei.tongche.module.main.ope;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.fragment.ExpressListFragment;
import com.siwei.tongche.module.main.fragment.MapInfoFragment;
import com.siwei.tongche.module.main.fragment.RentMapFragment;
import com.siwei.tongche.module.main.fragment.TaskFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by HanJinLiang on 2017-01-12.
 */

public class MainUIOpe extends BaseUIOpe {
    @Bind(R.id.layout_unbind)
    LinearLayout mLayout_unbind;//未绑定单位页面
    @Bind(R.id.layout_unbind_btn)
    LinearLayout mLayout_unbind_btn;//未绑定单位页面 操作按钮
    @Bind(R.id.bindUnit_scan)
    Button mBindUnit_scan;//扫描绑定
    @Bind(R.id.bindUnit_hint)
    TextView mBindUnit_hint;//绑定单位提示  您尚未绑定单位,请扫描管理员二维码\n或者前往个人中心绑定
    @Bind(R.id.main_title)//首页标题
            TextView mTvMain_title;


    MapInfoFragment mMapInfoFragment;
    TaskFragment mTaskFragment;
    ExpressListFragment mExpressListFragment;
    RentMapFragment mRentMapFragment;

    ArrayList<Fragment> mAllFragments = new ArrayList<>();

    public MainUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public void initView(FragmentActivity context, UserInfo mUserInfo) {
        switch (mUserInfo.getURoleCode()) {
            case AppConstants.USER_TYPE.TYPE_DRIVER://司机
                mExpressListFragment = new ExpressListFragment();
                mAllFragments.add(mExpressListFragment);
                break;
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT://发货单位工作人员
                mMapInfoFragment = new MapInfoFragment();
                mTaskFragment = new TaskFragment();
                mAllFragments.add(mTaskFragment);
                mAllFragments.add(mMapInfoFragment);
                break;
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT://收货单位工作人员
                //管理员
                if (mUserInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR) || mUserInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)) {
                    mMapInfoFragment = new MapInfoFragment();
                    mTaskFragment = new TaskFragment();
                    mAllFragments.add(mMapInfoFragment);
                    mAllFragments.add(mTaskFragment);
                } else {
                    mExpressListFragment = new ExpressListFragment();
                    mTaskFragment = new TaskFragment();
                    mAllFragments.add(mExpressListFragment);
                    mAllFragments.add(mTaskFragment);
                }
                break;
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT://租赁公司工作人员
                mRentMapFragment = new RentMapFragment();
                mAllFragments.add(mRentMapFragment);
                break;
        }
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mAllFragments) {
            fragmentTransaction.add(R.id.forwarding_fragment_container, fragment);
        }
        fragmentTransaction.commit();
    }


    /**
     * 检查单位是否绑定
     */
    public void checkUnit(UserInfo mUserInfo) {
        //绑定到所属审核状态0未绑定、1已绑定、2审核中、3绑定失败
        switch (mUserInfo.getUAuditStatus()){
            case "0"://未绑定
                mLayout_unbind.setVisibility(View.VISIBLE);
                mBindUnit_hint.setText("您尚未绑定单位，请扫描管理员二维码\n或者前往个人中心绑定");
                break;
            case "1"://已绑定
                mLayout_unbind.setVisibility(View.GONE);
                break;
            case "2"://审核中
                mLayout_unbind.setVisibility(View.VISIBLE);
                mLayout_unbind_btn.setVisibility(View.GONE);
                mBindUnit_hint.setText("您的单位信息已提交，请等待审核！");
                break;
            case "3"://绑定失败
                mLayout_unbind.setVisibility(View.VISIBLE);
                mBindUnit_hint.setText("您提交的单位信息审核失败，请扫描管理员二维码\n或者前往个人中心绑定");
                break;
        }

        if (mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)) {//司机
            //绑定到发货单位审核状态0未绑定、1已绑定、2审核中、3绑定失败
            switch (mUserInfo.getUBindSendUnitStatus()){
                case "0"://未绑定
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mBindUnit_hint.setText("您尚未绑定发货单位，请扫描管理员二维码\n或者前往个人中心绑定");
                    break;
                case "1"://已绑定
                    mLayout_unbind.setVisibility(View.GONE);
                    break;
                case "2"://审核中
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mLayout_unbind_btn.setVisibility(View.GONE);
                    mBindUnit_hint.setText("您的发货单位信息已提交，请等待审核！");
                    break;
                case "3"://绑定失败
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mBindUnit_hint.setText("您提交的发货单位信息审核失败，请扫描管理员二维码\n或者前往个人中心绑定");
                    break;
            }

            //绑定车辆  0未绑定、1已绑定、2审核中、3绑定失败
            switch (mUserInfo.getUBindVehicleStatus()){
                case "0"://未绑定
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mBindUnit_scan.setVisibility(View.GONE);
                    mBindUnit_hint.setText("您尚未绑定车辆，请前往个人中心绑定");
                    break;
                case "1"://已绑定
                    mLayout_unbind.setVisibility(View.GONE);
                    break;
                case "2"://审核中
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mLayout_unbind_btn.setVisibility(View.GONE);
                    mBindUnit_hint.setText("您的车辆信息已提交，请等待审核！");
                    break;
                case "3"://绑定失败
                    mLayout_unbind.setVisibility(View.VISIBLE);
                    mBindUnit_scan.setVisibility(View.GONE);
                    mBindUnit_hint.setText("您提交的车辆信息审核失败，请前往个人中心绑定");
                    break;
            }
        }
    }

    public LinearLayout getmLayout_unbind() {
        return mLayout_unbind;
    }

    public ArrayList<Fragment> getmAllFragments() {
        return mAllFragments;
    }

    public LinearLayout getmLayout_unbind_btn() {
        return mLayout_unbind_btn;
    }

    public Button getmBindUnit_scan() {
        return mBindUnit_scan;
    }

    public TextView getmBindUnit_hint() {
        return mBindUnit_hint;
    }

    public TextView getmTvMain_title() {
        return mTvMain_title;
    }

    public MapInfoFragment getmMapInfoFragment() {
        return mMapInfoFragment;
    }

    public TaskFragment getmTaskFragment() {
        return mTaskFragment;
    }

    public ExpressListFragment getmExpressListFragment() {
        return mExpressListFragment;
    }

    public RentMapFragment getmRentMapFragment() {
        return mRentMapFragment;
    }
}
