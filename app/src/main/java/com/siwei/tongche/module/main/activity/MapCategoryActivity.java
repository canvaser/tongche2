package com.siwei.tongche.module.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.daimajia.swipe.SwipeLayout;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.module.main.bean.ExpressInfoBean;
import com.siwei.tongche.module.main.bean.MapCategoryProjectBean;
import com.siwei.tongche.module.main.bean.MapMixingstation;
import com.siwei.tongche.module.main.bean.MapTodayCarBean;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.CallUtils;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.utils.TimeUtils;
import com.siwei.tongche.views.CircleBar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 地图  --某个类别
 */
public class MapCategoryActivity extends BaseActivity {
    /**
     * 类别
     */
    public static final String CATEGORY_TYPE = "MapCategoryActivity.CATEGORY_TYPE";

    /**
     * 工作车辆
     */
    public static final String CATEGORY_CAR_WORKING = "工作车辆";
    /**
     * 空闲车辆
     */
    public static final String CATEGORY_CAR_FREE = "空闲车辆";
    /**
     * 故障车辆
     */
    public static final String CATEGORY_CAR_ACCIDENT = "故障车辆";
    /**
     * 附近搅拌站
     */
    public static final String CATEGORY_MIXING_STATION = "附近搅拌站";

    /**
     * 今日车辆
     */
    public static final String CATEGORY_CAR_TODAY = "今日车辆";
    /**
     * 排队车辆
     */
    public static final String CATEGORY_CAR_INLINE = "排队车辆";
    /**
     * 今日工程
     */
    public static final String CATEGORY_PROJECT_TODAY = "今日工程";
    /**
     * 我的工程
     */
    public static final String CATEGORY_PROJECT_MINE = "我的工程";
    /**
     * 附近工程
     */
    public static final String CATEGORY_PROJECT_NEARBY = "附近工程";


    @Bind(R.id.ptrFrame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.loadMore)
    LoadMoreListViewContainer loadMore;

    @Bind(R.id.list)
    ListView mListView;
    @Bind(R.id.sliding_layout)
    public SlidingUpPanelLayout mLayout;

    @Bind(R.id.layout_PanelHeight)
    LinearLayout mLayoutPanelHeight;//上拉控制条

    @Bind(R.id.mapView)
    MapView mMapView;

    BaiduMapUtilsManager mBaiduMapUtilsManager;


    List<Overlay> mOverlays;

    @Override
    public int getContentView() {
        return R.layout.activity_map_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(CATEGORY_TYPE);
        setTitle(title);
        initView();
    }

