package com.siwei.tongche.module.bind_unit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.common.TextWatcherAdapter;
import com.siwei.tongche.dialog.NormalAlertDialog;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.bind_unit.bean.UnitInfo;
import com.siwei.tongche.module.bind_unit.ope.BindUserUnitDAOpe;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class BindUserUnitActivity extends BaseActivity {

    @Bind(R.id.recyclerView_unit)
    ListView mRecyclerView_unit;

    @Bind(R.id.et_depart)
    EditText departEt;

    MyBaseAdapter<UnitInfo> commonAdapter;

    BindUserUnitDAOpe bindUserUnitDAOpe;

    public static final String KEY_UNIT = "KEY_UNIT";

    //用户本身所属单位
    public static final String  MY_UNIT = "1";
    //发货单位
    public static final String  SEND_UNIT = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("绑定单位");
        bindUserUnitDAOpe = new BindUserUnitDAOpe();
        initView();
    }
    private int mSelectedPos = -1;//实现单选  方法二，变量保存当前选中的position
    private void initView() {
        getAllUnit();
    }

    private void getAllUnit() {
        String UnitName = getIntent().getStringExtra("UnitName");
        RequestParams params = new RequestParams();
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_ALL_UNIT, params, MyHttpUtil.ReturnType.ARRAY, UnitInfo.class, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if (object == null) {
                    return;
                }
                final ArrayList<UnitInfo> list = (ArrayList<UnitInfo>) object;
                bindUserUnitDAOpe.setList(list);
                commonAdapter = new MyBaseAdapter<UnitInfo>(bindUserUnitDAOpe.getNowList(), BindUserUnitActivity.this) {
                    @Override
                    public View getItemView(final int position, View convertView, ViewGroup parent, final UnitInfo model) {
                        MyViewHolder viewHolder = MyViewHolder.getViewHolder(getApplicationContext(), convertView, parent, R.layout.item_bind_unit, position);
                        viewHolder.setText(R.id.item_unit_name,model.getUnitName());
                        viewHolder.setText(R.id.item_unit_linkMen,model.getUName()+"  "+ model.getUPhone());
                        viewHolder.setChecked(R.id.item_unit_checkbox, model.isSelected());//设置是否选中
                        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mSelectedPos == -1) {//第一个
                                    //设置新Item的勾选状态
                                    mSelectedPos = position;
                                    bindUserUnitDAOpe.getNowList().get(mSelectedPos).setSelected(true);
                                    notifyDataSetChanged();
                                    return;
                                }
                                if (mSelectedPos != position) {
                                    //先取消上个item的勾选状态
                                    bindUserUnitDAOpe.getNowList().get(mSelectedPos).setSelected(false);
                                    //设置新Item的勾选状态
                                    mSelectedPos = position;
                                    bindUserUnitDAOpe.getNowList().get(mSelectedPos).setSelected(true);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        return viewHolder.getConvertView();
                    }

                    ;

                };
                mRecyclerView_unit.setAdapter(commonAdapter);

                departEt.addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        bindUserUnitDAOpe.getSelectList(s.toString());
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
    @Override
    public int getContentView() {
        return R.layout.activity_bind_user_unit;
    }

    @OnClick({R.id.bindUnit,R.id.tv_report})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bindUnit://绑定单位
                bindUnit();
                break;
            case R.id.tv_report:
                startActivity(new Intent(this,ComplainActivity.class));
                break;
        }
    }

    /**
     * 绑定单位
     */
    private void bindUnit() {
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        if(bindUserUnitDAOpe.getNowList().size()==0 && !userInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){
            showCreateUnitDialog();
        }else{
            UnitInfo  unitInfo = bindUserUnitDAOpe.getSelect();
            if(unitInfo!=null){
                bindUnitInfo(unitInfo);
            }
        }
    }


    public void bindUnitInfo(UnitInfo  unitInfo){
        JSONObject params=new JSONObject();
       final  UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UID",userInfo.getUID());
        params.put("UnitId",unitInfo.getUID());
        //绑定用户本身所属单位1 、绑定 发货单位2
        params.put("BindUnitType",getIntent().getStringExtra(KEY_UNIT));
        MyHttpUtil.sendRequest(this, MyUrls.BIND_UNIT, params, MyHttpUtil.ReturnType.BOOLEAN,null, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                if((boolean)object){//成功
                    MyToastUtils.showToast("提交审核成功");
                    if(getIntent().getStringExtra(KEY_UNIT).equals(MY_UNIT)){//所属单位
                        //0未绑定、1已绑定、2审核中、3绑定失败
                        userInfo.setUAuditStatus("2");
                        CacheUtils.setLocalUserInfo(userInfo);
                    }else{//发货单位
                        //0未绑定、1已绑定、2审核中、3绑定失败
                        userInfo.setUBindUnitStatus("2");
                        CacheUtils.setLocalUserInfo(userInfo);
                    }
                    finish();
                }
            }
        });
    }




    private void showCreateUnitDialog() {
        NormalAlertDialog alertDialog=new NormalAlertDialog(this,"该单位不存在，是否创建新单位？");
        alertDialog.setClicklistener(new NormalAlertDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                Intent i  = new Intent(BindUserUnitActivity.this,CreateUnitActivity.class);
                i.putExtra("UnitName",departEt.getText().toString());
                startActivity(i);
            }

            @Override
            public void doCancel() {

            }
        });
        alertDialog.show();
    }
}
