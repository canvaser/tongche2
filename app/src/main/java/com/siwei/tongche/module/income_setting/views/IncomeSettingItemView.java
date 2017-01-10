package com.siwei.tongche.module.income_setting.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siwei.tongche.R;


public class IncomeSettingItemView extends LinearLayout
{
	LinearLayout mIncomeSetting_item_layout;

	EditText mIncomeSetting_price;
	EditText mIncomeSetting_distance;

	public IncomeSettingItemView(Context context)
	{
		super(context);
		init();
	}

	public IncomeSettingItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mIncomeSetting_item_layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_income_setting_item, null);
		mIncomeSetting_item_layout.findViewById(R.id.incomeSetting_delete).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteItem();
			}
		});
		mIncomeSetting_price = (EditText) mIncomeSetting_item_layout.findViewById(R.id.incomeSetting_price);
		mIncomeSetting_distance = (EditText) mIncomeSetting_item_layout.findViewById(R.id.incomeSetting_distance);
		this.addView(mIncomeSetting_item_layout);
	}

	private void deleteItem() {
		if(mCallBack!=null){
			mCallBack.onDelete(this);
		}
	}

	OnDeleteItemCallBack mCallBack;
	public void setOnDeleteItemCallBack(OnDeleteItemCallBack callback){
		mCallBack=callback;
	}
	interface  OnDeleteItemCallBack{
		public void onDelete(IncomeSettingItemView view);
	}


}
