package com.siwei.tongche.module.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.A;
import com.daimajia.swipe.SwipeLayout;
import com.loopj.android.http.RequestParams;
import com.ramotion.foldingcell.FoldingCell;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.common.AppConstants;
import com.siwei.tongche.common.BaseBean;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MySwipeBaseAdapter;
import com.siwei.tongche.common.MySwipeViewHolder;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.dialog.SignExpressDialog;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.main.activity.MapDetailInfoActivity;
import com.siwei.tongche.module.main.activity.WaittingCarActivity;
import com.siwei.tongche.module.main.bean.ExpressInfoBean;
import com.siwei.tongche.module.main.bean.MapMixingstation;
import com.siwei.tongche.module.main.bean.MapProject;
import com.siwei.tongche.module.main.bean.MapVehiclePosition;
import com.siwei.tongche.module.main.bean.WaittingCarInfoBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyLogUtils;
import com.siwei.tongche.utils.MyRegexpUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.TimeUtils;
import com.siwei.tongche.views.timeline.view.TimeLineView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * Created by HanJinLiang on 2016-12-23.
 * 地图Fragment
 */

public class ExpressListFragment extends Fragment {
    private final static int TAG_STATUS=R.id.express_swipe;
    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMore;

    @Bind(R.id.list)
    ListView mExpressList;
    @Bind(R.id.sliding_layout)
    public SlidingUpPanelLayout mLayout;

    @Bind(R.id.express_list_map)
    MapView mExpressListMap;
    BaiduMapUtilsManager mBaiduMapUtilsManager;

