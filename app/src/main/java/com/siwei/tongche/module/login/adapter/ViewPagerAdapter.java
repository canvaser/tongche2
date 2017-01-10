package com.siwei.tongche.module.login.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

	ArrayList<View> views;
	public ViewPagerAdapter(ArrayList<View> views){
		this.views = views;
	}
	
	/**获得当前界面数 */
	@Override
	public int getCount() {
		return views==null?0:views.size();
	}

	/**初始化position位置的界面 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager)container).addView(views.get(position));
		return views.get(position);
	}
	
	/**判断是否由对象生成界面*/
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 ==arg1;
	}

	/**销毁position位置的界面 */
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(views.get(position));
	}
}
