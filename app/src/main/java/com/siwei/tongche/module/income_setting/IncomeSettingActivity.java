package com.siwei.tongche.module.income_setting;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.income_setting.views.IncomeSettingView;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 收入设置
 */
public class IncomeSettingActivity extends BaseActivity {


    @Bind(R.id.tv_unit)
    TextView mTv_unit;//计算单位

    @Bind(R.id.layout_incomeSettingView)
    IncomeSettingView  mIncomeSettingView;//单位是方

    @Bind(R.id.layout_unit_car)
    LinearLayout  mUnit_car;//单位是车





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("收入设置");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_income_setting;
    }

    @OnClick({R.id.layout_unit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.layout_unit://单位
                showSelectDialog();
                break;
        }
    }


    private void showSelectDialog(){
        final ArrayList<String> typeList= new ArrayList<String>();
        typeList.add("方");
        typeList.add("车");

        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(typeList);
        pvOptions.setCyclic(false);
        pvOptions.setSelectOptions(0);
        //监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if(options1==0){//方
                    mUnit_car.setVisibility(View.VISIBLE);
                    mIncomeSettingView.setVisibility(View.GONE);
                }else{
                    mUnit_car.setVisibility(View.GONE);
                    mIncomeSettingView.setVisibility(View.VISIBLE);
                }
                String  selectStr= typeList.get(options1);
                mTv_unit.setText(selectStr);
            }
        });
        pvOptions.show();
    }
}
