package com.siwei.tongche.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by HanJinLiang on 2017-01-04.
 */

public class CallUtils {
    /**
     * 跳转到拨号页面
     * @param context
     * @param mobile
     */
    public static void gotoCall(Context context, String mobile) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     * @param context
     * @param mobile
     */
    public static  void directCall(Context context, String mobile) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }
}
