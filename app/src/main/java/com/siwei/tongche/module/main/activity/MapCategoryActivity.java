package com.siwei.tongche.module.main.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
import com.siwei.tongche.R;
import com.siwei.tongche.baidumap.BaiduMapUtilsManager;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.module.main.fragment.MapInfoFragment;
import com.siwei.tongche.module.refresh_loadmore.ListViewUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Arrays;
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
        String title=getIntent().getStringExtra("title");
        setTitle(title);
        initView();
    }

    private void initView() {
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
                if(newState== SlidingUpPanelLayout.PanelState.EXPANDED){
                    mLayoutPanelHeight.animate().scaleY(0);
                }else{
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
        mBaiduMapUtilsManager= BaiduMapUtilsManager.initManager(mMapView);
        mBaiduMapUtilsManager.initBaiduMap(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图加载完成
            }
        });

        mOverlays=mBaiduMapUtilsManager.addOverlayRandom(new BaiduMapUtilsManager.OnMyMarkerClick() {
            @Override
            public void onMyMarkerClick(int index) {
                MyToastUtils.showToast("index=="+index);
            }
        });
    }

    private void addAdapter() {
        //设置下拉刷新的参数
        ListViewUtils.setRefreshParams( this, mPtrFrame);
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
        MyBaseAdapter<String> baseAdapter=new MyBaseAdapter<String>(your_array_list,this) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, String model) {
                MyViewHolder viewHolder=MyViewHolder.getViewHolder(MapCategoryActivity.this,convertView,parent,R.layout.layout_task_title,position);
                return viewHolder.getConvertView();
            }
        };

        mListView.setAdapter(baseAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mOverlays != null) {
                    if(mLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    }
                    mBaiduMapUtilsManager.moveTo(((Marker)mOverlays.get(position)).getPosition());
                }
            }
        });
    }
}
