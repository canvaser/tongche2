package com.siwei.tongche.module.carmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.module.usermanager.UserDetailInfoActivity;

import butterknife.Bind;

/**
 * 车辆管理
 */
public class CarManagerActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView mListviewCar;
    MyBaseAdapter<String> mCarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("车辆管理");
        setRight(R.drawable.title_add,"");
        initView();
    }

    private void initView() {
        mCarAdapter=new MyBaseAdapter<String>(null,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=new MyViewHolder(CarManagerActivity.this,parent,R.layout.item_car_manager,position);
                return viewHolder.getConvertView();
            }
        };
        mListviewCar.setAdapter(mCarAdapter);

        mListviewCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(CarManagerActivity.this,UserDetailInfoActivity.class));
            }
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
