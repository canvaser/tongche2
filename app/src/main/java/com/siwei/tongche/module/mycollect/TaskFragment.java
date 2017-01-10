package com.siwei.tongche.module.mycollect;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.ramotion.foldingcell.FoldingCell;
import com.siwei.tongche.R;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.module.main.activity.ExpressListActivity;
import com.siwei.tongche.views.CircleBar;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${viwmox} on 2016-12-26.
 */

public class TaskFragment extends Fragment {

    @Bind(R.id.listView_task)
    ListView listView_task;

    MyBaseAdapter<String> collectAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_collect_task,container,false);
        ButterKnife.bind(this,view);//绑定
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        List<String> your_array_list = Arrays.asList(
                "This",
                "Is",
                "An",
                "Example",
                "ListView",
                "That",
                "You",
                "Can",
                "Scroll",
                ".",
                "It",
                "Shows",
                "How",
                "Any",
                "Scrollable",
                "View",
                "Can",
                "Be",
                "Included",
                "As",
                "A",
                "Child",
                "Of",
                "SlidingUpPanelLayout"
        );
        MyBaseAdapter<String> baseAdapter=new MyBaseAdapter<String>(your_array_list,getActivity()) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(getActivity(),convertView,parent,R.layout.item_cell_task,position);

                CircleBar circleBar=viewHolder.getView(R.id.task_progress);
                circleBar.update(68,1000);
                CircleBar circleBarDetail=viewHolder.getView(R.id.task_detail_progress);
                circleBarDetail.update(68,1000,1300);

                final FoldingCell foldingCell=viewHolder.getView(R.id.item_task);
                viewHolder.getView(R.id.layout_task_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });

                SwipeLayout swipeLayout=viewHolder.getView(R.id.layout_task_title);
                swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

                swipeLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                viewHolder.getView(R.id.swipeOpe).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(),ExpressListActivity.class));
                    }
                });

                return viewHolder.getConvertView();
            }
        };

        listView_task.setAdapter(baseAdapter);
    }
}
