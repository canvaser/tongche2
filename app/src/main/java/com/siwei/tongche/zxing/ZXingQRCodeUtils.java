package com.siwei.tongche.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;

import com.siwei.tongche.R;
import com.siwei.tongche.utils.MyToastUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by HanJinLiang on 2016-05-31.
 */
public class ZXingQRCodeUtils {
    public static final int START_SCAN=0X011;

    /**
     * 开始扫描
     * @param context  必须是Activity
     */
    public static void startScan(Context context){
        //打开扫描界面扫描条形码或二维码
        Intent openCameraIntent = new Intent(context, CaptureActivity.class);
        ((Activity)context).startActivityForResult(openCameraIntent, START_SCAN);
    }


    /**
     * 开始扫描
     * @param fragment
     */
    public static void startScan(Fragment fragment){
        //打开扫描界面扫描条形码或二维码
        Intent openCameraIntent = new Intent(fragment.getContext(), CaptureActivity.class);
        fragment.startActivityForResult(openCameraIntent, START_SCAN);
    }

    /**
     * 开始扫描
     * @param fragment
     */
    public static void startScan(Fragment fragment, int requestCode){
        Intent openCameraIntent = new Intent(fragment.getContext(), CaptureActivity.class);
        fragment.startActivityForResult(openCameraIntent, requestCode);
    }

    /**
     * 开始扫描
     * @param context
     */
    public static Bitmap createQRcode(Context context,String content){
        if (!content.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            return  EncodingUtils.createQRCode(content, 350, 350,
                            BitmapFactory.decodeResource(context.getResources(), R.drawable.logo)
                             );
        } else {
            MyToastUtils.showToast("Text can not be empty");
            return null;
        }
    }

    /**
     * 开始扫描
     * @param context
     */
    public static Bitmap createQRcode(Context context,String content,Bitmap bitmap){
        if (!content.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            return  EncodingUtils.createQRCode(content, 350, 350,
                    bitmap);
        } else {
            MyToastUtils.showToast("Text can not be empty");
            return null;
        }
    }
}
