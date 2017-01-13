package com.siwei.tongche.module.accident;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.module.main.activity.MapDetailInfoActivity;

import butterknife.Bind;

/**
 * 故障报告列表
 */
public class AccidentReportListActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.listView)
    ListView mAccidentList;

    MyBaseAdapter<String>  mAccidentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("故障报告");
        setRight(R.drawable.title_add,"");
        initView();
    }

    private void initView() {
        mAccidentAdapter=new MyBaseAdapter<String>(null,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(AccidentReportListActivity.this,convertView,parent,R.layout.item_accident_report,position);
                viewHolder.getView(R.id.iv_tomap).setOnClickListener(AccidentReportListActivity.this);
                viewHolder.getView(R.id.iv_tomap).setTag(R.id.position,position);
                return viewHolder.getConvertView();
            }

        };
        mAccidentList.setAdapter(mAccidentAdapter);
    }

    @Override
    public void onRightText(View view) {
        super.onRightText(view);
        startActivity(new Intent(this,AccidentReportActivity.class));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_tomap:
                startActivity(new Intent(this,MapDetailInfoActivity.class));
                break;
        }
    }
}
