package com.siwei.tongche.module.usercenter.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.dialog.PopWindowSelectCamera;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageGallaryActivity;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.bind_unit.BindUserUnitActivity;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.usercenter.ope.UserBaseInfoUIOpe;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.ImageUploadUtils;
import com.siwei.tongche.utils.MyLogUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.OnClick;

/**
 * 用户基本信息
 */
public class UserBaseInfoActivity extends BaseActivity {


    UserBaseInfoUIOpe uiOpe;

    @Override
    public int getContentView() {
        return R.layout.activity_user_baseinfo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("基本信息");
        uiOpe= new UserBaseInfoUIOpe(this,rootView);
    }
    //部分手机拍照  执行两次OnCreate
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }




    @OnClick({R.id.baseInfo_layout_header,R.id.baseInfo_header,R.id.baseInfo_layout_name
            ,R.id.baseInfo_layout_mobile,R.id.baseInfo_layout_company,R.id.baseInfo_layout_QRCode,
            R.id.baseInfo_layout_driverid
    })
    public void onClick(View v){
        switch (v.getId()){
            case R.id.baseInfo_layout_header://头像
                startActivityForResult (new Intent(this, PopWindowSelectCamera.class), 0x013);
                break;
            case R.id.baseInfo_header://头像大图
                ArrayList<String>  header=new ArrayList<>();
                header.add(CacheUtils.getLocalUserInfo().getUHeadImg());
                startActivity(new Intent(this, ImageGallaryActivity.class).putExtra("pics", header));
                break;
            case R.id.baseInfo_layout_name://姓名
                startActivityForResult(new Intent(this, UpdateInfoActivity.class).putExtra("title", "更改姓名").putExtra("defaultValue",uiOpe.getmBaseInfo_name().getText().toString()), UpdateInfoActivity.UPDATE_NAME);
                break;
            case R.id.baseInfo_layout_mobile://手机号
                startActivity(new Intent(this, UpdateMobileActivity.class));
                break;
            case R.id.baseInfo_layout_company://单位
                Intent intent = new Intent(this, BindUserUnitActivity.class);
                intent.putExtra(BindUserUnitActivity.KEY_UNIT,BindUserUnitActivity.MY_UNIT);
                startActivity(intent);
                break;
            case R.id.baseInfo_layout_QRCode://二维码名片
                startActivity(new Intent(this, QRCodeCardActivity.class));
                break;
            case R.id.baseInfo_layout_driverid:
                startActivity(new Intent(this, UpdateDriverIdActivity.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case UpdateInfoActivity.UPDATE_NAME://姓名
                        uiOpe.getmBaseInfo_name().setText(data.getStringExtra("newValue"));
                    break;
                case UpdateInfoActivity.UPDATE_COMPANY://公司
                    uiOpe.getmBaseInfo_company().setText(data.getStringExtra("newValue"));
                    break;
                case 0x013://修改头像
                    switch (data.getIntExtra("type", -1)) {
                        case PopWindowSelectCamera.UPLOAD_OPEN_CAMERA://打开相机
                            openCamera();
                            break;
                        case PopWindowSelectCamera.UPLOAD_OPEN_PHOTO://打开相册
                            openGallery();
                            break;
                        default:
                            break;
                    }
                    break;
                case 0: // 相册
                    uri = data.getData();
                    outUri = ImageUploadUtils.getOutImgUri();
                    ImageUploadUtils.startPhotoZoom(this, uri,outUri);
                    break;
                // 相机
                case 1: //
                    uri = Uri.fromFile(file);
                    outUri = ImageUploadUtils.getOutImgUri();
                    ImageUploadUtils.startPhotoZoom(this, uri, outUri);
                    break;
                // 截图后的处理效果
                case 2: // 上传图片
                    showLoadingDialog("上传中");
                    ImageUploadUtils.fileUpload(
                            this, outUri,ImageUploadUtils.TYPE_HEADER,new ImageUploadUtils.FilePostCallback() {

                                @Override
                                public void responseOk(String json) {
                                    JSONObject jsonObject = JSONObject.parseObject(json);
                                    MyLogUtils.e(jsonObject.toString());
                                    if (jsonObject.getString("status").equals("1000")) {
                                        String filePath = jsonObject.getJSONObject("resultData").getString("filePath");
                                        ImageLoaderManager.getImageLoader().displayImage(filePath, uiOpe.getmBaseInfo_header());
                                        UserInfo userInfo=CacheUtils.getLocalUserInfo();
                                        userInfo.setUHeadImg(filePath);
                                        CacheUtils.setLocalUserInfo(userInfo);
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


    private File file;

    /**
     * 调用相机
     */
    public void openCamera() {
        file = new File(Environment.getExternalStorageDirectory(),
                "/temp.png");
        // 设置照片数据到指定文件中
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 1);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private Uri uri;
    private Uri outUri;


}
