package com.siwei.tongche.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siwei.tongche.R;


public class SDSimpleSetItemView extends LinearLayout
{

	private ImageView mImgTitle = null;
	private TextView mTxtTitle = null;
	private TextView mTxtTitleSub = null;
	
	private View mView = null;

	public ImageView getmImgTitle()
	{
		return mImgTitle;
	}

	public void moveImagTitle(){
		mImgTitle.setVisibility(GONE);
	}

	public void setmImgTitle(ImageView mImgTitle)
	{
		this.mImgTitle = mImgTitle;
	}

	public TextView getmTxtTitle()
	{
		return mTxtTitle;
	}

	public void setmTxtTitle(TextView mTxtTitle)
	{
		this.mTxtTitle = mTxtTitle;
	}

	public TextView getmTxtTitleSub()
	{
		return mTxtTitleSub;
	}

	public void setmTxtTitleSub(TextView mTxtTitleSub)
	{
		this.mTxtTitleSub = mTxtTitleSub;
	}

	public SDSimpleSetItemView(Context context)
	{
		super(context);
		init();
	}

	public SDSimpleSetItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mView = LayoutInflater.from(getContext()).inflate(R.layout.view_simple_set_item, null);
		mImgTitle = (ImageView) mView.findViewById(R.id.item_left_img);
		mTxtTitle = (TextView) mView.findViewById(R.id.item_tv_content);
		mTxtTitleSub = (TextView) mView.findViewById(R.id.item_tv_right_content);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		this.addView(mView, params);
	}

	/**
	 * 清除右边箭头
	 */
	public void setClearRightArrow(){
		mTxtTitleSub.setCompoundDrawables(null, null, null, null);
	}

	public void setTitleText(String text)
	{
		if (text != null)
		{
			this.mTxtTitle.setText(text);
		}
	}

	public void setTitleSubText(String text)
	{
		if (text != null)
		{
			this.mTxtTitleSub.setText(text);
		}
	}

	public void setTitleImage(int imgId)
	{	
		if(imgId==0){
			this.mImgTitle.setVisibility(View.GONE);
			return;
		}
		this.mImgTitle.setImageResource(imgId);
	}

	
	public void setBackgroundImage(int resId)
	{
		mView.setBackgroundResource(resId);
	}

}
