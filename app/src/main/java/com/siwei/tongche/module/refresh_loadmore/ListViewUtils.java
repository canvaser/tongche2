package com.siwei.tongche.module.refresh_loadmore;


import android.content.Context;
import android.widget.AbsListView;


import com.siwei.tongche.R;
import com.siwei.tongche.utils.DensityUtil;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class ListViewUtils
{

	/**
	 * 设置加载更多的参数
	 * @param context
	 * @param loadMore
	 */
	public static void setLoadMoreParams(Context context,LoadMoreListViewContainer loadMore) {
		// 创建
		MyLoadMoreFooterView footerView = new MyLoadMoreFooterView(context);
		// 根据实际情况设置样式
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(-2, LocalDisplay.dp2px(80));
		footerView.setLayoutParams(lp);
		// 设置
		loadMore.setLoadMoreView(footerView);
		loadMore.setLoadMoreUIHandler(footerView);
		loadMore.loadMoreFinish(true, true);
	}

	/**
	 * 设置下拉刷新的参数
	 * @param context
	 * @param mPtrFrame
	 */
	public static void setRefreshParams(Context context,PtrFrameLayout mPtrFrame){
		final MaterialHeader header = new MaterialHeader(context);
		int[] colors = context.getResources().getIntArray(R.array.ptr_frame_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, DensityUtil.dip2px( 15), 0, DensityUtil.dip2px( 10));
		header.setPtrFrameLayout(mPtrFrame);
		mPtrFrame.setLoadingMinTime(500);
		mPtrFrame.setDurationToCloseHeader(200);
		mPtrFrame.setPinContent(true);
		mPtrFrame.setHeaderView(header);
		mPtrFrame.addPtrUIHandler(header);
		mPtrFrame.disableWhenHorizontalMove(true);
	}


}
