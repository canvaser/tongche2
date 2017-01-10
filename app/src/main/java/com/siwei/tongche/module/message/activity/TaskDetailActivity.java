package com.siwei.tongche.module.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.module.main.activity.SignRulesActivity;
import com.siwei.tongche.utils.MyToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新建任务
 */
public class TaskDetailActivity extends BaseActivity {
    @Bind(R.id.build_sendUnit)
    TextView mTvSendUnit;//发货单位
    @Bind(R.id.build_contractNo)
    TextView mTvContractNo;//合同编号
    @Bind(R.id.build_projectName)
    TextView mTvProjectName;//工程名称
    @Bind(R.id.build_pouringWay)
    TextView mTvPouringWay;//浇筑方式
    @Bind(R.id.build_pouringParts)
    TextView mTvPouringParts;//浇筑部位
    @Bind(R.id.build_tongKind)
    TextView mTvTongKind;//砼品种
    @Bind(R.id.build_slump)
    TextView mTvSlump;//坍落度
    @Bind(R.id.build_planVolume)
    TextView mTvPlanVolume;//计划方量
    @Bind(R.id.build_planTime)
    TextView mTvPlanTime;//计划时间
    @Bind(R.id.build_signRules)
    TextView mTvSignRules;//签收规则
    @Bind(R.id.build_remarks)
    EditText mEtRemarks;//备注
    @Bind(R.id.tv_special)//特殊需求
    TextView specialTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新建任务");
        setRight(0,"完成");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_task_detail;
    }

    @OnClick({R.id.build_sendUnit,R.id.build_contractNo,R.id.build_tongKind,R.id.build_slump
    ,R.id.build_planTime,R.id.build_signRules,R.id.build_signRulesLinks})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.build_sendUnit://发货单位
                showSelectDialog();
                break;
            case R.id.build_contractNo://合同编号
                showSelectDialog();
                break;
            case R.id.build_tongKind://砼品种
                showSelectDialog();
                break;
            case R.id.build_slump://坍落度
                showSelectDialog();
                break;
            case R.id.build_planTime://计划时间
                showTimeSelectDialog();
                break;
            case R.id.build_signRules://签收规则
                showSelectDialog();
                break;
            case R.id.build_signRulesLinks://签收规则 解释
                startActivity(new Intent(this,SignRulesActivity.class));
                break;
        }
    }

    private void showSelectDialog(){
        //选项选择器
        OptionsPickerView    pvOptions = new OptionsPickerView(this);
        //选项2
       final  ArrayList<String> options2Items_01=new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        //三级联动效果
        pvOptions.setPicker(options2Items_01);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
               String  selectStr= options2Items_01.get(options1);
                MyToastUtils.showToast(selectStr);
            }
        });
        pvOptions.show();
    }


    //时间选择器
    TimePickerView mPvTime;
    private void showTimeSelectDialog(){
        //时间选择器
        mPvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        mPvTime.setTime(new Date());
        mPvTime.setCyclic(false);
        mPvTime.setCancelable(true);
        //时间选择后回调
        mPvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                mTvPlanTime.setText(getTime(date));
            }

            public String getTime(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return format.format(date);
            }
        });
        mPvTime.show();
    }

    @Override
    public void onRightText(View view) {
        MyToastUtils.showToast("创建任务单");
    }
}
