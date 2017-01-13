package com.siwei.tongche.module.usermanager;

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
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.module.usercenter.bean.UserBean;
import com.siwei.tongche.utils.CacheUtils;

import java.util.ArrayList;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 用户管理
 */
public class UserManagerActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView mListviewUser;
    MyBaseAdapter<UserBean> mUserAdapter;

    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMoreListViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用户管理");
        init();
        setRight(R.drawable.title_add,"");
    }

    private void init(){
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(this, mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListviewUser, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getNetUserBaseInfo();
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
    }


    public void getNetUserBaseInfo(){
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UserID",userInfo.getUID());
        MyHttpUtil.sendGetRequest(this, MyUrls.GET_UNIT_USER_LIST, params, MyHttpUtil.ReturnType.ARRAY, UserBean.class, "",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                ArrayList<UserBean> list = (ArrayList<UserBean>) object;
                initView(list);
                mPtrFrame.refreshComplete();
            }
        });
    }

    private void initView(final ArrayList<UserBean> list) {


        mUserAdapter=new MyBaseAdapter<UserBean>(list,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, UserBean userBean) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(UserManagerActivity.this,convertView,parent,R.layout.item_user_manager,position);
                viewHolder.setText(R.id.user_name,userBean.getUName());
                viewHolder.setText(R.id.user_mobile,userBean.getUPhone());
                viewHolder.setText(R.id.tv_shr,userBean.getAuditor()+"  "+userBean.getBindTime().substring(0,10));
                switch (userBean.getUnitRole()){
                    case AppConstants.USER_UNIT_ROLE.ROLE_CREATOR:
                        viewHolder.setText(R.id.user_type,"创建者");
                        break;
                    case AppConstants.USER_UNIT_ROLE.ROLE_MANAGER:
                        viewHolder.setText(R.id.user_type,"管理员");
                        break;
                    case AppConstants.USER_UNIT_ROLE.ROLE_NORMAL:
                        viewHolder.setText(R.id.user_type,"普通员工");
                        break;
                }
                Button button = viewHolder.getView(R.id.delete);
                if(userBean.getIsStop()==0){
                    button.setText("启用");
                    button.setBackgroundResource(R.drawable.corner_blue_blue);
                }else{
                    button.setText("停用");
                    button.setBackgroundResource(R.drawable.corner_red_red);
                }
                ImageLoaderManager.getImageLoader().displayImage(userBean.getUHeadImg(),(ImageView) viewHolder.getView(R.id.tv_head));
                return viewHolder.getConvertView();
            }
        };
        mListviewUser.setAdapter(mUserAdapter);

        mListviewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserManagerActivity.this,UserDetailInfoActivity.class);
                intent.putExtra("data",list.get(i));
                startActivity(intent);
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
