package com.siwei.tongche.module.accident;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.dialog.PopWindowSelectCamera;
import com.siwei.tongche.utils.ImageUploadUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 故障报告
 */
public class AccidentReportActivity extends BaseActivity {

    @Bind(R.id.accident_type)
    TextView mAccident_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("故障报告");
        setRight(0,"提交");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_accident_report;
    }



    @OnClick({R.id.select_accident_type,R.id.accident_add_pic,R.id.add_report})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.select_accident_type://故障类型
                showSelectDialog();
                break;
            case R.id.accident_add_pic://添加照片
                startActivityForResult(new Intent(this, PopWindowSelectCamera.class), 0x013);
                break;
            case R.id.add_report://提交
                MyToastUtils.showToast("提交");
                break;

        }

    }

    private void showSelectDialog(){
        final ArrayList<String> typeList= new ArrayList<String>();
        typeList.addAll(Arrays.asList("车辆故障","爆胎了啊","油箱漏油","交通事故"));
        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(typeList);
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String  selectStr= typeList.get(options1);
                mAccident_type.setText(selectStr);
            }
        });
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
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
                            this, outUri,1,new ImageUploadUtils.FilePostCallback() {

                                @Override
                                public void responseOk(String json) {
                                    JSONObject jsonObject = JSONObject.parseObject(json);
                                    MyLogUtils.e(jsonObject.toString());
                                    if (jsonObject.getString("status").equals("1000")) {
                                        String filePath = jsonObject.getJSONObject("resultData").getString("filePath");

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
