package com.siwei.tongche.module.carmanager.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.carmanager.bean.CarBean;
import com.siwei.tongche.module.carmanager.ope.CarManagerDAOpe;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.module.usercenter.bean.DriverBean;
import com.siwei.tongche.utils.CacheUtils;

import java.util.ArrayList;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 车辆管理
 */
public class CarManagerActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView mListviewCar;
    MyBaseAdapter<CarBean> mCarAdapter;

    ArrayList<CarBean> cars;
    ArrayList<DriverBean> drivers;

    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMoreListViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("车辆管理");
        setRight(R.drawable.title_add,"");
        init();
    }


    private void init(){
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(this, mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListviewCar, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getNetCarBaseInfo();
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
    }

    public void getNetCarBaseInfo(){
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UserID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_UNIT_VEHICLE_LIST, params, MyHttpUtil.ReturnType.ARRAY, CarBean.class, "",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                ArrayList<CarBean> list = (ArrayList<CarBean>) object;
                cars = list;
                getNetDriverBaseInfo();
            }
        });
    }


    public void getNetDriverBaseInfo(){
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UserID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_UNIT_DRIVER_LIST, params, MyHttpUtil.ReturnType.ARRAY, DriverBean.class, "",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                ArrayList<DriverBean> list = (ArrayList<DriverBean>) object;
                drivers = list;
                initView();
            }
        });
    }

    private void initView() {
        mCarAdapter=new MyBaseAdapter<CarBean>(cars,this) {
            @Override
            public View getItemView(final int position, View convertView, ViewGroup parent, final CarBean model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(CarManagerActivity.this,convertView,parent,R.layout.item_car_manager,position);
                viewHolder.getView(R.id.tv_driverman).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDriverList(cars.get(position));
                    }
                });
                viewHolder.setText(R.id.tv_id,"车牌:"+model.getVPlateNumber());
                viewHolder.setText(R.id.tv_num,"车号:"+model.getV_NO());
                viewHolder.setText(R.id.tv_weight,"规格:"+model.getVGgxh());
                viewHolder.setText(R.id.tv_info,"品牌:"+model.getVBrand());
                viewHolder.setText(R.id.tv_driverman,model.getDriverListStr());
                return viewHolder.getConvertView();
            }
        };
        mListviewCar.setAdapter(mCarAdapter);

        mListviewCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CarManagerActivity.this,CarDetailActivity.class);
                intent.putExtra("data",cars.get(i));
                startActivity(intent);
            }
        });
        mPtrFrame.refreshComplete();
    }

    //弹出的驾驶员列表多选
    public void showDriverList(final CarBean carBean){
        final Dialog dialog = new Dialog(this);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().getAttributes().alpha=1;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_driver_list,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        ListView listView = (ListView) view.findViewById(R.id.ll_driverlist);
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CarManagerDAOpe().getSelect(carBean,drivers);
                dialog.dismiss();
            }
        });
        new CarManagerDAOpe().initSelect(carBean,drivers);
        listView.setAdapter(new MyBaseAdapter<DriverBean>(drivers,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, DriverBean model) {
                final MyViewHolder viewHolder=MyViewHolder.getViewHolder(CarManagerActivity.this,convertView,parent,R.layout.list_driver,position);
                viewHolder.getView(R.id.ll_root).setOnClickListener(listener);
                viewHolder.getView(R.id.ll_root).setTag(R.id.data,viewHolder);
                viewHolder.getView(R.id.ll_root).setTag(R.id.position,position);
                viewHolder.setText(R.id.tv_name,model.getUName());
                return viewHolder.getConvertView();
            }
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.data);
                    AppCompatCheckBox checkBox = viewHolder.getView(R.id.check);
                    checkBox.setChecked(!checkBox.isChecked());
                }
            };

        });
    }

    @Override
    public void onRightText(View view) {
        super.onRightText(view);
        startActivity(new Intent(this,AddCarActivity.class));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }
}
