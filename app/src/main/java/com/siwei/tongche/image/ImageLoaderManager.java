package com.siwei.tongche.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.siwei.tongche.R;
import com.siwei.tongche.common.MyApplication;

public class ImageLoaderManager
{

	private static ImageLoader mImageLoader = ImageLoader.getInstance();
	private static DisplayImageOptions options = null;
	private static DisplayImageOptions headerOptions = null;

	private ImageLoaderManager()
	{
	}

	public static ImageLoader getImageLoader()
	{
		initImageLoader();
		return mImageLoader;
	}

	public static DisplayImageOptions getDefaultOptions()
	{
		if (options == null)
		{
			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.load_failed)
					.showImageOnFail(R.drawable.load_failed)
					.showImageOnLoading(R.drawable.loading)
					.resetViewBeforeLoading(true)
					.cacheOnDisc(true)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(300))
					.build();
		}
		return options;
	}

	private static DisplayImageOptions getHeaderOptions()
	{
		if (headerOptions == null)
		{
			headerOptions = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.default_header)
					.showImageOnFail(R.drawable.default_header)
//					.showImageOnLoading(R.drawable.default_header)
					.resetViewBeforeLoading(false)
					.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
// 					.displayer(new FadeInBitmapDisplayer(100))  //渐变显示
					.displayer(new SimpleBitmapDisplayer())
					.build();
		}
		return headerOptions;
	}

	/**
	 * 加载头像
	 * @param uri
	 * @param imageView
	 */
	public static void getHeaderImage(String uri,ImageView imageView)
	{
		getImageLoader().displayImage(uri, imageView,getHeaderOptions());
	}

	public static void setOptions(DisplayImageOptions options)
	{
		ImageLoaderManager.options = options;
	}

	private static void initImageLoader()
	{
		if (!mImageLoader.isInited())
		{
			ImageLoaderConfiguration config = new ImageLoaderConfiguration
					.Builder(MyApplication.getMyApplication())
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
					.defaultDisplayImageOptions(getDefaultOptions()).build();
			mImageLoader.init(config);
		}
	}

}
