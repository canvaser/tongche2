package com.siwei.tongche.module.mycollect;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.loopj.android.http.RequestParams;
import com.ramotion.foldingcell.FoldingCell;
import com.siwei.tongche.R;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MySwipeBaseAdapter;
import com.siwei.tongche.common.MySwipeViewHolder;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.dialog.SignExpressDialog;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.activity.MapDetailInfoActivity;
import com.siwei.tongche.module.main.bean.ExpressInfoBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.timeline.view.TimeLineView;

import java.util.ArrayList;

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

public class TicketFragment extends Fragment {
    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.listView_ticket)
    ListView mExpressList;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMore;

    ArrayList<ExpressInfoBean> mDatas=new ArrayList<>();
    MySwipeBaseAdapter<ExpressInfoBean> mExpressAdapter;
    UserInfo mUserInfo= CacheUtils.getLocalUserInfo();
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
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(getActivity(),mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mExpressList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadExpressList(false);
            }
        });
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
        //设置loadMore的参数
        ListViewUtils.setLoadMoreParams(getActivity(), loadMore);
        loadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadExpressList(true);
            }
        });

        addAdapter();
    }

    private void addAdapter() {
        mExpressAdapter=new MySwipeBaseAdapter<ExpressInfoBean>(mDatas,getContext(),R.layout.item_cell_express) {
            @Override
            public int getSwipeLayoutResourceId(int position) {
                return R.id.layout_express_title;
            }

            @Override
            public void fillValues(int position, View convertView, final ExpressInfoBean model) {
                MySwipeViewHolder viewHolder= MySwipeViewHolder.getMySwipeViewHolder( convertView );

                initItem(viewHolder,position,model);
                //初始化侧滑栏
                ((SwipeLayout) viewHolder.getView(R.id.layout_express_title)).setSwipeEnabled(false);//默认都不能打开侧滑栏

                final FoldingCell foldingCell=viewHolder.getView(R.id.item_express);
                viewHolder.getView(R.id.swipe_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                viewHolder.getView(R.id.close_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foldingCell.toggle(false);
                    }
                });
                viewHolder.getView(R.id.express_detail_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyToastUtils.showToast("收藏");
                    }
                });
                viewHolder.getView(R.id.express_detail_call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            CallUtils.gotoCall(getActivity(),model.getTQSRMobile());
                        }else{
                            CallUtils.gotoCall(getActivity(),model.getTDriveMobile());
                        }
                    }
                });
                viewHolder.getView(R.id.express_detail_num).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        SignExpressDialog signExpressDialog=new SignExpressDialog(getContext(),12.0);
                        signExpressDialog.setClicklistener(new SignExpressDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm(String signedSize, String leftSize, String loseSize) {

                            }
                        });
                        signExpressDialog.show();
                        return false;
                    }
                });
                viewHolder.getView(R.id.express_detail_schedule).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(),MapDetailInfoActivity.class));
                    }
                });

            }


            /**
             * 初始化ITEM布局
             * @param viewHolder
             * @param position
             * @param expressInfoBean
             */
            private void initItem(MySwipeViewHolder viewHolder, int position, ExpressInfoBean expressInfoBean) {
                //title布局
                if(expressInfoBean.getTQSStatus().equals("1")){//已签收
                    setNum(expressInfoBean.getTQSNum(),expressInfoBean.getTRemainNum(),expressInfoBean.getTYKNum(), (TextView) viewHolder.getView(R.id.express_title_num));//签收方量/剩余方量/盈亏量
                }else{//未签收
                    viewHolder.setText(R.id.express_title_num,expressInfoBean.getTSendNum());//发货量
                }
                viewHolder.setText(R.id.express_title_historyNum,expressInfoBean.getRTLjNum()+"m³/"+expressInfoBean.getRTLjCs()+"车");//累计方量/累计车次
                viewHolder.setText(R.id.express_title_plateNum,expressInfoBean.getVPlateNumber());//车牌号
                viewHolder.setText(R.id.express_title_carNum,expressInfoBean.getV_NO());//车号
                viewHolder.setText(R.id.express_title_TongType,expressInfoBean.getRTTpz());//砼品种
                viewHolder.setText(R.id.express_title_jiaozhu,"（"+expressInfoBean.getRTLd()+" "+expressInfoBean.getRTJzfs()+"）");//坍落度、浇筑方式
                viewHolder.setText(R.id.express_title_unit,expressInfoBean.getTSendUnitName()+"→"+expressInfoBean.getTReceiveUnitName());//发货单位 收货单位
                viewHolder.setText(R.id.express_title_castingPlace,expressInfoBean.getRTJzbw());//浇筑部位

                //展开布局
                if(expressInfoBean.getIsCollect().equals("1")){//已收藏
                    viewHolder.setImageResource(R.id.express_detail_collect,R.drawable.rating_star_2);
                }else{
                    viewHolder.setImageResource(R.id.express_detail_collect,R.drawable.rating_star_1);
                }
                viewHolder.setText(R.id.express_detail_plate,expressInfoBean.getVPlateNumber());//车牌号
                viewHolder.setText(R.id.express_detail_taskNo_projectAddress,"任务："+expressInfoBean.getPTRNo()+expressInfoBean.getPTGcAddress()  );//任务编号 工程地址
                viewHolder.setText(R.id.express_detail_sendLinkedMan,expressInfoBean.getTDriveName());//发货联系
                viewHolder.setText(R.id.express_detail_unit,expressInfoBean.getTSendUnitName()+"→"+expressInfoBean.getTReceiveUnitName());//发货单位 收货单位
                viewHolder.setText(R.id.express_detail_historyNum,expressInfoBean.getRTLjNum()+"m³/"+expressInfoBean.getRTLjCs()+"车");//累计方量/累计车次
                viewHolder.setText(R.id.express_detail_castingPlace,expressInfoBean.getRTJzbw());//浇筑部位
                viewHolder.setText(R.id.express_detail_expressNo_carBrand,expressInfoBean.getTTNo()+" "+expressInfoBean.getVBrand());//小票编号  车辆品牌
                viewHolder.setText(R.id.express_detail_receiveLinkedMan,expressInfoBean.getTQSRName());//收货联系
                viewHolder.setText(R.id.express_detail_TongType,expressInfoBean.getRTTpz());//砼品种
                viewHolder.setText(R.id.express_detail_jiaozhu,"（"+expressInfoBean.getRTLd()+" "+expressInfoBean.getRTJzfs()+"）");//坍落度、浇筑方式
                if(expressInfoBean.getTQSStatus().equals("1")){//已签收
                    setNum(expressInfoBean.getTQSNum(),expressInfoBean.getTRemainNum(),expressInfoBean.getTYKNum(), (TextView) viewHolder.getView(R.id.express_title_num));//签收方量/剩余方量/盈亏量
                }else{//未签收
                    viewHolder.setText(R.id.express_detail_num,expressInfoBean.getTSendNum());//发货量
                }
                viewHolder.setText(R.id.express_detail_remarks,expressInfoBean.getTMemo());//备注信息
                //进度
                TimeLineView timeLineView = viewHolder.getView(R.id.express_detail_schedule);
                timeLineView.setTxt(new String[]{MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTOutFactoryTime())+" 出厂"
                        ,MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTArriveTime())+" 到达"
                        ,MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTJzTime())+" 浇筑"
                        ,MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTLeaveTime())+" 离开"
                        ,MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTGoBackFactoryTime())+" 回厂"
                        ,expressInfoBean.getTQSStatus().equals("1")?MyFormatUtils.getDetailTime2HHmm(expressInfoBean.getTSignTime())+" 签收":""});
                //当前进度
                if(expressInfoBean.getTQProgress().equals("0")){
                    timeLineView.setNowTime(0);
                }else if(expressInfoBean.getTQProgress().equals("1")){
                    timeLineView.setNowTime(1);
                }else if(expressInfoBean.getTQProgress().equals("2")){
                    timeLineView.setNowTime(2);
                }else if(expressInfoBean.getTQProgress().equals("3")){
                    timeLineView.setNowTime(3);
                }else if(expressInfoBean.getTQProgress().equals("4")){
                    timeLineView.setNowTime(3);
                }else if(expressInfoBean.getTQProgress().equals("5")){
                    timeLineView.setNowTime(4);
                }

            }

            /**
             * 显示签收方量   签收方量/剩余方量/盈亏量
             * @param sginedNum
             * @param leftNum
             * @param profitAndLoss
             * @param textView
             */
            private void setNum(String sginedNum,String leftNum,String profitAndLoss,TextView textView){
                SpannableString styledText = new SpannableString(sginedNum+"/"+leftNum+"/"+profitAndLoss);
                styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.num_large_blue), 0, sginedNum.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.num_normal_blue), sginedNum.length(), sginedNum.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.num_normal_black), sginedNum.length()+1, sginedNum.length()+1+leftNum.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.num_normal_blue), sginedNum.length()+1+leftNum.length(), sginedNum.length()+1+leftNum.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(getContext(), R.style.num_normal_red), sginedNum.length()+1+leftNum.length()+1, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(styledText, TextView.BufferType.SPANNABLE);
            }
        };
        mExpressList.setAdapter(mExpressAdapter);
    }

    private  int pageIndex=0;
    /**
     * 加载小票列表
     */
    private void loadExpressList(final boolean isLoadMore) {
        RequestParams params = new RequestParams();
//        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UID","b3ea8bbb180648d7b14ca6e30136b51b");
        params.put("pageSize", AppConstants.LIST_PAGE_SIZE);
        params.put("pageIndex",isLoadMore?pageIndex:0);
        MyHttpUtil.sendGetRequest(getActivity(), MyUrls.EXPRESS_LIST, params, MyHttpUtil.ReturnType.ARRAY, ExpressInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if (isLoadMore) {
                        pageIndex++;
                    } else {
                        mPtrFrame.refreshComplete();
                        pageIndex = 1;
                    }
                    ArrayList<ExpressInfoBean> datas = (ArrayList<ExpressInfoBean>) object;
                    if ((datas == null || datas.size() == 0) && isLoadMore) {//没有数据了
                        loadMore.loadMoreFinish(false, false);
                    } else {
                        if (!isLoadMore) {
                            mDatas.clear();
                        }
                        mDatas.addAll(datas);
                        mExpressAdapter.notifyDataSetChanged();
                        loadMore.loadMoreFinish(false, true);
                    }
                } catch (Exception e) {
                }
            }
        });
    }
}
