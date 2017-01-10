package com.siwei.tongche.module.mycollect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.ramotion.foldingcell.FoldingCell;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;

import butterknife.Bind;
import butterknife.OnClick;

public class MyCollectActivity extends BaseActivity {


    @Bind(R.id.tv_ticket)
    TextView ticketTV;
    @Bind(R.id.tv_task)
    TextView taskTV;

    TaskFragment taskFragment;

    TicketFragment ticketFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收藏");
        taskFragment= new TaskFragment();
        ticketFragment = new TicketFragment();
        initView();
    }


    private void initView(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction .add(R.id.rl_collect_container,taskFragment);
        fragmentTransaction .add(R.id.rl_collect_container,ticketFragment);
        fragmentTransaction.commit();
        toggleFragment(0);
        onTopClick(ticketTV);
    }

    private  void toggleFragment(int index){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if(index==0){
            fragmentTransaction.show(ticketFragment);
            fragmentTransaction.hide(taskFragment);
        }else{
            fragmentTransaction.show(taskFragment);
            fragmentTransaction.hide(ticketFragment);
        }
        fragmentTransaction.commit();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_my_collect;
    }

    @OnClick({R.id.tv_task,R.id.tv_ticket})
    public void onTopClick(View view){
        switch (view.getId()){
            case R.id.tv_task:
                taskTV.setTextColor(getResources().getColor(R.color.text_blue));
                ticketTV.setTextColor(getResources().getColor(R.color.text_black));
                toggleFragment(1);
                break;
            case R.id.tv_ticket:
                ticketTV.setTextColor(getResources().getColor(R.color.text_blue));
                taskTV.setTextColor(getResources().getColor(R.color.text_black));
                toggleFragment(0);
                break;
        }
    }


}
