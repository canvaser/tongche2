package com.siwei.tongche.module.carmanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.dialog.PopWindowSelectCamera;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.carmanager.bean.CarDetailInfoBean;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.usercenter.activity.UpdateInfoActivity;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.ImageUploadUtils;
import com.siwei.tongche.utils.MyLogUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加车辆
 */
public class AddCarActivityDetail extends BaseActivity {
    public static String KEY_PLATE="KEY_PLATE";
    @Bind(R.id.pic_car)
    ImageView pic_car;//车辆照片
    @Bind(R.id.pic_drivingLicence)
    ImageView pic_drivingLicence;//行驶证照片
    @Bind(R.id.car_plate)
    EditText car_plate;//车牌
    @Bind(R.id.car_carNo)
    EditText car_carNo;//车号
    @Bind(R.id.car_size)
    EditText car_size;//规格
    @Bind(R.id.car_brand)
    EditText car_brand;//品牌
    @Bind(R.id.car_unit)
    EditText car_unit;//所属单位
    @Bind(R.id.car_years)
    EditText car_years;//年限
    @Bind(R.id.car_mileage)
    EditText car_mileage;//行驶公里数
    @Bind(R.id.car_maintenance_mileage)
    EditText car_maintenance_mileage;//保养公里数

    String plate;
    String mVImg;//车辆照片
    String mVTravelImg;//行驶证照片

    CarDetailInfoBean mCarDetailInfoBean;
    @Override
    public int getContentView() {
        return R.layout.activity_add_car_detail;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加车辆");
        plate=getIntent().getStringExtra(KEY_PLATE);
        car_plate.setEnabled(false);//车牌号不可编辑
        car_plate.setText(plate);
        loadCarInfo();
    }

    /**
     * 加载车辆信息
     */
    private void loadCarInfo() {
        if(plate==null){
            return;
        }
//        VPlateNumber(车牌号)
        RequestParams params=new RequestParams();
        params.put("VPlateNumber",plate);
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_CARINFO_PLATE,params, MyHttpUtil.ReturnType.OBJECT, CarDetailInfoBean.class,"", new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if(object!=null){
                    mCarDetailInfoBean=(CarDetailInfoBean)object;
                    initView(mCarDetailInfoBean);
                }
            }
        });
    }

    private void initView(CarDetailInfoBean carDetailInfo) {

        car_plate.setText(plate);
        car_carNo.setText(carDetailInfo.getV_NO());
        car_size.setText(carDetailInfo.getVGgxh());
        car_brand.setText(carDetailInfo.getVBrand());
//        car_unit.setText();
        car_years.setText(carDetailInfo.getVAgeLimit()+"年");
        car_mileage.setText(carDetailInfo.getVTravelMiles()+"公里");
        car_maintenance_mileage.setText(carDetailInfo.getVKeepMiles()+"公里");
        mVImg=carDetailInfo.getVImg();
        mVTravelImg=carDetailInfo.getVTravelImg();
        ImageLoaderManager.getImageLoader().displayImage(mVImg,pic_car);
        ImageLoaderManager.getImageLoader().displayImage(mVTravelImg,pic_drivingLicence);
    }

    @OnClick({R.id.addCar,R.id.layout_pic_car,R.id.layout_pic_drivingLicence})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addCar:
                bindCar();
                break;
            case R.id.layout_pic_car://上传车辆照片
                startActivityForResult (new Intent(this, PopWindowSelectCamera.class), 0x014);
                break;
            case R.id.layout_pic_drivingLicence://上传行驶证
                startActivityForResult (new Intent(this, PopWindowSelectCamera.class), 0x015);
                break;
        }
    }

    /**
     * 绑定车辆
     */
    private void bindCar() {
        String  carNo=car_carNo.getText().toString();
        String  size=car_size.getText().toString();
        String  brand=car_brand.getText().toString();
        String  unit=car_unit.getText().toString();
        String  years=car_years.getText().toString();
        String  mileage=car_mileage.getText().toString();
        String  maintenance_mileage=car_maintenance_mileage.getText().toString();

//        VPlateNumber（车牌号）,V_NO（车号）,VGgxh（规格型号）
//        ,VBrand（品牌）,VUnitID（驾驶员单位ID）,VAgeLimit（车辆年限）
//        ,VTravelMiles（已行驶公里数）,VKeepMiles（保养公里数）
//        ,VImg（车辆照片路径）,VTravelImg（行驶证照片）,DriverID（驾驶员用户ID）
        JSONObject params=new JSONObject();
        params.put("VPlateNumber",plate);
        params.put("V_NO",carNo);
        params.put("VGgxh",size);
        params.put("VBrand",brand);
        params.put("VUnitID",unit);
        params.put("VAgeLimit",years);
        params.put("VTravelMiles",mileage);
        params.put("VKeepMiles",maintenance_mileage);
        params.put("VImg",mVImg);
        params.put("VTravelImg",mVTravelImg);
        params.put("DriverID",CacheUtils.getLocalUserInfo().getUID());
        MyHttpUtil.sendRequest(this, MyUrls.BIND_CAR, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if((boolean)object){//绑定车辆成功

                }
            }
        });
    }

    int requstcode = -1;

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 0x014:////上传车辆照片
                case 0x015:////上传行驶证
                    requstcode = requestCode;
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
                    int type=requstcode==0x014?ImageUploadUtils.TYPE_CAR:ImageUploadUtils.TYPE_VEHICLE_LICENCE;
                    ImageUploadUtils.fileUpload(
                            this, outUri,type,new ImageUploadUtils.FilePostCallback() {

                                @Override
                                public void responseOk(String json) {
                                    JSONObject jsonObject = JSONObject.parseObject(json);
                                    MyLogUtils.e(jsonObject.toString());
                                    if (jsonObject.getString("status").equals("1000")) {
                                        String filePath = jsonObject.getJSONObject("resultData").getString("filePath");
                                         if(requstcode==0x014){
                                             mVImg=filePath;
                                             ImageLoaderManager.getImageLoader().displayImage(filePath,pic_car);
                                         }else{
                                            mVTravelImg=filePath;
                                             ImageLoaderManager.getImageLoader().displayImage(filePath,pic_drivingLicence);
                                         }
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
     * 调用相机
     */
    public void openCamera(int requsecode) {
        file = new File(Environment.getExternalStorageDirectory(),
                "/temp.png");
        // 设置照片数据到指定文件中
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, requsecode);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    /**
     * 打开相册
     */
    public void openGallery(int requsecode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, requsecode);
    }

    private Uri uri;
    private Uri outUri;
}
