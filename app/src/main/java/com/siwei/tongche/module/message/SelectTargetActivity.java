package com.siwei.tongche.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.message.adapter.SelectTargetAdapter;
import com.siwei.tongche.module.message.bean.CompanyUserList;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.TempUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择发送对象
 */
public class SelectTargetActivity extends BaseActivity {
    @Bind(R.id.list_targets)
    ListView mList_targets;

    @Bind(R.id.btn_select_target)
    Button btn_select_target;


    SelectTargetAdapter mSelectTargetAdapter;

    private ArrayList<CompanyUserList>  mCompanyUserList;
    private ArrayList<String>   mSelectedStr;

    private Boolean isFromMain;//是否从主页进入
    @Override
    public int getContentView() {
        return R.layout.activity_selected_target;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("联系人");
        initView();
        isFromMain=getIntent().getBooleanExtra("isFromMain",false);
        mSelectedStr=getIntent().getStringArrayListExtra("isSelectedStr");
        loadData();
    }

    private void loadData() {
        JSONObject params=new JSONObject();
        params.put("USER_ID", CacheUtils.getLocalUserInfo().getUID());
        MyHttpUtil.sendRequest(this, MyUrls.GET_COMPANY_LIST, params, MyHttpUtil.ReturnType.ARRAY, CompanyUserList.class, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                try {
                    mCompanyUserList = (ArrayList<CompanyUserList>) object;
                    if (mCompanyUserList != null) {
                        if (mCompanyUserList.size() == 0) {//没有联系人   按钮不可见
                            btn_select_target.setVisibility(View.GONE);
                            setRightShowing(false);
                        } else {
                            btn_select_target.setVisibility(View.VISIBLE);
                            setRightShowing(true);
                            setRight(0, "全选");

                            //恢复上次退出选中的
                            if(mSelectedStr!=null&&mSelectedStr.size()>0&&mSelectedStr.size()==mCompanyUserList.size()){
                                mSelectTargetAdapter.setData(mCompanyUserList);
                                LinkedHashMap<Integer, Boolean> mSelected= mSelectTargetAdapter.getIsSelected();
                                mSelected.clear();
                                for (int i = 0; i <mSelectedStr.size(); i++) {
                                    if(mSelectedStr.get(i).equals("1")){
                                        mSelected.put(i, true);
                                    }else{
                                        mSelected.put(i, false);
                                    }
                                }
                                mSelectTargetAdapter.setIsSelected(mSelected);
                                mSelectTargetAdapter.notifyDataSetChanged();
                                return;
                            }

                        }
                        mSelectTargetAdapter.setData(mCompanyUserList);
                        mSelectTargetAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }
        });

    }


    @Override
    public void onRightText(View view) {
        String opeStr=((TextView)view).getText().toString();
        boolean isSelectedAll;
        if(opeStr.equals("全选")){
            isSelectedAll=true;
            setRight(0,"取消");
        }else{
            isSelectedAll=false;
            setRight(0,"全选");
        }
        LinkedHashMap<Integer, Boolean> mSelected= mSelectTargetAdapter.getIsSelected();
        int size=mSelected.size();
        mSelected.clear();
        for (int i = 0; i <size; i++) {
            mSelected.put(i, isSelectedAll);
        }
        mSelectTargetAdapter.setIsSelected(mSelected);
        mSelectTargetAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mSelectTargetAdapter= new SelectTargetAdapter(this,null);
        mList_targets.setAdapter(mSelectTargetAdapter);
        TempUtils.setEmptyView(this,mList_targets,"暂无联系人");
        mList_targets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectTargetAdapter.toggleChecked(position);//切换选中效果
            }
        });
        mSelectTargetAdapter.setOnItemCheckedChangedListener(new SelectTargetAdapter.OnItemCheckedChanged() {
            @Override
            public void onItemCheckedChanged() {
                if(isAllSelected()){//全部选中
                    setRight(0,"取消");
                }else{
                    setRight(0,"全选");
                }
            }
        });
    }

    /**
     * 遍历是否全部选中
     * @return
     */
    private boolean isAllSelected() {
        HashMap<Integer, Boolean> mSelected= mSelectTargetAdapter.getIsSelected();
        for (Map.Entry<Integer, Boolean> entry : mSelected.entrySet()) {
            Boolean isSelected = (Boolean) entry.getValue();
            if(!isSelected){//只要有一个没选中就返回false
                return false;
            }
        }
        return true;
    }

    @OnClick({R.id.btn_select_target})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_select_target://发消息
                selectTarget();
                break;
        }
    }

    private void selectTarget() {
        StringBuilder sb_name=new StringBuilder();//选择的姓名
        StringBuilder sb_ids=new StringBuilder();//选择的ID
        ArrayList<String> isSelectedStr=new ArrayList<String>();//每一项是否选中
        LinkedHashMap<Integer, Boolean> mSelected= mSelectTargetAdapter.getIsSelected();
        for (Map.Entry<Integer, Boolean> entry : mSelected.entrySet()) {
            Integer position = (Integer) entry.getKey();
            Boolean isSelected = (Boolean) entry.getValue();
            if(isSelected){
                sb_name.append(mSelectTargetAdapter.getData().get(position).getUSER_NAME()+"; ");
                sb_ids.append(mSelectTargetAdapter.getData().get(position).getUSER_ID()+",");
                isSelectedStr.add("1");
            }else{
                isSelectedStr.add("0");
            }
        }
        if(sb_name.length()>0) {
            sb_name.delete(sb_name.length() - 2, sb_name.length());
            sb_ids.delete(sb_ids.length() - 1, sb_ids.length());
        }else{
            MyToastUtils.showToast("请选择联系人");
            return;
        }

        if(isFromMain){
            startActivity(new Intent(this, BuildMessageActivity.class).putExtra("targets",sb_name.toString()).putExtra("targets_ids",sb_ids.toString()).putStringArrayListExtra("isSelectedStr",isSelectedStr));
        }else{
            setResult(RESULT_OK,new Intent().putExtra("targets",sb_name.toString()).putExtra("targets_ids",sb_ids.toString()).putStringArrayListExtra("isSelectedStr",isSelectedStr));
        }
        finish();
    }


}
