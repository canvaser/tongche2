package com.siwei.tongche.module.carmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加车辆
 */
public class AddCarActivity extends BaseActivity {
    @Bind(R.id.plate_number)
    EditText et_plate_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加车辆");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_car;
    }

    @OnClick({R.id.addCarNext})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addCarNext:
                startAddCar();
                break;
        }
    }

    private void startAddCar() {
        String plate_number=et_plate_number.getText().toString();
        if(MyRegexpUtils.isEmpty(plate_number)){
            MyToastUtils.showToast("车牌号不能为空");
            return;
        }
        startActivity(new Intent(this,AddCarActivityDetail.class).putExtra(AddCarActivityDetail.KEY_PLATE,plate_number.toUpperCase()));
    }
}
