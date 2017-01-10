package com.siwei.tongche.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个通用的适配器 所有适配器继承该适配器   适配器
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter
{

	protected List<T> mListModel = null;
	protected LayoutInflater mInflater = null;
	protected Context mContext = null;

	public MyBaseAdapter(List<T> listModel, Context context)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		}
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return getItemView(position, convertView, parent, getItem(position));
	}

	public abstract View getItemView(int position, View convertView, ViewGroup parent, final T model);

}
