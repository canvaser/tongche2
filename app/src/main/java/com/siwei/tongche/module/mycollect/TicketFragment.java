package com.siwei.tongche.module.mycollect;


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
import com.siwei.tongche.views.timeline.view.TimeLineView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${viwmox} on 2016-12-26.
 */

public class TicketFragment extends Fragment {

    @Bind(R.id.listView_ticket)
    ListView listView_ticket;

    MyBaseAdapter<String> collectAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_collect_ticket,container,false);
        ButterKnife.bind(this,view);//绑定
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        collectAdapter=new MyBaseAdapter<String>(null,getActivity()) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(getActivity(),convertView,parent,R.layout.item_cell_express,position);
                final FoldingCell foldingCell=viewHolder.getView(R.id.item_express);
                TimeLineView timeLineView = viewHolder.getView(R.id.express_detail_schedule);
                timeLineView.setNowTime(3);
                timeLineView.setTxt(new String[]{"1","2","3","4","5","6"});
                viewHolder.getView(R.id.layout_express_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });

                SwipeLayout swipeLayout=viewHolder.getView(R.id.layout_express_title);
                swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

                swipeLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                return viewHolder.getConvertView();
            }
        };
        listView_ticket.setAdapter(collectAdapter);
    }
}
