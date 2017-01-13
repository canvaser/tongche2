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
import com.loopj.android.http.RequestParams;
import com.ramotion.foldingcell.FoldingCell;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MySwipeBaseAdapter;
import com.siwei.tongche.common.MySwipeViewHolder;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.activity.ExpressListActivity;
import com.siwei.tongche.module.main.bean.TaskInfoBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.TimeUtils;
import com.siwei.tongche.views.CircleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ${viwmox} on 2016-12-26.
 */

public class TaskFragment extends Fragment {

    @Bind(R.id.listView_task)
    ListView mList_task;

    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMore;


    UserInfo mUserInfo;
    MySwipeBaseAdapter<TaskInfoBean> mTaskAdapter;
    ArrayList<TaskInfoBean> mTaskDatas=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_collect_task,container,false);
        ButterKnife.bind(this,view);//绑定
        mUserInfo= CacheUtils.getLocalUserInfo();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(getActivity(), mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mList_task, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(false);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);

        //设置loadMore的参数
        ListViewUtils.setLoadMoreParams(getContext(), loadMore);
        loadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadData(true);
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);

        mTaskAdapter=new MySwipeBaseAdapter<TaskInfoBean>(mTaskDatas,getActivity(),R.layout.item_cell_task) {
            @Override
            public int getSwipeLayoutResourceId(int position) {
                return R.id.layout_task_title;
            }
            @Override
            public void fillValues(int position, View convertView, TaskInfoBean model) {
                MySwipeViewHolder viewHolder=MySwipeViewHolder.getMySwipeViewHolder(convertView);

                initTaskView(viewHolder,position,model);

                final FoldingCell foldingCell=viewHolder.getView(R.id.item_task);
                viewHolder.getView(R.id.swipe_task_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                viewHolder.getView(R.id.close_task_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                viewHolder.getView(R.id.task_detail_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyToastUtils.showToast("收藏");
                    }
                });
                viewHolder.getView(R.id.task_detail_call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyToastUtils.showToast("打电话");
                    }
                });
                viewHolder.getView(R.id.swipe_express_list).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(),ExpressListActivity.class));
                    }
                });

            }

            /**
             * 初始化任务单布局
             * @param viewHolder
             * @param position
             * @param model
             */
            private void initTaskView(MySwipeViewHolder viewHolder, int position, TaskInfoBean model) {
                //完成比例
                int progress= (int) ((MyFormatUtils.toDouble(model.getLjNum())/MyFormatUtils.toDouble(model.getPlanNum()))*100);
                CircleBar circleBar=viewHolder.getView(R.id.task_progress);
                circleBar.update(progress,1000);
                CircleBar circleBarDetail=viewHolder.getView(R.id.task_detail_progress);
                circleBarDetail.update(progress,1000,1300);

                //title
                viewHolder.setText(R.id.task_title_taskNo,"NO."+model.getTaskNo());//任务编号
                viewHolder.setText(R.id.task_title_TongType,model.getTpz());//砼品种
                viewHolder.setText(R.id.task_title_jiaozhu,"（"+model.getRtld()+" "+model.getJzfs()+"）");//坍落度、浇筑方式
                viewHolder.setText(R.id.task_title_unit,model.getSendUnitName()+"→"+model.getReceiveUnitName());//发货单位 收货单位
                viewHolder.setText(R.id.task_title_castingPlace,model.getJzbw());//浇筑部位
                viewHolder.setText(R.id.task_title_express,model.getCompleteCs()+"/"+model.getSendCs());//已有小票20  签收15车
                viewHolder.setText(R.id.task_title_progressTime, TimeUtils.getTimeWaitting(model.getPlanDate()));//任务执行时间
                viewHolder.setText(R.id.task_title_finishedVolume,model.getLjNum()+"m³");//完成方量
                viewHolder.setText(R.id.task_title_planVolume,model.getPlanNum());//计划方量


                //任务detail
                viewHolder.setText(R.id.task_detail_taskNo,"NO."+model.getTaskNo());//任务编号
                viewHolder.setText(R.id.task_detail_finishedVolume,model.getLjNum()+"m³");//完成方量
                viewHolder.setText(R.id.task_detail_planVolume,model.getPlanNum());//计划方量
                viewHolder.setText(R.id.task_detail_TongType,model.getTpz());//砼品种
                viewHolder.setText(R.id.task_detail_jiaozhu,"（"+model.getRtld()+" "+model.getJzfs()+"）");//坍落度、浇筑方式
                viewHolder.setText(R.id.task_detail_unit,model.getSendUnitName()+"→"+model.getReceiveUnitName());//发货单位 收货单位
                viewHolder.setText(R.id.task_detail_castingPlace,model.getJzbw());//浇筑部位
                viewHolder.setText(R.id.task_detail_express,model.getCompleteCs()+"/"+model.getSendCs());//已有小票20  签收15车
                viewHolder.setText(R.id.task_detail_progressTime, TimeUtils.getTimeWaitting(model.getPlanDate()));//任务执行时间
                viewHolder.setText(R.id.task_detail_projectName,model.getProName());//工程名称
                viewHolder.setText(R.id.task_detail_linkMan,model.getTaskLinkMan());//联系人
                viewHolder.setText(R.id.task_detail_contractNo,"合同："+model.getContractNo());//合同编号
                viewHolder.setText(R.id.task_detail_planTime,"计划："+model.getPlanDate());//计划时间
                viewHolder.setText(R.id.task_detail_startTime,"开始："+model.getFactStartDate());//开始时间
                viewHolder.setText(R.id.task_detail_projectAddress,model.getProAddress());//工程地址
                //备注
                if(MyRegexpUtils.isEmpty(model.getMemo())){
                    viewHolder.getView(R.id.task_detail_remarks).setVisibility(View.GONE);
                }else{
                    viewHolder.getView(R.id.task_detail_remarks).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.task_detail_remarks,model.getMemo());
                }

                //初始化侧滑栏
                ((SwipeLayout) viewHolder.getView(R.id.layout_task_title)).setSwipeEnabled(false);//默认都不能打开侧滑栏

            }

        };

        mList_task.setAdapter(mTaskAdapter);
    }

    private  int  pageIndex=0;
    private void loadData(final boolean isAdd) {
//        UserID，TaskType(任务类型)
        RequestParams params=new RequestParams();
        params.put("UserID",mUserInfo.getUID());
        params.put("TaskType",1);
        params.put("pageSize", AppConstants.LIST_PAGE_SIZE);
        params.put("pageIndex",isAdd?pageIndex:0);
        MyHttpUtil.sendGetRequest(getContext(), MyUrls.TASK_LIST, params, MyHttpUtil.ReturnType.ARRAY, TaskInfoBean.class, "", new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if (isAdd) {
                        pageIndex++;
                    } else {
                        mPtrFrame.refreshComplete();
                        pageIndex = 1;
                    }
                    ArrayList<TaskInfoBean> datas = (ArrayList<TaskInfoBean>) object;
                    if ((datas == null || datas.size() == 0) && isAdd) {//没有数据了
                        loadMore.loadMoreFinish(false, false);
                    } else {
                        if (!isAdd) {
                            mTaskDatas.clear();
                        }
                        mTaskDatas.addAll(datas);
                        mTaskAdapter.notifyDataSetChanged();
                        loadMore.loadMoreFinish(false, true);
                    }
                } catch (Exception e) {
                }
            }
        });

    }

}
