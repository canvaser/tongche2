package com.siwei.tongche.module.login.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.login.adapter.ViewPagerAdapter;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.views.PointWidget;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 引导页面
 * @author hjl
 *
 * 2016年1月7日下午4:51:04
 */
public class GuideActivity extends BaseActivity {
	@Bind(R.id.btn_enter)
	Button mBtnEnter;//进入按钮

	@Bind(R.id.viewPager)
	ViewPager mViewPager;//引导页

	@Bind(R.id.pointWidget)
	PointWidget mPointWidget;//点

	private int[] resIds=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initView();
	}
	@Override
	protected void setTranslucentStatus(boolean on) {
		//重写此方法    引导页全屏显示
	}
	@Override
	public int getContentView() {
		return R.layout.activity_guide;
	}
	private void initView() {
		final ArrayList<View> allImages=new ArrayList<View>();
		for(int i=0;i<resIds.length;i++){
			ImageView imageView=new ImageView(this);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageResource(resIds[i]);
			allImages.add(imageView);
		}
		mViewPager.setAdapter(new ViewPagerAdapter(allImages));

		mPointWidget.setPointCount(resIds.length);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if(position==resIds.length-1){
					mBtnEnter.setVisibility(View.VISIBLE);
				}else{
					mBtnEnter.setVisibility(View.INVISIBLE);
				}
				mPointWidget.setPoint(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}



	@OnClick(R.id.btn_enter)
	public void onClick(View view){
		switch (view.getId()){
			case R.id.btn_enter:
				//进入
				startActivity(new Intent(this, LoginActivity.class));
				finish();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CacheUtils.saveIsFirst(getApplicationContext(),false);
	}
}
