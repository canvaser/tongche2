package com.siwei.tongche.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.siwei.tongche.R;

import java.util.ArrayList;

public class PointWidget extends LinearLayout {

	ArrayList<View> pointList;
	ImageView point;
	LayoutParams lp;
	Context context;
	int left=10, top, right=10, bottom;// -2为warp

	public PointWidget(Context context) {
		super(context);
		this.context = context;
		init();
		setOrientation(LinearLayout.HORIZONTAL);
	}

	public PointWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		setOrientation(LinearLayout.HORIZONTAL);
	}

	private void init() {
		pointList = new ArrayList<View>();
	}

	public void setPointCount(int PointCount) {
		pointList.clear();
		removeAllViews();
		lp = (null == lp ? new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) : lp);
		for (int i = 0; i < PointCount; i++) {
			point = new ImageView(context);
			point.setImageResource(R.drawable.selector_main_ad);
			point.setPadding(left, top, right, bottom);
			point.setEnabled(false);
			point.setLayoutParams(lp);
			if (pointList.size() == 0) {
				point.setEnabled(true);
			} else {
				pointList.get(0).setEnabled(true);
			}
			pointList.add(point);
			addView(point);
		}
	}

	public void setPoint(int i) {
		for (int a = 0; a < pointList.size(); a++) {
			if (a == i) {
				pointList.get(i).setEnabled(true);
				continue;
			}
			pointList.get(a).setEnabled(false);
		}

	}

}