    private void initView() {
//        initListView();
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
//        mLayout.setFadeOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyToastUtils.showToast("onClick");
//                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//            }
//        });

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mLayoutPanelHeight.animate().scaleY(0);
                } else {
                    mLayoutPanelHeight.animate().scaleY(1);
                }
            }
        });

        mLayout.setAnchorPoint(0.5f);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mLayout.setParallaxOffset(500);
        //设置背景为透明
        mLayout.setCoveredFadeColor(Color.TRANSPARENT);
        //地图初始化
        mBaiduMapUtilsManager = BaiduMapUtilsManager.initManager(mMapView);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
                addAdapter();
            }
        });
    }

    private void initListView() {
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams(this, mPtrFrame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                MyToastUtils.showToast("onRefreshBegin");
            }
        });

        //设置loadMore的参数
        ListViewUtils.setLoadMoreParams(this, loadMore);
        loadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                MyToastUtils.showToast("onLoadMore");
            }
        });
    }

    private void addAdapter() {
        switch (getIntent().getStringExtra(CATEGORY_TYPE)) {
            case CATEGORY_CAR_WORKING://工作车辆  工作中车辆的位置  小票信息
                addExpressAdapter();
                break;
            case CATEGORY_CAR_FREE://空闲车辆  车辆位置 车辆
                addCarAdapter();
                break;
            case CATEGORY_CAR_ACCIDENT://故障车辆 车辆位置 车辆
                addCarAdapter();
                break;
            case CATEGORY_MIXING_STATION://附近搅拌站  搅拌站位置 搅拌站信息
                addMixingStationAdapter();
                break;
            case CATEGORY_CAR_TODAY://今日车辆  车辆位置 任务
                addToadyCarAdapter();
                //任务对于多个车辆   暂时不添加点击事件
                return;
            case CATEGORY_CAR_INLINE://排队车辆 车辆位置  车辆信息
                addCarAdapter();
                break;
            case CATEGORY_PROJECT_TODAY://今日工程 工地 任务
                addTaskAdapter(1);
                break;
            case CATEGORY_PROJECT_MINE://我的工程  历史上所有的工程，包括今日工程。列表显示所有任务单
                addTaskAdapter(2);
                break;
            case CATEGORY_PROJECT_NEARBY://附近工程   附近工程页地图显示我附近的工程位置；下方以列表形式显示我附近的工程信息
                addTaskAdapter(3);
                break;
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mOverlays != null) {
                    if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    }
                    mBaiduMapUtilsManager.moveTo(((Marker) mOverlays.get(position)).getPosition());
                }
            }
        });
    }

    ArrayList<MapTodayCarBean> mMapTodayCarBeans = new ArrayList<MapTodayCarBean>();
    MyBaseAdapter<MapTodayCarBean> mMapTodayCarAdapter;
    /**
     * 今日车辆
     */
    private void addToadyCarAdapter() {
        mMapTodayCarAdapter = new MyBaseAdapter<MapTodayCarBean>(mMapTodayCarBeans, this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, MapTodayCarBean model) {
                MyViewHolder viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.layout_task_title_content, position);
                //完成比例
                int progress = (int) ((MyFormatUtils.toDouble(model.getLjNum()) / MyFormatUtils.toDouble(model.getPlanNum())) * 100);
                CircleBar circleBar = viewHolder.getView(R.id.task_progress);
                circleBar.update(progress, 1000);

                //title
                viewHolder.setText(R.id.task_title_taskNo, model.getTaskNo());//任务编号
                viewHolder.setText(R.id.task_title_TongType, model.getTpz());//砼品种
                viewHolder.setText(R.id.task_title_jiaozhu, "（" + model.getRtld() + " " + model.getJzfs() + "）");//坍落度、浇筑方式
                viewHolder.setText(R.id.task_title_unit, model.getSendUnitName() + "→" + model.getRecvUnitName());//发货单位 收货单位
                viewHolder.setText(R.id.task_title_castingPlace, model.getJzbw());//浇筑部位
                viewHolder.setText(R.id.task_title_express, model.getCompleteCs() + "/" + model.getSendCs());//已有小票20  签收15车
                viewHolder.setText(R.id.task_title_progressTime, TimeUtils.getTimeWaitting(model.getAcceptTime()));//任务执行时间
                viewHolder.setText(R.id.task_title_finishedVolume, model.getLjNum() + "m³");//完成方量
                viewHolder.setText(R.id.task_title_planVolume, model.getPlanNum() + "");//计划方量

                return viewHolder.getConvertView();
            }
        };
        mListView.setAdapter(mMapTodayCarAdapter);
        loadTodayCarData(false);
    }

    /**
     * 加载今日车辆
     */
    private void loadTodayCarData(final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        params.put("userid", 1);
        MyHttpUtil.sendGetRequest(this, "http://192.168.20.122:8889/SenderMap/GetVehicles", params, MyHttpUtil.ReturnType.ARRAY, MapTodayCarBean.class, "", new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if (object != null) {
                    if (isLoadMore) {
                        pageIndex++;
                    } else {
                        pageIndex = 1;
                    }
                    ArrayList<MapTodayCarBean> datas = (ArrayList<MapTodayCarBean>) object;

                    if ((datas == null || datas.size() == 0) && isLoadMore) {//没有数据了
                        loadMore.loadMoreFinish(false, false);
                    } else {
                        if (!isLoadMore) {
                            mMapTodayCarBeans.clear();
                        }
                        mMapTodayCarBeans.addAll(datas);
                        mMapTodayCarAdapter.notifyDataSetChanged();
                        loadMore.loadMoreFinish(false, true);

                        //地图添加
                        ArrayList<LatLng> projectPoints = new ArrayList<LatLng>();
                        for (MapTodayCarBean beans : mMapTodayCarBeans) {
                            if(beans.getVehicles()!=null) {
                                for (MapTodayCarBean.VehiclesBean bean : beans.getVehicles()) {
                                    projectPoints.add(new LatLng(MyFormatUtils.toDouble(bean.getVGpsy()), MyFormatUtils.toDouble(bean.getVGpsx())));
                                }
                            }
                        }
                        mBaiduMapUtilsManager.clear();
                        mOverlays = mBaiduMapUtilsManager.addOverlayWhitoutClick(projectPoints, BaiduMapUtilsManager.MAP_TYPE_PROJECT);
                    }
                }
            }
        });
    }

    //页数
    private int pageIndex = 0;
    ArrayList<MapCategoryProjectBean> mMapCategoryProjectBeans = new ArrayList<MapCategoryProjectBean>();
    MyBaseAdapter<MapCategoryProjectBean> mMapCategoryProjectAdapter;

    /**
     * 列表是任务单
     *
     * @param type 1表示今日工程 2表示我的工程  3表示附近工程
     */
    private void addTaskAdapter(final int type) {
        mMapCategoryProjectAdapter = new MyBaseAdapter<MapCategoryProjectBean>(mMapCategoryProjectBeans, this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, MapCategoryProjectBean model) {
                MyViewHolder viewHolder = null;
                if (type != 3) {
                    viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.layout_task_title_content, position);
                    //完成比例
                    int progress = (int) ((MyFormatUtils.toDouble(model.getLjNum()) / MyFormatUtils.toDouble(model.getPlanNum())) * 100);
                    CircleBar circleBar = viewHolder.getView(R.id.task_progress);
                    circleBar.update(progress, 1000);

                    //title
                    viewHolder.setText(R.id.task_title_taskNo, model.getTaskNo());//任务编号
                    viewHolder.setText(R.id.task_title_TongType, model.getTpz());//砼品种
                    viewHolder.setText(R.id.task_title_jiaozhu, "（" + model.getRtld() + " " + model.getJzfs() + "）");//坍落度、浇筑方式
                    viewHolder.setText(R.id.task_title_unit, model.getSendUnitName() + "→" + model.getRecvUnitName());//发货单位 收货单位
                    viewHolder.setText(R.id.task_title_castingPlace, model.getJzbw());//浇筑部位
                    viewHolder.setText(R.id.task_title_express, model.getCompleteCs() + "/" + model.getSendCs());//已有小票20  签收15车
                    viewHolder.setText(R.id.task_title_progressTime, TimeUtils.getTimeWaitting(model.getAcceptTime()));//任务执行时间
                    viewHolder.setText(R.id.task_title_finishedVolume, model.getLjNum() + "m³");//完成方量
                    viewHolder.setText(R.id.task_title_planVolume, model.getPlanNum() + "");//计划方量
                } else {//附近工程   列表显示工程信息
                    viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.item_mixing_station, position);
                    viewHolder.setText(R.id.name, model.getProName());
                    viewHolder.setText(R.id.linkMan_name, model.getProLinkMan());
                    viewHolder.setText(R.id.address, model.getProAddress());
                    viewHolder.getView(R.id.linkMan_name).setOnClickListener(onClick);
                    viewHolder.getView(R.id.linkMan_name).setTag(R.id.linkMan_name, model.getProLinkTel());
                    //计算p1、p2两点之间的直线距离，单位：米
                    double distance=DistanceUtil.getDistance(new LatLng(CacheUtils.getLatitudes(),CacheUtils.getLongitude())
                            , new LatLng(MyFormatUtils.toDouble(model.getProGpsy()),MyFormatUtils.toDouble(model.getProGpsx())));
                    viewHolder.setText(R.id.distance,distance<1000?(distance+"米"):(MyFormatUtils.doubleSave1(distance/1000.0)+"公里"));
                }
                return viewHolder.getConvertView();
            }

            View.OnClickListener onClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拨打电话
                    String mobile = (String) v.getTag(R.id.linkMan_name);
                    CallUtils.gotoCall(MapCategoryActivity.this, mobile);
                }
            };
        };
        mListView.setAdapter(mMapCategoryProjectAdapter);
        loadTaskData(false, type);
    }

    /**
     * 加载任务单
     *
     * @param type 1表示今日工程 2表示我的工程  3表示附近工程
     */
    private void loadTaskData(final boolean isLoadMore, final int type) {
        RequestParams params = new RequestParams();
        params.put("userid", 1);
        params.put("type", type);
        MyHttpUtil.sendGetRequest(this, "http://192.168.20.122:8889/SenderMap/gettasks", params, MyHttpUtil.ReturnType.ARRAY, MapCategoryProjectBean.class, "", new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if (object != null) {
                    if (isLoadMore) {
                        pageIndex++;
                    } else {
                        pageIndex = 1;
                    }
                    ArrayList<MapCategoryProjectBean> datas = (ArrayList<MapCategoryProjectBean>) object;

                    if ((datas == null || datas.size() == 0) && isLoadMore) {//没有数据了
                        loadMore.loadMoreFinish(false, false);
                    } else {
                        if (!isLoadMore) {
                            mMapCategoryProjectBeans.clear();
                        }
                        mMapCategoryProjectBeans.addAll(datas);
                        mMapCategoryProjectAdapter.notifyDataSetChanged();
                        loadMore.loadMoreFinish(false, true);

                        //地图添加
                        ArrayList<LatLng> projectPoints = new ArrayList<LatLng>();
                        for (MapCategoryProjectBean bean : mMapCategoryProjectBeans) {
                            projectPoints.add(new LatLng(MyFormatUtils.toDouble(bean.getProGpsy()), MyFormatUtils.toDouble(bean.getProGpsx())));
                        }
                        mBaiduMapUtilsManager.clear();
                        mOverlays = mBaiduMapUtilsManager.addOverlayWhitoutClick(projectPoints, BaiduMapUtilsManager.MAP_TYPE_PROJECT);
                    }
                }
            }
        });
    }

    /**
     * 列表小票
     */
    private void addExpressAdapter() {
        MyBaseAdapter<String> baseAdapter = new MyBaseAdapter<String>(null, this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.layout_express_title, position);
                return viewHolder.getConvertView();
            }
        };
        mListView.setAdapter(baseAdapter);
    }

    /**
     * 列表 车辆信息
     */
    private void addCarAdapter() {
        MyBaseAdapter<String> baseAdapter = new MyBaseAdapter<String>(null, this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.item_waitting_car, position);
                return viewHolder.getConvertView();
            }
        };
        mListView.setAdapter(baseAdapter);
    }


    ArrayList<MapMixingstation> mMapMixingstationBeans = new ArrayList<MapMixingstation>();
    MyBaseAdapter<MapMixingstation> mMapMixingstationAdapter;
    /**
     * 列表  搅拌站信息
     */
    private void addMixingStationAdapter() {

        mMapMixingstationAdapter = new MyBaseAdapter<MapMixingstation>(mMapMixingstationBeans, this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, MapMixingstation model) {
//
                MyViewHolder viewHolder = MyViewHolder.getViewHolder(MapCategoryActivity.this, convertView, parent, R.layout.item_mixing_station, position);
//                    viewHolder.setText(R.id.name, model.getProName());
//                    viewHolder.setText(R.id.linkMan_name, model.getProLinkMan());
//                    viewHolder.setText(R.id.address, model.getProAddress());
//                    viewHolder.getView(R.id.linkMan_name).setOnClickListener(onClick);
//                    viewHolder.getView(R.id.linkMan_name).setTag(R.id.linkMan_name, model.getProLinkTel());
//                    //计算p1、p2两点之间的直线距离，单位：米
//                    double distance=DistanceUtil.getDistance(new LatLng(CacheUtils.getLatitudes(),CacheUtils.getLongitude())
//                            , new LatLng(MyFormatUtils.toDouble(model.getProGpsy()),MyFormatUtils.toDouble(model.getProGpsx())));
//                    viewHolder.setText(R.id.distance,distance<1000?(distance+"米"):(MyFormatUtils.doubleSave1(distance/1000.0)+"公里"));
                return viewHolder.getConvertView();
            }

            View.OnClickListener onClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拨打电话
                    String mobile = (String) v.getTag(R.id.linkMan_name);
                    CallUtils.gotoCall(MapCategoryActivity.this, mobile);
                }
            };
        };
        mListView.setAdapter(mMapMixingstationAdapter);
        loadMixingStationData(false);
    }

    private void loadMixingStationData(final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        params.put("userid", 1);
        MyHttpUtil.sendGetRequest(this, " http://192.168.20.122:8889/SenderMap/GetJBZ", params, MyHttpUtil.ReturnType.ARRAY, MapMixingstation.class, "", new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if (object != null) {
                    if (isLoadMore) {
                        pageIndex++;
                    } else {
                        pageIndex = 1;
                    }
                    ArrayList<MapMixingstation> datas = (ArrayList<MapMixingstation>) object;

                    if ((datas == null || datas.size() == 0) && isLoadMore) {//没有数据了
                        loadMore.loadMoreFinish(false, false);
                    } else {
                        if (!isLoadMore) {
                            mMapMixingstationBeans.clear();
                        }
                        mMapMixingstationBeans.addAll(datas);
                        mMapCategoryProjectAdapter.notifyDataSetChanged();
                        loadMore.loadMoreFinish(false, true);

                        //地图添加
                        ArrayList<LatLng> projectPoints = new ArrayList<LatLng>();
                        for (MapCategoryProjectBean bean : mMapCategoryProjectBeans) {
                            projectPoints.add(new LatLng(MyFormatUtils.toDouble(bean.getProGpsy()), MyFormatUtils.toDouble(bean.getProGpsx())));
                        }
                        mBaiduMapUtilsManager.clear();
                        mOverlays = mBaiduMapUtilsManager.addOverlayWhitoutClick(projectPoints, BaiduMapUtilsManager.MAP_TYPE_PROJECT);
                    }
                }
            }
        });
    }
}
