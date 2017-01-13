package com.siwei.tongche.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个通用的适配器 所有适配器继承该适配器   适配器
 * @param <T>
 */
public abstract  class MySwipeBaseAdapter<T> extends BaseSwipeAdapter
{

	protected List<T> mListModel = null;
	protected LayoutInflater mInflater = null;
	protected Context mContext = null;
	protected int mLayout_item;
	public MySwipeBaseAdapter(List<T> listModel, Context context,int layout_item)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		}
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		mLayout_item=layout_item;
	}

	public void setData(List<T> listModel)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		} else
		{
			this.mListModel = new ArrayList<T>();
		}
	}

	public List<T> getData()
	{
		return mListModel;
	}

	public void updateListViewData(List<T> listModel)
	{
		setData(listModel);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (mListModel != null)
		{
 			return mListModel.size();
		} else
		{
			return 10;
		}
	}

	@Override
	public T getItem(int position)
	{
		if (mListModel != null && mListModel.size() > 0 && mListModel.size() > position)
		{
			return mListModel.get(position);
		} else
		{
			return null;
		}
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}


	@Override
	public View generateView(int position, ViewGroup parent) {
		  return mInflater.inflate(mLayout_item,parent,false);
	}

	@Override
	public void fillValues(int position, View convertView) {
		fillValues(position,convertView,getItem(position));
	}

	public abstract  void fillValues(int position, View convertView, final T model);

}
