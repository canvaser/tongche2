package com.siwei.tongche.module.gasstation.ope;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class AddGasListUIOpe extends BaseUIOpe{

    @Bind(R.id.listView)
    ListView mAddGasList;

    MyBaseAdapter<String> mAddGasAdapter;



    public AddGasListUIOpe(Context context, View containerView) {
        super(context, containerView);
        initView();
    }


    private void initView() {
        mAddGasAdapter=new MyBaseAdapter<String>(null,context) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=new MyViewHolder(context,parent,R.layout.item_add_gas,position);
                return viewHolder.getConvertView();
            }
        };

        mAddGasList.setAdapter(mAddGasAdapter);
    }

}
