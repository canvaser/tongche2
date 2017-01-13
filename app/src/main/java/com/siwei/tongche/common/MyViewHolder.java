package com.siwei.tongche.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.siwei.tongche.image.ImageLoaderManager;


/**
 * ViewHolder优化类
 * @author hjl
 *
 * 2015年12月31日下午6:40:51
 */
public class MyViewHolder {

	//SparseArray就是map，只是比map效率高一点
	private SparseArray<View> mViews;	//用来存放item中的各个控件的id及对象
	private int mPosition;
	private View mConvertView;

	private MyViewHolder(Context context,ViewGroup parent,int layoutId, int position) {
		mViews = new SparseArray<View>();
		mPosition = position;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	/**
	 * 获得ViewHolder
	 * @param context 上下文
	 * @param convertView item容器
	 * @param parent 根
	 * @param layoutId item布局文件id
	 * @param position 当前第position项item
	 * @return
	 */
	public static MyViewHolder getViewHolder(Context context,View convertView,ViewGroup parent,int layoutId,int position){
		if(convertView == null){
			return new MyViewHolder(context,parent, layoutId, position);
		}else{
			MyViewHolder viewHolder = (MyViewHolder) convertView.getTag();
			viewHolder.mPosition = position;
			return viewHolder;
		}
	}

	/**
	 * 获取 convertView
	 * @return convertView
	 */
	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 获得当前position
	 * @return
	 */
	public int getPosition() {
		return mPosition;
	}

	/**
	 * 通过控件id获得控件实例
	 * @param viewId 控件id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	/**
	 * 给TextView控件设置文本
	 * @param viewId TextView控件的id
	 * @param text	待设置的文本信息
	 * @return
	 */
	public MyViewHolder setText(int viewId,String text){
		TextView textView = getView(viewId);
		textView.setText(text);
		return this;
	}

	/**
	 * 给TextView控件设置文本
	 * @param viewId TextView控件的id
	 * @param text	待设置的文本信息
	 * @return
	 */
	public MyViewHolder setText(int viewId,String text,int txtColor){
		TextView textView = getView(viewId);
		textView.setText(text);
		textView.setTextColor(txtColor);
		return this;
	}
	
	/**
	 * 给TextView控件设置文本（带下划线效果）
	 * @param viewId TextView控件的id
	 * @param text	待设置的文本信息
	 * @return
	 */
	public MyViewHolder setTextHtml(int viewId,String text){
		TextView textView = getView(viewId);
		textView.setText(Html.fromHtml("<u>"+text+"</u>"));
		return this;
	}
	
	/**
	 * 给ImageView控件设置本地资源图片
	 * @param viewId ImageView控件的id
	 * @param resId 资源文件下的图片资源id
	 * @return
	 */
	public MyViewHolder setImageResource(int viewId,int resId){
		ImageView imageView = getView(viewId);
		imageView.setImageResource(resId);
		return this;
	}
	
	/**
	 * 给ImageView控件设置bitmap
	 * @param viewId ImageView控件的id
	 * @param bitmap bitmap对象
	 * @return
	 */
	public MyViewHolder setImageBitmap(int viewId,Bitmap bitmap){
		ImageView imageView = getView(viewId);
		imageView.setImageBitmap(bitmap);
		return this;
	}
	
	/**
	 * 给ImageView控件设置uri上的图片
	 * @param viewId ImageView控件的id
	 * @param uri uri
	 * @return
	 */
	public MyViewHolder setImageURI(int viewId,Uri uri){
		ImageView imageView = getView(viewId);
		imageView.setImageURI(uri);
		return this;
	}
	
	/**
	 * 给ImageView控件加载网上的图片
	 * @param viewId ImageView控件的id
	 * @param path 图片路径
	 * @return
	 */
	public MyViewHolder setImageLoader(int viewId,String path){
		ImageView imageView = getView(viewId);
		ImageLoaderManager.getImageLoader().displayImage(path, imageView);
		return this;
	}
	/**
	 * 给CheckBox设置是否选中
	 * @param viewId ImageView控件的id
	 * @param path 图片路径
	 * @return
	 */
	public MyViewHolder setCheckBoxChecked(int viewId,boolean isChecked){
		CheckBox checkBox = getView(viewId);
		checkBox.setChecked(isChecked);
		return this;
	}

	/**
	 * 给ImageView控件加载网上的图片
	 * @param viewId ImageView控件的id
	 * @param path 图片路径
	 * @return
	 */
	public MyViewHolder setHeaderImageLoader(int viewId,String path){
		ImageView imageView = getView(viewId);
		ImageLoaderManager.getHeaderImage(path, imageView);
		return this;
	}

	public void setChecked(int item_unit_checkbox, boolean selected) {
		CheckBox checkBox = getView(item_unit_checkbox);
		checkBox.setChecked(selected);
	}
}
