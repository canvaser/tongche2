package com.siwei.tongche.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;


import com.siwei.tongche.http.MyUrls;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


/**
 * 图片文件操作类
 * 
 * @author Administrator
 *
 */
public class ImageUploadUtils {
    //头像、驾驶证、车辆照片、行驶证照片、故障照片、营业执照、税务登记证、组织机构代码证、建议反馈
	final public static int TYPE_HEADER=1;
	final public static int TYPE_DRIVING_LICENCE=2;
	final public static int TYPE_CAR=3;
	final public static int TYPE_VEHICLE_LICENCE=4;
	final public static int TYPE_ACCIDENT=5;
	final public static int TYPE_BUSINESS_LICENCE=6;
	final public static int TYPE_TAX_REGISTERATION=7;
	final public static int TYPE_ORGANIZATION_CODE=8;
	final public static int TYPE_FEEDBACK=9;


	private static Handler handler = new Handler();
	public static Uri imageUri = Uri.fromFile(new File(Environment
			.getExternalStorageDirectory() + "/temp.jpg"));

	/**
	 * 图片文件上传
	 * 
	 */
	public static void fileUpload(Activity activity, Uri uri, int TYPE,FilePostCallback httpResponse) {
		File file = new File(GetPathFromUri4kitkat.getPath(activity, uri));
//		file	File	文件
//		user_id	String	用户id
//		token	String	令牌
		if (file.exists() && file.length() > 0) {// 如果文件存在及有效
			HashMap<String, String> args=new HashMap<String, String>();
//			USERID(用户ID)、TYPE（图片类型 1、头像 2 驾驶证图片...）
			args.put("USERID", CacheUtils.getLocalUserInfo().getUID());
			args.put("TYPE",TYPE+"");
			uploadFile(MyUrls.POST_IMG, CompressFile(file), "FILE", args, httpResponse);
		}else{
			MyLogUtils.e("上传图片", "文件不存在");
		}
	}
	
