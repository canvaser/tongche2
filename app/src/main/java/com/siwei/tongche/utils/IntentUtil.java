package com.siwei.tongche.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;


import java.io.File;

//import com.siweisoft.imga.constant.ValueConstant;

/**
 * 跳转
 * Created by SWSD on 2016/4/18.
 */
public class IntentUtil {


    private Uri uri;

    private Uri outUri;

    private File file;

    /**
     * 调用相机
     */
    public void openCamera(Activity activity) {
        file = new File(Environment.getExternalStorageDirectory(),
                "/temp.png");
        // 设置照片数据到指定文件中
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        activity.startActivityForResult(intent, 1);
    }

    /**
     * 打开相册
     */
    public void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 0);
    }

    public File getFile() {
        return file;
    }

    public Uri getOutUri() {
        return outUri;
    }

    public Uri getUri() {
        return uri;
    }
}
