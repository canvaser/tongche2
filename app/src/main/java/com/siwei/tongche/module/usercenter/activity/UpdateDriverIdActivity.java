package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.dialog.PopWindowSelectCamera;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.usercenter.ope.UpdateDriverIdUIOpe;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.ImageUploadUtils;
import com.siwei.tongche.utils.IntentUtil;
import com.siwei.tongche.utils.MD5Util;
import com.siwei.tongche.utils.MyLogUtils;

import butterknife.OnClick;

/**
 * Created by ${viwmox} on 2016-12-28.
 */

public class UpdateDriverIdActivity extends BaseActivity{

    UpdateDriverIdUIOpe uiOpe;

    IntentUtil intentUtil =new IntentUtil();

    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiOpe = new UpdateDriverIdUIOpe(this,rootView);
        setTitle("上传驾驶证");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_update_driverid;
    }

    @OnClick({R.id.btn_save,R.id.iv_addpic})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_save:
                updateDriveInfo(filePath);
                break;
            case R.id.iv_addpic:
                startActivityForResult (new Intent(this, PopWindowSelectCamera.class), 0x013);
                break;
        }
    }


    public void updateDriveInfo(String filePath){
        UserInfo userInfo=CacheUtils.getLocalUserInfo();
        JSONObject params=new JSONObject();
        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UDriveNo",uiOpe.getInputET().getText().toString());
        params.put("UDriveImg",filePath);
        MyHttpUtil.sendRequest(this, MyUrls.COMPLETE_DRIVER_INFO, params, MyHttpUtil.ReturnType.BOOLEAN, UserInfo.class, "登录中...",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if((Boolean) object){
                    finish();
                }
            }
        });
    }

    Uri uri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 0x013://修改头像
                    switch (data.getIntExtra("type", -1)) {
                        case PopWindowSelectCamera.UPLOAD_OPEN_CAMERA://打开相机
                            intentUtil.openCamera(this);
                            break;
                        case PopWindowSelectCamera.UPLOAD_OPEN_PHOTO://打开相册
                            intentUtil.openGallery(this);
                            break;
                        default:
                            break;
                    }
                    break;
                case 0: // 相册
                    uri=ImageUploadUtils.getOutImgUri();
                    ImageUploadUtils.startPhotoZoom(this, data.getData(),uri);
                    break;
                // 相机
                case 1: //
                    uri=ImageUploadUtils.getOutImgUri();
                    ImageUploadUtils.startPhotoZoom(this, Uri.fromFile(intentUtil.getFile()), uri);
                    break;
                // 截图后的处理效果
                case 2: // 上传图片
                    showLoadingDialog("上传中");
                    ImageUploadUtils.fileUpload(
                            this, uri,2,new ImageUploadUtils.FilePostCallback() {

                                @Override
                                public void responseOk(String json) {
                                    JSONObject jsonObject = JSONObject.parseObject(json);
                                    MyLogUtils.e(jsonObject.toString());
                                    if (jsonObject.getString("status").equals("1000")) {
                                         filePath = jsonObject.getJSONObject("resultData").getString("filePath");

                                       // ImageLoaderManager.getImageLoader().displayImage(filePath, uiOpe.getmBaseInfo_header());
                                    }
                                    hideLoadingDialog();
                                }

                                @Override
                                public void responseError(String error) {
                                    MyLogUtils.e("头像上传", error);
                                    hideLoadingDialog();
                                }
                            });
                    break;
            }
        }
    }
}
