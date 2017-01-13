package com.siwei.tongche.module.message;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseActivity;
import com.siwei.tongche.common.MyBaseAdapter;
import com.siwei.tongche.common.MyViewHolder;
import com.siwei.tongche.dialog.MiddleSelectPop;
import com.siwei.tongche.http.MyHttpUtil;
import com.siwei.tongche.http.MyUrls;
import com.siwei.tongche.module.login.bean.UserInfo;
import com.siwei.tongche.module.message.activity.TicketDetailActivity;
import com.siwei.tongche.module.message.bean.MessageDetailBean;
import com.siwei.tongche.module.message.ope.MessageDAOpe;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.MyLogUtils;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

import static com.siwei.tongche.module.message.ope.MessageDAOpe.*;

public class MessageActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView mTv_title;

    @Bind(R.id.listView_message)
    ListView mListView_message;

    MyBaseAdapter<MessageDetailBean> mMessageAdapter;

    MessageDAOpe messageDAOpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageDAOpe= new MessageDAOpe();
        setTitle("消息");
        setRight(R.drawable.title_add,"");
        Drawable arrowDown= ContextCompat.getDrawable(this,R.drawable.arrow_left);
        arrowDown.setBounds(0,0,50,50);
        mTv_title.setCompoundDrawables(null,null,arrowDown,null);
        initView();
        getMessage();
    }


    public void getMessage(){
        //（消息类型 0 全部 消息类别小票1、车辆2、用户3、任务4、系统5、公告6）
        RequestParams params=new RequestParams();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("UID",userInfo.getUID());
        params.put("MType",messageDAOpe.getMessageMenuBean().getmType());
        params.put("pageSize","10");
        params.put("pageIndex",messageDAOpe.getMessageMenuBean().getIndex()+"");

        MyHttpUtil.sendGetRequest(this, MyUrls.GET_MESSAGE, params, MyHttpUtil.ReturnType.ARRAY, MessageDetailBean.class, "获取中...",new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if(object==null){
                    return;
                }
                messageDAOpe.getMessageMenuBean().getList().addAll((ArrayList<MessageDetailBean>) object);
                //messageDAOpe.getMessageMenuBean().setIndex(messageDAOpe.getMessageMenuBean().getIndex()+1);
                initView();
            }
        });
    }

    private void initView() {
        mMessageAdapter=new MyBaseAdapter<MessageDetailBean>(messageDAOpe.getMessageMenuBean().getList(),this) {

            @Override
            public View getItemView(final int position, View convertView, ViewGroup parent, final MessageDetailBean model) {

                MyViewHolder viewHolder = null;
                //用户和任务有接受拒绝按钮
                if(messageDAOpe.getMessageMenuBean().getList().get(position).getMType().equals("2")||
                        messageDAOpe.getMessageMenuBean().getList().get(position).getMType().equals("3")){
                    viewHolder=MyViewHolder.getViewHolder(MessageActivity.this,convertView,parent,R.layout.item_message,position);
                }else{
                    viewHolder=MyViewHolder.getViewHolder(MessageActivity.this,convertView,parent,R.layout.item_message_without,position);
                }
                viewHolder.setText(R.id.dialog_item_txt,model.getMContent()+"");
                viewHolder.getView(R.id.ll_txt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        //（消息类型 0 全部 消息类别小票1、车辆2、用户3、任务4、系统5、公告6）
                        switch (messageDAOpe.getMessageMenuBean().getList().get(position).getMType()){
                            case "0":

                                break;
                            case "1":
                                intent = new Intent(MessageActivity.this, TicketDetailActivity.class);
                                startActivity(intent);
                                break;
                            case "2":

                                break;
                            case "3":

                                break;
                            case "4":

                                break;

                            case "5":
                                break;

                        }



                        intent = new Intent(MessageActivity.this, TicketDetailActivity.class);
                        startActivity(intent);
                    }
                });
                //接受
                viewHolder.getView(R.id.message_accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BindUnitAudit(model.getMUID(),"1");
                    }
                });
                //拒绝
                viewHolder.getView(R.id.message_refuse).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BindUnitAudit(model.getMUID(),"2");
                    }
                });
                return viewHolder.getConvertView();
            }
        };
        mListView_message.setAdapter(mMessageAdapter);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_message;
    }

    @OnClick({R.id.tv_title})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_title:
                showMiddleDialog(view);
                break;
        }
    }

    //UID(审核人ID)、MUID(消息发送人用户id)，MStatus （审核状态类型 1接受、2拒绝） MBindUnitType （消息表中绑定单位类型字段）
    public void BindUnitAudit(String muid,String mstatus){
        JSONObject params=new JSONObject();
        UserInfo userInfo= CacheUtils.getLocalUserInfo();
        params.put("ID",userInfo.getUID());
        params.put("MUID",muid);
        params.put("MStatus",mstatus);
        //1自身2发货单位
        params.put("MBindUnitType","1");

        MyHttpUtil.sendRequest(this, MyUrls.BIND_UNIT_AUDIT, params, MyHttpUtil.ReturnType.BOOLEAN, null, new MyHttpUtil.HttpResult() {
            @Override
            public void onResult(Object object) {
                if((boolean)object){//绑定车辆成功

                }
            }
        });
    }

    /**
     * 选择类别
     */
    private void showMiddleDialog(View view) {
        ArrayList<String> typeData=messageDAOpe.getNames();
        MiddleSelectPop typePop=new MiddleSelectPop(this, typeData, new MiddleSelectPop.OnItemClickListener() {
            @Override
            public void itemClick(String title, int position) {
                messageDAOpe.setMessageMenuBean(position);
                getMessage();
            }
        },100,typeData.size());
        typePop.showBlowView(view);
    }

    @Override
    public void onRightText(View view) {
        super.onRightText(view);
        startActivity(new Intent(this,SelectTargetActivity.class));
    }
}
