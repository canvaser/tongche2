package com.siwei.tongche.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {
	public static  Bitmap decodeUriAsBitmap(Context context,Uri uri) {
		 Bitmap bitmap = null;
		 BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
	        newOpts.inJustDecodeBounds = true; 
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri),null,newOpts);
			 newOpts.inJustDecodeBounds = false; 
			 
			 
			 newOpts.inSampleSize = 4;//设置缩放比例  
			 bitmap = BitmapFactory.decodeStream(context.getContentResolver()
						.openInputStream(uri),null,newOpts);
			 
			 //MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
		
			// sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	 		/**
	      * 以最省内存的方式读取本地资源的图片
	      * @param context
	     * @param resId
	     * @return
	     */  
	    public static Bitmap readBitMap(Context context, int resId){  
	        BitmapFactory.Options opt = new BitmapFactory.Options();  
	        opt.inPreferredConfig = Bitmap.Config.RGB_565;   
	        opt.inPurgeable = true;  
	        opt.inInputShareable = true;  
	          //获取资源图片  
	       InputStream is = context.getResources().openRawResource(resId);  
	       return BitmapFactory.decodeStream(is,null,opt);  
	   }
	    
	    public  static int calculateInSampleSize(BitmapFactory.Options options,  
	            int reqWidth, int reqHeight) {  
	        // Raw height and width of image  
	        final int height = options.outHeight;  
	        final int width = options.outWidth;  
	        int inSampleSize = 1;  
	  
	        if (height > reqHeight || width > reqWidth) {  
	  
	            // Calculate ratios of height and width to requested height and  
	            // width  
	            final int heightRatio = Math.round((float) height  
	                    / (float) reqHeight);  
	            final int widthRatio = Math.round((float) width / (float) reqWidth);  
	  
	            // Choose the smallest ratio as inSampleSize value, this will  
	            // guarantee  
	            // a final image with both dimensions larger than or equal to the  
	            // requested height and width.  
	            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;  
	        }  
	  
	        return inSampleSize;  
	    } 
	    
	    
	    /**
	     * 将Uri转为bitmap
	     * @param context
	     * @param uri
	     * @return
	     */
	    public static  Bitmap decodeUriAsBitmap2(Context context,Uri uri) {
			  Bitmap bitmap = null;
			  int degree=readPictureDegree(getRealFilePath(context,uri));
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				//开始读入图片，此时把options.inJustDecodeBounds 设回true了
				newOpts.inJustDecodeBounds = true;
				try {
					bitmap = BitmapFactory.decodeStream(context.getContentResolver()
							.openInputStream(uri), null, newOpts);
					newOpts.inJustDecodeBounds = false;
					newOpts.inSampleSize = getSimpleSize(newOpts);//设置缩放比例
					bitmap = BitmapFactory.decodeStream(context.getContentResolver()
							.openInputStream(uri), null, newOpts);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
					MyLogUtils.e("=======", "没找到");
				}


			return rotaingImageView(degree, bitmap);
		}
	    
	    /* 旋转图片
		* @param angle
		* @param bitmap
		* @return Bitmap
		*/
		public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
			if(angle==0){
				return bitmap;
			}
			//旋转图片 动作
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			//创建新的图片
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			return resizedBitmap;
		}


		/**
		 * change image size: width and height
		 *
		 * @param options
		 * @return
		 */
		private static int getSimpleSize(BitmapFactory.Options options)
		{
			int w = options.outWidth;
			int h = options.outHeight;
			float hh = 800f;
			float ww = 480f;
			int scale = 1;
			if (w > h && w > ww)
			{
				scale = (int) (options.outWidth / ww);
			} else if (w < h && h > hh)
			{
				scale = (int) (options.outHeight / hh);
			}
			if (scale <= 0)
				scale = 1;
			return scale;
		}
		/**
		 * 读取图片属性：旋转的角度
		 * @param path 图片绝对路径
		 * @return degree旋转的角度
		 */
		public static int readPictureDegree(String path) {
			int degree  = 0;
			try {
				ExifInterface exifInterface = new ExifInterface(path);
				int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return degree;
		}

		/**
		 * Try to return the absolute file path from the given Uri
		 *
		 * @param context
		 * @param uri
		 * @return the file path or null
		 */
		public static String getRealFilePath( final Context context, final Uri uri ) {
			if ( null == uri ) return null;
			final String scheme = uri.getScheme();
			String data = null;
			if ( scheme == null )
				data = uri.getPath();
			else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
				data = uri.getPath();
			} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
				Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
				if ( null != cursor ) {
					if ( cursor.moveToFirst() ) {
						int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
						if ( index > -1 ) {
							data = cursor.getString( index );
						}
					}
					cursor.close();
				}
			}
			return data;
		}


	// 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
	private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
											int dstHeight) {
		Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
		if (src != dst) { // 如果没有缩放，那么不回收
			src.recycle(); // 释放Bitmap的native像素数组
		}
		return dst;
	}

	// 从Resources中加载图片
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
														 int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight); // 计算inSampleSize
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
		return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
	}

	// 从sd卡上加载图片
	public static Bitmap decodeSampledBitmapFromFd(String pathName,
												   int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeFile(pathName, options);
		return createScaleBitmap(src, reqWidth, reqHeight);
	}
}
