package com.siwei.tongche.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.siwei.tongche.R;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.utils.DensityUtil;

import java.util.ArrayList;


/**
 * 主菜单
 */
public class NormalSelectPop extends PopupWindow {
    public static final int POP_WITDH=150;//dp
    public static final int POP_Y_OFFSET=-20;//dp

    private Context mContext;

    private OnItemClickListener itemclick;
   private OnItemClickListener  mListener;
    private ArrayList<SelectMenuBean> mDatas=new ArrayList<SelectMenuBean>();

    public NormalSelectPop(Context context, ArrayList<SelectMenuBean> datas, OnItemClickListener onItemClickListener){
        mDatas= datas;
        mListener=onItemClickListener;
        mContext=context;
        init(POP_WITDH,3*50+10);
    }

    public NormalSelectPop(Context context, ArrayList<SelectMenuBean> datas, OnItemClickListener onItemClickListener, int witdh, int showNum){
        mDatas= datas;
        mListener=onItemClickListener;
        mContext=context;
        init(witdh,showNum*50+10);
    }
    public NormalSelectPop(Context context, ArrayList<SelectMenuBean> datas, OnItemClickListener onItemClickListener,int showNum){
        mDatas= datas;
        mListener=onItemClickListener;
        mContext=context;
        init(POP_WITDH,showNum*50+10);
    }

    private int rWidth;//真实宽度
    private void init(int width,int height) {
        rWidth=width;
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(colorDrawable);
        this.setWidth(DensityUtil.dip2px(width));
        this.setHeight(DensityUtil.dip2px(height));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.MyPopWindow_anim_normal);

        //解决pop 被系统键盘挡住
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ListView contentView=new ListView(mContext);
        contentView.setBackgroundResource(R.drawable.bg_pop_right);
        contentView.setPadding(0,DensityUtil.dip2px(5),0,0);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(width), ViewGroup.LayoutParams.MATCH_PARENT));

        initView(contentView);
        this.setContentView(contentView);
    }

    private void initView(ListView dialog_listview) {
        dialog_listview.setAdapter(new MyBaseAdapter<SelectMenuBean>(mDatas,mContext) {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent, SelectMenuBean model) {
                MyViewHolder myViewHolder=new MyViewHolder(mContext,parent,R.layout.item_select_menu,position);
                myViewHolder.setText(R.id.dialog_item_txt,model.getMenuName());
                myViewHolder.setImageResource(R.id.menu_item_icon,model.getIconRes());
                return myViewHolder.getConvertView();
            }
        });
        dialog_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.itemClick(mDatas.get(position).getMenuName(),position);
                dismiss();
            }
        });
    }


    /**
     * 设置点击事件
     *
     * @param click
     */
    public void setOnItemClick(OnItemClickListener click) {
        this.itemclick = click;
    }

    public interface OnItemClickListener {
        void itemClick(String title, int position);
    }


   public void showBlowView(View view){
       showAsDropDown(view,view.getWidth()/2- DensityUtil.dip2px(rWidth/2),NormalSelectPop.POP_Y_OFFSET);
   }

    public void showBlowView(View view,int Y_OFFSET){
        showAsDropDown(view,view.getWidth()/2- DensityUtil.dip2px(rWidth/2),Y_OFFSET);
    }
}