	/**
	 * 压缩 图片文件 
	 * @param picFile  原始文件
	 * @return
	 */
	public static File CompressFile(File picFile){
			BitmapFactory.Options options = new BitmapFactory.Options();  
	        options.inJustDecodeBounds = true;  
	        BitmapFactory.decodeFile(picFile.getPath(), options);  
	        // Calculate inSampleSize  
	        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, 460, 800);
	        MyLogUtils.e("=========inSampleSize===========",  options.inSampleSize+"");
	        // Decode bitmap with inSampleSize set  
	        options.inJustDecodeBounds = false;  
	        Bitmap bm = BitmapFactory.decodeFile(picFile.getPath(), options);  
	        if(bm == null){  
	        	return picFile;
	        } 
//	        //部分手机拍照后会颠倒
 	        int degree = BitmapUtils.readPictureDegree(picFile.getPath());
 	        bm=BitmapUtils.rotaingImageView(degree, bm);
	        try {
	        	int compress=compressImage(bm);
	        	MyLogUtils.e("compress==="+compress);
				bm.compress(Bitmap.CompressFormat.JPEG,compress, new FileOutputStream(picFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
	        return picFile;
	        
	} 
	
	
	/**
	 * 获取压缩比例
	 * @param image   image
	 * @return
	 */
	public static int compressImage(Bitmap image) {
		if(image==null) return 100;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		if(baos.toByteArray().length/1024<1024){
			//当图片小于1024kb时，直接上传
			return 100;
		}
		int options = 100;
		MyLogUtils.e("压缩前图片大小", baos.toByteArray().length / 1024+"");
		while ( baos.toByteArray().length / 1024>200) {	//循环判断如果压缩后图片是否大于200kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			MyLogUtils.e("压缩后图片大小", baos.toByteArray().length / 1024+"");
			if(options==0){
				return  0;
			}
		}
		return options;
	}

	/**
	 * 上传文件或图片
	 * 
	 * @param file
	 * @return
	 */
	public static void uploadFile(final String requestURL, final File file,
			final String fileKey, final HashMap<String, String> args,
			final FilePostCallback httpResponse) {
		final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		final String PREFIX = "--";
		final String LINE_END = "\r\n";
		final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		new Thread() {
			public void run() {

				try {
					URL url = new URL(requestURL);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					int TIME_OUT = 10000; // 超时时间
					conn.setReadTimeout(TIME_OUT);
					conn.setConnectTimeout(TIME_OUT);
					conn.setDoInput(true); // 允许输入流
					conn.setDoOutput(true); // 允许输出流
					conn.setUseCaches(false); // 不允许使用缓存
					conn.setRequestMethod("POST"); // 请求方式
					conn.setRequestProperty("Charset", "utf-8"); // 设置编码
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Content-Type", CONTENT_TYPE
							+ ";boundary=" + BOUNDARY);

					// 首先组拼文本类型的参数
					StringBuilder sb1 = new StringBuilder();

					Iterator<Map.Entry<String, String>> it = args.entrySet()
							.iterator();
					while (it.hasNext()) {// 遍历添加参数
						Map.Entry<String, String> entry = it.next();
						sb1.append(PREFIX);
						sb1.append(BOUNDARY);
						sb1.append(LINE_END);
						sb1.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"" + LINE_END);
						sb1.append("Content-Type: text/plain; charset="
								+ "utf-8" + LINE_END);
						sb1.append("Content-Transfer-Encoding: 8bit" + LINE_END);
						sb1.append(LINE_END);
						sb1.append(entry.getValue());
						sb1.append(LINE_END);
						MyLogUtils.e("=====参数=====",
								entry.getKey() + "==" + entry.getValue());
					}

					DataOutputStream outStream = new DataOutputStream(
							conn.getOutputStream());
					outStream.write(sb1.toString().getBytes());
					if (file != null) {
						/**
						 * 当文件不为空，把文件包装并且上传
						 */
						OutputStream outputSteam = conn.getOutputStream();

						DataOutputStream dos = new DataOutputStream(outputSteam);
						StringBuffer sb = new StringBuffer();
						sb.append(PREFIX);
						sb.append(BOUNDARY);
						sb.append(LINE_END);
						/**
						 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
						 * filename是文件的名字，包含后缀名的 比如:abc.png
						 */

						sb.append("Content-Disposition: form-data; name=\""
								+ fileKey + "\"; filename=\"" + file.getName()
								+ "\"" + LINE_END);
						sb.append("Content-Type: application/octet-stream; charset="
								+ "utf-8" + LINE_END);
						sb.append(LINE_END);
						dos.write(sb.toString().getBytes());
						InputStream is = new FileInputStream(file);
						MyLogUtils.e("==图片大小===", is.available() / 1000000.0 + "M");
						byte[] bytes = new byte[1024];
						int len = 0;
						while ((len = is.read(bytes)) != -1) {
							dos.write(bytes, 0, len);
						}
						is.close();
						dos.write(LINE_END.getBytes());
						byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
								.getBytes();
						dos.write(end_data);
						dos.flush();
						/**
						 * 获取响应码 200=成功 当响应成功，获取响应的流
						 */
						int res = conn.getResponseCode();
						if (res == 200) {
							InputStream in = conn.getInputStream();
							InputStreamReader isReader = new InputStreamReader(
									in);
							BufferedReader bufReader = new BufferedReader(
									isReader);
							String line;
							String result = "";
							while ((line = bufReader.readLine()) != null) {
								result += "\n" + line;
							}
							final String json = result;
							handler.post(new Runnable() {

								@Override
								public void run() {
									httpResponse.responseOk(json);
								}
							});
						} else {
							handler.post(new Runnable() {
								@Override
								public void run() {
									httpResponse.responseError("网络异常");
								}
							});
						}
					} else {
						MyLogUtils.e("==图片大小===", "===没有图片===");
					}
				} catch (Exception e) {
					final Exception e1 = e;
					handler.post(new Runnable() {
						@Override
						public void run() {
							httpResponse.responseError(e1.toString() + "");
						}
					});
				}
			};
		}.start();

	}
	public static void startPhotoZoom(Context context, Uri uri, Uri outUri,int dp){
		dp=DensityUtil.dip2px(dp);//dp转px
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 2);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", dp*3/2);
		intent.putExtra("outputY", dp);
		// 图片格式
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
		intent.putExtra("return-data", false);// 设置返回数据

		((Activity) context).startActivityForResult(intent, 2);
	}

	/**
	 * 截图
	 * 
	 * @param uri
	 */
	public static void startPhotoZoom(Context context, Uri uri, Uri outUri) {
		//图片大小
		int dp = 600;
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
 		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", dp);
		intent.putExtra("outputY", dp);
		// 图片格式
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
		intent.putExtra("return-data", false);// 设置返回数据

		((Activity) context).startActivityForResult(intent, 2);
	}

	/**
	 * 生成一个Uri路径
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Uri getOutImgUri() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String path = sdf.format(date);
		File file = new File(Environment.getExternalStorageDirectory(), "SW_"
				+ path + ".jpg");
		return Uri.fromFile(file);
	}

	/**
	 * 删除头像零时文件
	 */
	public static void deleteTempImage() {
		// 删除头像零时文件
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/temp.jpg");
		if (!f.exists()) {
			return;
		}
		f.delete();
	}

	public interface FilePostCallback{
		public void responseOk(String json);
		public void responseError(String error);
	}	
	 
}
