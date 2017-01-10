package com.siwei.tongche.module.usermanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;

import butterknife.Bind;

/**
 * 用户管理
 */
public class UserManagerActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView mListviewUser;
    MyBaseAdapter<String> mUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用户管理");
        setRight(R.drawable.title_add,"");
        initView();
    }

    private void initView() {
        mUserAdapter=new MyBaseAdapter<String>(null,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=new MyViewHolder(UserManagerActivity.this,parent,R.layout.item_user_manager,position);
                return viewHolder.getConvertView();
            }
        };
        mListviewUser.setAdapter(mUserAdapter);

        mListviewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(UserManagerActivity.this,UserDetailInfoActivity.class));
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
