package com.siwei.tongche.module.income_setting.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.utils.MyToastUtils;


public class IncomeSettingView extends LinearLayout implements IncomeSettingItemView.OnDeleteItemCallBack {
	LinearLayout mIncomeSetting_layout;

	public IncomeSettingView(Context context)
	{
		super(context);
		init();
	}

	public IncomeSettingView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mIncomeSetting_layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_income_setting, null);
		mIncomeSetting_layout.findViewById(R.id.addIncomeItem).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				addItem();
			}
		});
		this.addView(mIncomeSetting_layout);
	}

	/**
	 * 添加单项
	 */
	private void addItem() {
		if(mIncomeSetting_layout.getChildCount()>=7){
			MyToastUtils.showToast("最多设置5个梯度");
			return;
		}
		IncomeSettingItemView itemView=new IncomeSettingItemView(getContext());
		itemView.setOnDeleteItemCallBack(this);
		mIncomeSetting_layout.addView(itemView);
	}

	@Override
	public void onDelete(IncomeSettingItemView view) {
		mIncomeSetting_layout.removeView(view);
	}
}