    ArrayList<ExpressInfoBean> mDatas=new ArrayList<>();
    MySwipeBaseAdapter<ExpressInfoBean> mExpressAdapter;
    UserInfo mUserInfo=CacheUtils.getLocalUserInfo();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_express_list,container,false);
        ButterKnife.bind(this,view);//绑定
        initView();
        return view;
    }

    private void initView() {
        //设置loadMore的参数
        ListViewUtils.setLoadMoreParams(getActivity(), loadMore);
        loadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadExpressList(true);
            }
        });

        addAdapter();

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("addPanelSlideListener", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("addPanelSlideListener", "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        mLayout.setAnchorPoint(0.6f);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mLayout.setParallaxOffset(500);
        mLayout.setOverlayed(true);


        mBaiduMapUtilsManager= BaiduMapUtilsManager.initManager(mExpressListMap);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
                loadMapData();
            }
        });

        loadWaittingData();
        loadExpressList(false);
    }

    /**
     * 加载首页地图数据
     */
    private void loadMapData() {
        //        UID(用户ID)
        RequestParams params = new RequestParams();
//        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UID","b3ea8bbb180648d7b14ca6e30136b51b");
        MyHttpUtil.sendGetRequest(getActivity(), MyUrls.DRIVER_MAP_INFO, params, MyHttpUtil.ReturnType.JSONSTRING, WaittingCarInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if(object==null){
                        return;
                    }
                    JSONObject data=JSONObject.parseObject(object.toString());
                    String status=data.getString("status");
                    if(status.equals("1000")){
                        mBaiduMapUtilsManager.clear();
                        JSONObject  resultData=data.getJSONObject("resultData");
                        //taskStauts  1 无送货任务且无排队时、taskStauts 2无送货任务且空车处于排队中时 3 有送货任务时
                        int taskStauts= resultData.getIntValue("taskStauts");
                        if(taskStauts==1||taskStauts==2) {
                            JSONArray mixingstation = resultData.getJSONArray("mixingstation");
                            final ArrayList<MapMixingstation> mixingstationList = (ArrayList<MapMixingstation>) JSONArray.parseArray(mixingstation.toJSONString(), MapMixingstation.class);

                            JSONArray project = resultData.getJSONArray("project");
                            final ArrayList<MapProject> projectList = (ArrayList<MapProject>) JSONArray.parseArray(project.toJSONString(), MapProject.class);

//                        JSONArray  vehiclePosition=resultData.getJSONArray("VehiclePosition");
//                        ArrayList<MapVehiclePosition>  VehiclePositionList= (ArrayList<MapVehiclePosition>) JSONArray.parseArray(vehiclePosition.toJSONString(), MapVehiclePosition.class);
                            //----------搅拌站----------
                            ArrayList<LatLng> mixingstationPoints = new ArrayList<LatLng>();
                            for (MapMixingstation mixingstationInfo : mixingstationList) {
                                mixingstationPoints.add(new LatLng(MyFormatUtils.toDouble(mixingstationInfo.getMSGpsy()), MyFormatUtils.toDouble(mixingstationInfo.getMSGpsx())));
                            }
                            mBaiduMapUtilsManager.addOverlayWhitoutClick(mixingstationPoints, BaiduMapUtilsManager.MAP_TYPE_MIXINGSTATION);
                            //------------工程------------
                            ArrayList<LatLng> projectPoints = new ArrayList<LatLng>();
                            for (MapProject projectInfo : projectList) {
                                projectPoints.add(new LatLng(MyFormatUtils.toDouble(projectInfo.getPGCGpsy()), MyFormatUtils.toDouble(projectInfo.getPGCGpsx())));
                            }
                            mBaiduMapUtilsManager.addOverlayWhitoutClick(projectPoints, BaiduMapUtilsManager.MAP_TYPE_PROJECT);

                            //------------车辆-----------
//                        ArrayList<LatLng> vehiclePoints=new ArrayList<LatLng>();
//                        for(MapVehiclePosition vehicleInfo:VehiclePositionList ){
//                            vehiclePoints.add(new LatLng(MyFormatUtils.toDouble(vehicleInfo.getVGpsy()),MyFormatUtils.toDouble(vehicleInfo.getVGpsx())));
//                        }
//                        mBaiduMapUtilsManager.addOverlay(vehiclePoints, new BaiduMapUtilsManager.OnMyMarkerClick() {
//                            @Override
//                            public void onMyMarkerClick(int index) {
//                                MyToastUtils.showToast("车辆---"+index);
//                            }
//                        });
                            mBaiduMapUtilsManager.setMultipleMarkerClicker(new BaiduMapUtilsManager.OnMultipleMarkerClick() {
                                @Override
                                public void onMyMarkerClick(int dataType, int index) {
                                    Intent intent=new Intent(getActivity(),MapDetailInfoActivity.class);
                                    switch (dataType) {
                                        case BaiduMapUtilsManager.MAP_TYPE_CAR://车辆
                                            MyToastUtils.showToast("车辆==" + index);
                                            break;
                                        case BaiduMapUtilsManager.MAP_TYPE_PROJECT://工程
                                            MyToastUtils.showToast("工程==" + index);
                                            intent.putExtra(MapDetailInfoActivity.MAP_TYPE,MapDetailInfoActivity.MAP_PROJECT);
                                            intent.putExtra(MapDetailInfoActivity.MAP_DATA,projectList.get(index));
                                            break;
                                        case BaiduMapUtilsManager.MAP_TYPE_MIXINGSTATION://搅拌站
                                            MyToastUtils.showToast("搅拌站==" + index);
                                            intent.putExtra(MapDetailInfoActivity.MAP_TYPE,MapDetailInfoActivity.MAP_MIXING_STATION);
                                            intent.putExtra(MapDetailInfoActivity.MAP_DATA,mixingstationList.get(index));
                                            break;
                                    }
                                    getActivity().startActivity(intent);

                                }
                            });
                        }else if(taskStauts==3){//有任务
                          JSONObject ticketInfo= resultData.getJSONObject("ticketInfo");
                            //发货单位
                            String sendUnitGpsx=ticketInfo.getString("SendUnitGpsx");
                            String sendUnitGpsy=ticketInfo.getString("SendUnitGpsy");
                            //收货单位
                            String reciveUnitGpsx=ticketInfo.getString("ReciveUnitGpsx");
                            String reciveUnitGpsy=ticketInfo.getString("ReciveUnitGpsy");
                            //司机位置
                            String VGpsx=ticketInfo.getString("VGpsx");
                            String VGpsy=ticketInfo.getString("VGpsy");
                            //规划路径
                            String pGpsx=ticketInfo.getString("PGpsx");
                            String pGpsy=ticketInfo.getString("PGpsy");

                            mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_MIXINGSTATION,new LatLng(MyFormatUtils.toDouble(sendUnitGpsy), MyFormatUtils.toDouble(sendUnitGpsx)));
                            mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_PROJECT,new LatLng(MyFormatUtils.toDouble(reciveUnitGpsy), MyFormatUtils.toDouble(reciveUnitGpsx)));
                            mBaiduMapUtilsManager.addSingleOverlay(BaiduMapUtilsManager.MAP_TYPE_CAR,new LatLng(MyFormatUtils.toDouble(VGpsy), MyFormatUtils.toDouble(VGpsx)));

                            String[] planWay_JD=pGpsx.split(",");
                            String[] planWay_WD=pGpsy.split(",");
                            if(planWay_JD.length>0) {
                                ArrayList<LatLng> points = new ArrayList<LatLng>();
                                for (int i = 0; i < planWay_JD.length; i++) {
                                    MyLogUtils.e("point=" + planWay_JD[i] + "," + planWay_WD[i]);
                                    points.add(new LatLng(MyFormatUtils.toDouble(planWay_WD[i]), MyFormatUtils.toDouble(planWay_JD[i])));
                                }
                                mBaiduMapUtilsManager.addLine(points, true);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 等待车辆列表
     */
    private void loadWaittingData() {
        //        UID(用户ID)
        RequestParams params = new RequestParams();
//        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UID","b3ea8bbb180648d7b14ca6e30136b51b");
        MyHttpUtil.sendGetRequest(getActivity(), MyUrls.WAITTING_CAR, params, MyHttpUtil.ReturnType.OBJECT, WaittingCarInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if(object!=null){
                        addHeaderView((WaittingCarInfoBean) object);
                    }else{//当前没有排队
                        if(mTimer!=null){//取消上次的计时器
                            mTimer.cancel();
                            mTimer=null;
                        }
                        mExpressList.removeHeaderView(mHeaderView);
                    }
                } catch (Exception e) {
                }
            }
        });

    }

    private  int pageIndex=0;
    /**
     * 加载小票列表
     */
    private void loadExpressList(final boolean isLoadMore) {
        RequestParams params = new RequestParams();
//        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("UID","b3ea8bbb180648d7b14ca6e30136b51b");
        params.put("pageSize",AppConstants.LIST_PAGE_SIZE);
        params.put("pageIndex",pageIndex);

        MyHttpUtil.sendGetRequest(getActivity(), MyUrls.EXPRESS_LIST, params, MyHttpUtil.ReturnType.ARRAY, ExpressInfoBean.class, "",new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                        if (isLoadMore) {
                            pageIndex++;
                        } else {
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
    View mHeaderView;
    private void addHeaderView(WaittingCarInfoBean waittingCarInfoBean) {
        if(mTimer!=null){//取消上次的计时器
            mTimer.cancel();
            mTimer=null;
        }
        mHeaderView=LayoutInflater.from(getContext()).inflate(R.layout.item_waitting_car,null);
        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WaittingCarActivity.class));
            }
        });
        ImageLoaderManager.getHeaderImage(waittingCarInfoBean.getUHeadImg(), ((ImageView) mHeaderView.findViewById(R.id.imageView)));
        ((TextView)mHeaderView.findViewById(R.id.waitting_driver_name)).setText(waittingCarInfoBean.getUName());//司机姓名
        ((TextView)mHeaderView.findViewById(R.id.waitting_plate_number)).setText(waittingCarInfoBean.getVPlateNumber());//车牌号
        ((TextView)mHeaderView.findViewById(R.id.waitting_carNo)).setText(waittingCarInfoBean.getV_NO());//车号
        ((TextView)mHeaderView.findViewById(R.id.waitting_car_brand)).setText(waittingCarInfoBean.getVBrand());//车品牌
        ((TextView)mHeaderView.findViewById(R.id.waitting_car_size)).setText(waittingCarInfoBean.getVGgxh());//车大小
        ((TextView)mHeaderView.findViewById(R.id.waitting_unit_name)).setText(waittingCarInfoBean.getUnitName());//单位名称
        ((TextView)mHeaderView.findViewById(R.id.wait_time)).setText("已等待："+TimeUtils.getTimeWaitting(waittingCarInfoBean.getVGoBackTime()));
        mExpressList.addHeaderView(mHeaderView);
        //开启定时器
        startTimer(waittingCarInfoBean.getVGoBackTime(),((TextView)mHeaderView.findViewById(R.id.wait_time)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){//取消计时器
            mTimer.cancel();
            mTimer=null;
        }
    }

    Timer mTimer;
    private void startTimer(final String startTime,final TextView tvWaittingTime) {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvWaittingTime.setText("已等待："+ TimeUtils.getTimeWaitting(startTime));
                    }
                });
            }
        },1000,1000);
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
                initSwipeMenu((SwipeLayout) viewHolder.getView(R.id.layout_express_title),model,position);

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
             * 初始化侧滑栏
             * @param swipeLayout
             * @param expressInfoBean
             */
            private void initSwipeMenu(SwipeLayout swipeLayout, final ExpressInfoBean expressInfoBean, final int position) {
                Button swipe_menu= (Button) swipeLayout.findViewById(R.id.swipe_menu);
                swipeLayout.close();//默认关闭
                swipeLayout.setSwipeEnabled(false);//默认都不能打开侧滑栏
                switch (expressInfoBean.getPTRuleCode()){
                    case "1"://全自动签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setText("离开");
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setText("回厂");
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{

                        }
                        break;
                    case "2"://驾驶员确认到达-系统签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{

                        }
                        break;
                    case "3"://驾驶员确认到达-驾驶员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{

                        }
                        break;
                    case "4":// 驾驶员确认到达-签收员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{
                            if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                    case "5"://签收员确认到达-签收员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                    case "6"://签收员确认到达-系统签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                    case "7"://签收员确认到达-驾驶员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{
                            if(expressInfoBean.getTQProgress().equals("0")){//出发
                                swipe_menu.setTag(TAG_STATUS,"1");
                                swipe_menu.setText("到达");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                    case "8"://GPS到达-驾驶员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                    case "9"://GPS到达-签收员确认签收
                        if(mUserInfo.getURoleCode().equals(AppConstants.USER_TYPE.TYPE_DRIVER)){//驾驶员
                            if(expressInfoBean.getTQProgress().equals("4")){//签收
                                swipe_menu.setTag(TAG_STATUS,"3");
                                swipe_menu.setText("离开");
                                swipeLayout.setSwipeEnabled(true);
                            }else if(expressInfoBean.getTQProgress().equals("3")){//离开
                                swipe_menu.setTag(TAG_STATUS,"5");
                                swipe_menu.setText("回厂");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }else{
                            if(expressInfoBean.getTQProgress().equals("1")){//到达  签收
                                swipe_menu.setTag(TAG_STATUS,"4");
                                swipe_menu.setText("签收");
                                swipeLayout.setSwipeEnabled(true);
                            }
                        }
                        break;
                }

                swipe_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //侧滑按钮操作类型
                        String opeStatus= (String) v.getTag(TAG_STATUS);
                        changeExpressStatus(expressInfoBean.getTID(),opeStatus,position);
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

    /**
     * 加载小票列表
     *  UserID（用户id）、TicketId(小票id)、TStatus（小票进度状态）
     */
    private void changeExpressStatus(String TicketId,final String TStatus, final int position) {
        JSONObject params = new JSONObject();
        params.put("UID", CacheUtils.getLocalUserInfo().getUID());
        params.put("TicketId", TicketId);
        params.put("TStatus", TStatus);
        MyHttpUtil.sendRequest(getActivity(), MyUrls.EXPRESS_STATUS_CHANGE, params, MyHttpUtil.ReturnType.BOOLEAN, null, "", new MyHttpUtil.HttpResult() {

            @Override
            public void onResult(Object object) {
                try {
                    if ((boolean) object) {
                        //刷新数据
                        mDatas.get(position).setTQProgress(TStatus);
                        mExpressAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }
        });
    }
}
