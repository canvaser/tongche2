package com.siwei.tongche.module.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.image.ImageLoaderManager;
import com.siwei.tongche.module.message.bean.CompanyUserList;
import com.siwei.tongche.views.RoundImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by HanJinLiang on 2016-05-12.
 */
public class SelectTargetAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<CompanyUserList> beans;

    // 用来控制CheckBox的选中状况
    private static LinkedHashMap<Integer, Boolean> isSelected;

    class ViewHolder {

        TextView tvName;
        CheckBox cb;
        RoundImageView ivHeader;

    }

    public SelectTargetAdapter(Context context, ArrayList<CompanyUserList> beans) {
        // TODO Auto-generated constructor stub
        this.beans = beans;
        this.context = context;
        isSelected = new LinkedHashMap<Integer, Boolean>();
        // 初始化数据
        initDate();

    }

    public ArrayList<CompanyUserList>  getData(){
        return this.beans;
    }

    // 初始化isSelected的数据
    private void initDate() {
        if(beans==null){
            return;
        }
        for (int i = 0; i < beans.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
    public void setData(ArrayList<CompanyUserList>  beans){
        this.beans = beans;
        // 初始化数据
        initDate();
    }

    @Override
    public int getCount() {
        return beans==null?0:beans.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 页面
        ViewHolder holder;
        CompanyUserList bean = beans.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.item_message_target, parent,false);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_target_checkbox);
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_target_name);
            holder.ivHeader= (RoundImageView) convertView.findViewById(R.id.item_target_headerImg);
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(bean.getUSER_NAME());
        //显示头像
        ImageLoaderManager.getHeaderImage(bean.getHEADER_IMG(), holder.ivHeader);
        // 监听checkBox并根据原来的状态来设置新的状态
        holder.cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    setIsSelected(isSelected);
                } else {
                    isSelected.put(position, true);
                    setIsSelected(isSelected);
                }
                if(onItemCheckedChangedListener!=null){//回调通知点击
                    onItemCheckedChangedListener.onItemCheckedChanged();
                }
            }
        });
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public static LinkedHashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(LinkedHashMap<Integer, Boolean> isSelected) {
        SelectTargetAdapter.isSelected = isSelected;
    }

    public void toggleChecked(int position){
        boolean isSelected=getIsSelected().get(position);
        if(isSelected){
            getIsSelected().put(position,false);
        }else{
            getIsSelected().put(position,true);
        }
        notifyDataSetChanged();
        if(onItemCheckedChangedListener!=null){//回调通知点击
            onItemCheckedChangedListener.onItemCheckedChanged();
        }
    }

    OnItemCheckedChanged onItemCheckedChangedListener;
    public void setOnItemCheckedChangedListener(OnItemCheckedChanged onItemCheckedChanged){
        onItemCheckedChangedListener=onItemCheckedChanged;
    }

    public interface OnItemCheckedChanged{
        public void onItemCheckedChanged();
    }

}
