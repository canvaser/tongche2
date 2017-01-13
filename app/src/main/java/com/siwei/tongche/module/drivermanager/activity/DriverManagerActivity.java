package com.siwei.tongche.module.drivermanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.module.usercenter.bean.DriverBean;
import com.siwei.tongche.module.usermanager.AddUserActivity;
import com.siwei.tongche.module.usermanager.UserDetailInfoActivity;
import com.siwei.tongche.utils.CacheUtils;

import java.util.ArrayList;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ${viwmox} on 2017-01-10.
 */

public class DriverManagerActivity   extends BaseActivity {

    @Bind(R.id.listView)
    ListView mListviewUser;
    MyBaseAdapter<DriverBean> mUserAdapter;

    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMoreListViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getNetDriverBaseInfo();
    }

    private void init(){
        setTitle("驾驶员管理");
        setRight(R.drawable.title_add,"");
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(this, mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListviewUser, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 1000);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
    }

    public void getNetDriverBaseInfo(){
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UserID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_UNIT_DRIVER_LIST, params, MyHttpUtil.ReturnType.ARRAY, DriverBean.class, "",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                ArrayList<DriverBean> list = (ArrayList<DriverBean>) object;
                initView(list);
            }
        });
    }

    private void initView(ArrayList<DriverBean> list ) {

        mUserAdapter=new MyBaseAdapter<DriverBean>(list,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, DriverBean model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(DriverManagerActivity.this,convertView,parent,R.layout.item_driver_manager,position);
                viewHolder.setText(R.id.user_name,model.getUName());
                viewHolder.setText(R.id.user_mobile,model.getUPhone());
                viewHolder.setText(R.id.tv_shr,model.getAuditor()+"  "+model.getBindTime().substring(0,10));
                ImageLoaderManager.getImageLoader().displayImage(model.getUHeadImg(),(ImageView) viewHolder.getView(R.id.tv_head));
                Button button = viewHolder.getView(R.id.delete);
                if(model.getIsStop()==0){
                    button.setText("启用");
                    button.setBackgroundResource(R.drawable.corner_blue_blue);
                }else{
                    button.setText("停用");
                    button.setBackgroundResource(R.drawable.corner_red_red);
                }
                return viewHolder.getConvertView();
            }
        };
        mListviewUser.setAdapter(mUserAdapter);

        mListviewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onRightText(View view) {
        super.onRightText(view);
        startActivity(new Intent(this,AddUserActivity.class));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_listview_refresh;
    }

}
