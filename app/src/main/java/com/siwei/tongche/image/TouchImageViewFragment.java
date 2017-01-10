package com.siwei.tongche.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.siwei.tongche.R;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MyToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可缩放的 图片
 * 
 * @author HJL
 * 
 */
public class TouchImageViewFragment extends Fragment {
	private ExecutorService cachedThreadPool;//线程池
	MatrixImageView zoom_imageView;
	/** Called when the activity is first created. */
	private String path;
	private Bitmap bitmap;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_touch_imageview, container,false);
		initView(view);
		return view;
	}
	
	public static TouchImageViewFragment  newInstance(String img_path){
		TouchImageViewFragment f=new TouchImageViewFragment();
		Bundle args=new Bundle();
		args.putString("img_path", img_path);
		f.setArguments(args);
		return f;
	}
	
	private void initView(View view) {
		zoom_imageView = (MatrixImageView) view.findViewById(R.id.zoom_imageView);
		zoom_imageView.setOnMovingListener(((ImageGallaryActivity) getActivity()).getmViewPager());
		ImageLoaderManager.getImageLoader().displayImage(path,
				zoom_imageView, new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						MyToastUtils.showToast("获取图片失败");
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (loadedImage != null) {
							bitmap=ImageUtils.resizeImageByWidth(loadedImage, DensityUtil.getScreenWidth());
							zoom_imageView.setImageBitmap(bitmap);
						}else{
							MyToastUtils.showToast("获取图片失败");
						}
						
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						MyToastUtils.showToast("获取图片失败");
					}
				});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		path = getArguments().getString("img_path");
		cachedThreadPool = Executors.newCachedThreadPool();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
//		if(bitmap!=null){
//			bitmap.recycle();//释放资源
//			Log.e("=========TouchImageViewActivity=====", "======释放图片资源=======");
//		}
	}
	
	/**
	 * 保存照片到 系统相册
	 * @param context
	 */
	public  void saveImageToGallery(final Context context) {
		Toast.makeText(context.getApplicationContext(), "图片保存中...",Toast.LENGTH_SHORT).show();
	    // 首先保存图片
	    final File appDir = new File(Environment.getExternalStorageDirectory(), "mimigardenSave");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String fileName = System.currentTimeMillis() + ".jpg";
				File file = new File(appDir, fileName);
				try {
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();
					MyToastUtils.showToast("成功保存，路径"+appDir.getAbsolutePath()+"/"+fileName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 其次把文件插入到系统图库
				try {
					MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				// 最后通知图库更新
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
			}
	   	});
	}
}
