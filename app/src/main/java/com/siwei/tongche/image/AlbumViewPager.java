package com.siwei.tongche.image;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/** 
 * @ClassName: AlbumViewPager 
 * @Description:  自定义viewpager  优化了事件拦截
 * @author LinJ
 * @date 2015-1-9 下午5:33:33 
 *  
 */
public class AlbumViewPager extends ViewPager implements MatrixImageView.OnMovingListener {
	public final static String TAG="AlbumViewPager";

	 	/**  当前子控件是否处理拖动状态  */ 
	private boolean mChildIsBeingDragged=false;

	 public AlbumViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(mChildIsBeingDragged)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public void startDrag() {
		// TODO Auto-generated method stub
		mChildIsBeingDragged=true;
	}

	@Override
	public void stopDrag() {
		// TODO Auto-generated method stub
		mChildIsBeingDragged=false;
	}
}
