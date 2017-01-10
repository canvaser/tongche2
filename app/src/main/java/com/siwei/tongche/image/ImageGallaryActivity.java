package com.siwei.tongche.image;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;


import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 浏览图片
 * @author HJL
 *
 */
public class ImageGallaryActivity extends BaseActivity {
	@Bind(R.id.viewPager)
	AlbumViewPager mViewPager;
	@Bind(R.id.index)
	TextView imgIndex;
	 
	private ArrayList<String> pics;
	private ArrayList<TouchImageViewFragment> allImages=new ArrayList<TouchImageViewFragment>();
	private int currentIndex;

	@Override
	public int getContentView() {
		return R.layout.activity_images_gallary;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		pics = getIntent().getStringArrayListExtra("pics");
		currentIndex = getIntent().getIntExtra("currentIndex", 0);
		if(pics==null||pics.size()==0){
			return;
		}
		for(String path:pics){
				TouchImageViewFragment touchImageViewFragment = TouchImageViewFragment.newInstance(path);
				allImages.add(touchImageViewFragment);
		}
		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return allImages==null?0:allImages.size();
			}
			
			@Override
			public Fragment getItem(int index) {
				return allImages.get(index);
			}
		});
		mViewPager.setCurrentItem(currentIndex);
		imgIndex.setText(currentIndex+1+"/"+pics.size());

		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				imgIndex.setText(index + 1 + "/" + pics.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	@Override
	protected void setTranslucentStatus(boolean on) {
		//重写此方法    引导页全屏显示
	}
	public AlbumViewPager getmViewPager() {
		return mViewPager;
	}
	
	@OnClick(R.id.iv_download)
	public void onClick(View view){
		allImages.get(mViewPager.getCurrentItem()).saveImageToGallery(this);
	}
}
