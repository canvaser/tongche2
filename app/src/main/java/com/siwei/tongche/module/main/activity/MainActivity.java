package com.siwei.tongche.module.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.LocationManager;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.dialog.MiddleSelectPop;
import com.siwei.tongche.dialog.NormalSelectPop;
import com.siwei.tongche.dialog.SelectMenuBean;
import com.siwei.tongche.events.EventTag;
import com.siwei.tongche.module.accident.AccidentReportListActivity;
import com.siwei.tongche.module.bind_unit.BindUserUnitActivity;
import com.siwei.tongche.module.carmanager.activity.AddCarActivity;
import com.siwei.tongche.module.gasstation.activity.AddGasListActivity;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.fragment.ExpressListFragment;
import com.siwei.tongche.module.main.fragment.MapInfoFragment;
import com.siwei.tongche.module.main.fragment.RentMapFragment;
import com.siwei.tongche.module.main.fragment.TaskFragment;
import com.siwei.tongche.module.main.ope.ScanResultOpe;
import com.siwei.tongche.module.scan.activity.UserInfoActivity;
import com.siwei.tongche.module.scan.fragment.TicketFrag;
import com.siwei.tongche.module.usercenter.activity.UserCenterActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.zxing.ZXingQRCodeUtils;
import com.sunday.busevent.SDBaseEvent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    MapInfoFragment mMapInfoFragment;
    TaskFragment mTaskFragment;
    ExpressListFragment mExpressListFragment;
    RentMapFragment mRentMapFragment;

    ArrayList<Fragment> mAllFragments = new ArrayList<>();
    private UserInfo mUserInfo;

    ArrayList<String> mTypeData = new ArrayList<String>();

    ArrayList<SelectMenuBean> mMenuData = new ArrayList<SelectMenuBean>();

    @Bind(R.id.layout_unbind)
    LinearLayout mLayout_unbind;//未绑定单位页面

    @Bind(R.id.bindUnit_hint)
    TextView mBindUnit_hint;//绑定单位提示  您尚未绑定单位,请扫描管理员二维码\n或者前往个人中心绑定

    @Bind(R.id.main_title)//首页标题
    TextView mTvMain_title;

    ScanResultOpe scanResultOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = CacheUtils.getLocalUserInfo();
        scanResultOpe= new ScanResultOpe();
        mUserInfo.setURoleCode(AppConstants.USER_TYPE.TYPE_DRIVER);
        mUserInfo.setUUnitRole(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER);//管理员
        CacheUtils.setLocalUserInfo(mUserInfo);
        getLocation();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLayout_unbind.setVisibility(View.GONE);
        checkUnit();
    }

    /**
     * 检查单位是否绑定
     */
    private void checkUnit() {
        if (mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)) {//司机
            if (MyRegexpUtils.isEmpty(mUserInfo.getUVehicleId())) {//没有绑定车辆
                mLayout_unbind.setVisibility(View.VISIBLE);
                mBindUnit_hint.setText("您尚未绑定车辆，请扫描管理员二维码\n或者前往个人中心绑定");
            } else {
                mLayout_unbind.setVisibility(View.GONE);
            }

            if (MyRegexpUtils.isEmpty(mUserInfo.getUBindSendUnitId())) {//没有绑定发货单位
                mLayout_unbind.setVisibility(View.VISIBLE);
                mBindUnit_hint.setText("您尚未发货单位，请扫描管理员二维码\n或者前往个人中心绑定");
            } else {
                mLayout_unbind.setVisibility(View.GONE);
            }
        }
        if (MyRegexpUtils.isEmpty(mUserInfo.getUUnitId())) {//没有绑定单位
            mLayout_unbind.setVisibility(View.VISIBLE);
            mBindUnit_hint.setText("您尚未绑定单位，请扫描管理员二维码\n或者前往个人中心绑定");
        } else {
            mLayout_unbind.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void initView() {
        switch (mUserInfo.getURoleCode()) {
            case AppConstants.USER_TYPE.TYPE_DRIVER://司机
                mExpressListFragment = new ExpressListFragment();
                mAllFragments.add(mExpressListFragment);
                mMenuData.add(new SelectMenuBean("扫一扫  ", R.drawable.menu_scan));
                mMenuData.add(new SelectMenuBean("故障报告", R.drawable.menu_accident_report));
                mMenuData.add(new SelectMenuBean("加点油吧", R.drawable.menu_gas_station));
                break;
            case AppConstants.USER_TYPE.TYPE_SENDER_UNIT://发货单位工作人员
                mMapInfoFragment = new MapInfoFragment();
                mTaskFragment = new TaskFragment();
                mAllFragments.add(mTaskFragment);
                mAllFragments.add(mMapInfoFragment);
                mTypeData.add("任务");
                mTypeData.add("地图");
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                break;
            case AppConstants.USER_TYPE.TYPE_CONSIGNEE_UNIT://收货单位工作人员
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                //管理员
                if (mUserInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_CREATOR) || mUserInfo.getUUnitRole().equals(AppConstants.USER_UNIT_ROLE.ROLE_MANAGER)) {
                    //是管理员可以发送任务
                    mMenuData.add(new SelectMenuBean("新建任务", R.drawable.menu_scan));
                    mTypeData.add("地图");
                    mTypeData.add("任务");
                    //
                    mMapInfoFragment = new MapInfoFragment();
                    mTaskFragment = new TaskFragment();
                    mAllFragments.add(mMapInfoFragment);
                    mAllFragments.add(mTaskFragment);
                } else {
                    mTypeData.add("小票");
                    mTypeData.add("任务");
                    mExpressListFragment = new ExpressListFragment();
                    mTaskFragment = new TaskFragment();
                    mAllFragments.add(mExpressListFragment);
                    mAllFragments.add(mTaskFragment);
                }
                break;
            case AppConstants.USER_TYPE.TYPE_RENT_UNIT://租赁公司工作人员
                mRentMapFragment = new RentMapFragment();
                mAllFragments.add(mRentMapFragment);
                mMenuData.add(new SelectMenuBean("扫一扫", R.drawable.menu_scan));
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mAllFragments) {
            fragmentTransaction.add(R.id.forwarding_fragment_container, fragment);
        }
        fragmentTransaction.commit();
        toggleFragment(0);
    }

    private void toggleFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mAllFragments) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.show(mAllFragments.get(index));
        fragmentTransaction.commit();

        //设置首页标题
        if(mTypeData!=null&&mTypeData.size()>0){
            mTvMain_title.setText(mTypeData.get(index));
        }
    }

    @OnClick({R.id.main_title, R.id.main_title_more, R.id.main_title_user_center,
            R.id.bindUnit_scan, R.id.bindUnit_userCenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_title:
                showMiddleDialog(view);
                break;
            case R.id.main_title_more:
                showMenu(view);
                break;
            case R.id.main_title_user_center:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.bindUnit_scan://扫描绑定单位
                ZXingQRCodeUtils.startScan(MainActivity.this);
                break;
            case R.id.bindUnit_userCenter://个人中心绑定单位
                if (MyRegexpUtils.isEmpty(mUserInfo.getUUnitId())) {//没有绑定单位
                    startActivity(new Intent(this, BindUserUnitActivity.class).putExtra(BindUserUnitActivity.KEY_UNIT,BindUserUnitActivity.MY_UNIT));
                } else if (MyRegexpUtils.isEmpty(mUserInfo.getUVehicleId())) {//没有绑定车辆
                    startActivity(new Intent(this, AddCarActivity.class));
                } else if (MyRegexpUtils.isEmpty(mUserInfo.getUBindSendUnitId())) {//没有绑定发货单位
                    startActivity(new Intent(this, BindUserUnitActivity.class).putExtra(BindUserUnitActivity.KEY_UNIT,BindUserUnitActivity.SEND_UNIT));
                }
                break;
        }
    }

    private void showMenu(View view) {
        NormalSelectPop normalSelectPop = new NormalSelectPop(this, mMenuData, new NormalSelectPop.OnItemClickListener() {
            @Override
            public void itemClick(String title, int position) {
                if (title.equals("新建任务")) {//新建任务
                    startActivity(new Intent(MainActivity.this, BuildTaskActivity.class));
                    return;
                }
                switch (position) {
                    case 0://扫一扫
                        ZXingQRCodeUtils.startScan(MainActivity.this);
                        break;
                    case 1://故障报告
                        startActivity(new Intent(MainActivity.this, AccidentReportListActivity.class));
                        break;
                    case 2://加点油吧
                        startActivity(new Intent(MainActivity.this, AddGasListActivity.class));
                        break;
                }

            }
        }, mMenuData.size());
        normalSelectPop.showBlowView(view, DensityUtil.dip2px(2));
    }

    /**
     * 选择类别
     */
    private void showMiddleDialog(View view) {
        if (mTypeData.size() == 0) {
            return;
        }
        MiddleSelectPop typePop = new MiddleSelectPop(this, mTypeData, new MiddleSelectPop.OnItemClickListener() {
            @Override
            public void itemClick(String title, int position) {
                toggleFragment(position);
            }
        }, 100, mTypeData.size());
        typePop.showBlowView(view, DensityUtil.dip2px(10));
    }


    /**
     * 开启百度地图定位
     */
    private void getLocation() {
        LocationManager.newInstance().getLocation(LocationManager.LocationMultiple, new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //Receive Location
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息

                MyLogUtils.e("BaiduLocationApiDem", MyFormatUtils.getDetailTimeByLongTime(System.currentTimeMillis() / 1000l + ""));
                MyLogUtils.e("BaiduLocationApiDem", sb.toString());
                MyLogUtils.e("BaiduLocationApiDem", " location.getSpeed()==" + location.getSpeed());

                CacheUtils.setLatitudes(location.getLatitude() + "");
                CacheUtils.setLongitude(location.getLongitude() + "");
                CacheUtils.setAddress(location.getAddrStr());

                EventBus.getDefault().post(new SDBaseEvent(null, EventTag.EVENT_REFRESH_LOCATION));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZXingQRCodeUtils.START_SCAN:
                //当普通用户扫描管理员二维码，页面出现管理员信息与【绑定单位】按钮，用户点击【绑定单位】，管理员即收到用户消息，点击【接受】即可。
                //当管理员扫描普通用户二维码，页面出现普通用户信息与【添加用户】按钮，用户点击【添加用户】，普通用户即收到添加消息，点击【接受】即可。
                //创建者扫描普通用户二维码，页面跳转后可指定该用户是否为管理员。
                //当管理员扫描驾驶员二维码，页面出现驾驶员信息与【添加】按钮，用户点击【添加】，驾驶员即收到添加消息，点击【接受】即可。
                if(data!=null && data.getStringExtra("result")!=null){


                    Fragment fragment = scanResultOpe.analyze(this,data.getStringExtra("result"));
                    if(fragment!=null){
                        //跳转到小票列表
                        if(fragment.getClass().getSimpleName().equals(TicketFrag.class.getSimpleName())){

                        }else{
                            Intent intent = new Intent(this,UserInfoActivity.class);
                            intent.putExtra("fragment",fragment.getClass().getSimpleName());
                            startActivity(intent);
                        }
                    }
                }
                break;
        }
    }
}
